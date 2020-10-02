package net.zeeraa.dynamiccurrencies.commands.dynamiccurrencies;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerEconomyData;
import net.zeeraa.zcommandlib.command.ZSubCommand;
import net.zeeraa.zcommandlib.command.utils.AllowedSenders;
import net.zeeraa.zcommandlib.command.utils.PermissionRegistrator;

public class SCBalance extends ZSubCommand {

	public SCBalance() {
		super("balance");

		setAliases(generateAliasList("setbal"));
		
		setDescription("Show balance");
		setPermission("dynamiccurrencies.command.dynamiccurrencies.balance");
		setPermissionDefaultValue(PermissionDefault.TRUE);
		setAllowedSenders(AllowedSenders.ALL);
		setHelpString("/dynamiccurrencies balance [Player]");
		setFilterAutocomplete(true);
		addHelpSubCommand();
		
		PermissionRegistrator.registerPermission("dynamiccurrencies.command.dynamiccurrencies.balance", "", PermissionDefault.OP);
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				showInfo(sender, (Player) sender);
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "Please provide a player");
			}
		} else {
			if(sender.hasPermission("dynamiccurrencies.command.dynamiccurrencies.balance")) {
				//TODO: Show
				return true;
			} else {
				sender.sendMessage(ChatColor.RED + "You dont have permission to show other players balance");
			}
		}
		return false;
	}

	private void showInfo(CommandSender sender, Player player) {
		PlayerEconomyData economyData = DynamicCurrenciesAPI.getPlayerEconomyData(player.getUniqueId());

		sender.sendMessage(ChatColor.GOLD + "-=-=-= Balance =-=-=-");
		if (economyData.getAccounts().size() == 0) {
			sender.sendMessage(ChatColor.AQUA + economyData.getAccount(economyData.getPrimaryCurrency()).toString());
		} else {
			boolean onlyPrimary = true;
			for (Account account : economyData.getAccounts()) {
				sender.sendMessage(ChatColor.AQUA + account.toString());
				if (!account.getCurrency().equals(economyData.getPrimaryCurrency())) {
					onlyPrimary = false;
				}
			}

			if (!onlyPrimary) {
				double pServerBalance = economyData.getPrimaryServerCurrencyBalance();
				if (pServerBalance > 0) {
					double balPlayer = DynamicCurrenciesAPI.formatCurrency(economyData.getPrimaryCurrency().convertFromOtherCurrency(DynamicCurrenciesAPI.getPrimaryCurrency(), pServerBalance));
					sender.sendMessage(ChatColor.GOLD + "This is equivilent to " + ChatColor.AQUA + balPlayer + " " + economyData.getPrimaryCurrency().getDisplayName(balPlayer));
				}
			}
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		List<String> result = new ArrayList<String>();

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			result.add(player.getName());
		}

		return result;
	}
}