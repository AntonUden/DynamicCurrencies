package net.zeeraa.dynamiccurrencies.commands.dynamiccurrencies;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerEconomyData;
import net.zeeraa.zcommandlib.command.ZSubCommand;
import net.zeeraa.zcommandlib.command.utils.AllowedSenders;

public class SCWithdraw extends ZSubCommand {
	public SCWithdraw() {
		super("withdraw");

		setPermission("dynamiccurrencies.command.dynamiccurencies.withdraw");
		setPermissionDefaultValue(PermissionDefault.OP);

		setFilterAutocomplete(true);
		setAllowedSenders(AllowedSenders.ALL);
		setDescription("Withdraw currency from a user");
		setHelpString("/dynamiccurencies withdraw <player> <currency> <amount>");
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 3) {
			Player player = Bukkit.getServer().getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(ChatColor.RED + "Could not find player: " + args[0]);
				return false;
			}

			Currency currency = DynamicCurrenciesAPI.getCurrencyDataManager().getCurrency(args[1]);

			if (currency == null) {
				sender.sendMessage(ChatColor.RED + "Could not find currency: " + args[1]);
				return false;
			}

			try {
				double amount = Double.parseDouble(args[2]);
				if (amount < 0) {
					sender.sendMessage(ChatColor.RED + "Cant withdraw a negative amount");
					return false;
				}

				if (!DynamicCurrenciesAPI.hasAccount(player.getUniqueId())) {
					sender.sendMessage(ChatColor.RED + "That player does not have an account");
					return false;
				}

				PlayerEconomyData playerEconomyData = DynamicCurrenciesAPI.getPlayerEconomyData(player.getUniqueId());

				if (amount > playerEconomyData.getAccount(currency).getBalance()) {
					sender.sendMessage(ChatColor.RED + "The player does not have enough money");
					return false;
				}

				playerEconomyData.getAccount(currency).withdrawBalance(amount);
				playerEconomyData.save();

				sender.sendMessage(ChatColor.GREEN + "Withdrawn " + amount + " " + currency.getDisplayName(amount));
				return true;
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Invalid currency amount");
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Useage: /dynamiccurencies withdraw <player> <currency> <amount>");
		}
		return false;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		List<String> result = new ArrayList<String>();
		if (args.length == 1) {
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				result.add(player.getName());
			}
		} else if (args.length == 2) {
			for (Currency currency : DynamicCurrenciesAPI.getCurrencyDataManager().getCurrencies().values()) {
				result.add(currency.getName().toLowerCase());
			}
		}

		return result;
	}
}