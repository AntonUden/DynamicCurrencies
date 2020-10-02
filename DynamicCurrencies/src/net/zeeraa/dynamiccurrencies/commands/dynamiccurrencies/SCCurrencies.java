package net.zeeraa.dynamiccurrencies.commands.dynamiccurrencies;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.zcommandlib.command.ZSubCommand;
import net.zeeraa.zcommandlib.command.utils.AllowedSenders;

public class SCCurrencies extends ZSubCommand {

	public SCCurrencies() {
		super("currencies");

		setDescription("Show currencies");
		setPermission("dynamiccurrencies.command.dynamiccurrencies.currencies");
		setPermissionDefaultValue(PermissionDefault.TRUE);
		setAllowedSenders(AllowedSenders.ALL);
		setEmptyTabMode(true);
		addHelpSubCommand();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "-=-=-= Currencies =-=-=-");
		for (Currency currency : DynamicCurrenciesAPI.getCurrencyDataManager().getCurrencies().values()) {
			sender.sendMessage(ChatColor.AQUA + currency.getDisplayNamePlural() + ChatColor.GOLD + " Rate: " + currency.getExchangeRate());
		}
		sender.sendMessage(ChatColor.GOLD + "The rate is relative to the server primary currency " + ChatColor.AQUA + DynamicCurrenciesAPI.getPrimaryCurrency().getDisplayNameSingular());

		return true;
	}
}