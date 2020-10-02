package net.zeeraa.dynamiccurrencies.commands.dynamiccurrencies;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.zcommandlib.command.ZSubCommand;
import net.zeeraa.zcommandlib.command.utils.AllowedSenders;

public class SCInfo extends ZSubCommand {

	public SCInfo() {
		super("info");

		setPermission("dynamiccurrencues.command.dynamiccurrencies.info");
		setPermissionDefaultValue(PermissionDefault.TRUE);
		setAllowedSenders(AllowedSenders.ALL);
		setDescription("Show info abount the plugin");
		
		setEmptyTabMode(true);
		
		
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.GOLD + "-=-= DynamicCurrencies =-=-");
		sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.AQUA + DynamicCurrenciesAPI.getPluginVersion());
		sender.sendMessage(ChatColor.GOLD + "Authors: " + ChatColor.AQUA + DynamicCurrenciesAPI.getPluginAuthors());
		
		return true;
	}
}