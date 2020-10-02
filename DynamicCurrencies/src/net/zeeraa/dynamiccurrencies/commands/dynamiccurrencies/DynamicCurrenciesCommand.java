package net.zeeraa.dynamiccurrencies.commands.dynamiccurrencies;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerEconomyData;
import net.zeeraa.zcommandlib.command.ZCommand;
import net.zeeraa.zcommandlib.command.utils.AllowedSenders;

public class DynamicCurrenciesCommand extends ZCommand {

	public DynamicCurrenciesCommand(List<String> aliases) {
		super("dynamiccurrencies");

		setAliases(aliases);
		setPermission("dynamiccurrencues.command.dynamiccurrencies");
		setPermissionDefaultValue(PermissionDefault.TRUE);
		setAllowedSenders(AllowedSenders.ALL);
		setHelpString("/dynamiccurrencies");

		addSubCommand(new SCInfo());
		addSubCommand(new SCBalance());
		addSubCommand(new SCCurrencies());
		addSubCommand(new SCWithdraw());
		addSubCommand(new SCDeposit());
		addSubCommand(new SCSetBalance());

		setEmptyTabMode(true);

		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			PlayerEconomyData economyData = DynamicCurrenciesAPI.getPlayerEconomyData(((Player) sender).getUniqueId());

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

		} else {
			sender.sendMessage(ChatColor.RED + "use '/dynamiccurrencies help' for a list of commands");
		}
		return true;
	}

}
