package net.zeeraa.dynamiccurrencies.api.implementation;

import org.bukkit.plugin.Plugin;

import net.zeeraa.dynamiccurrencies.api.currency.CurrencyDataManager;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerDataManager;

public class APIImplementation {
	private static PlayerDataManager playerDataManager;
	private static CurrencyDataManager currencyDataManager;
	private static boolean saveOnTransaction;
	private static Plugin plugin;
	
	public static PlayerDataManager getPlayerDataManager() {
		return playerDataManager;
	}
	
	public static void setPlayerDataManager(PlayerDataManager playerDataManager) {
		APIImplementation.playerDataManager = playerDataManager;
	}
	
	public static CurrencyDataManager getCurrencyDataManager() {
		return currencyDataManager;
	}
	
	public static void setCurrencyDataManager(CurrencyDataManager currencyDataManager) {
		APIImplementation.currencyDataManager = currencyDataManager;
	}
	
	public static boolean isSaveOnTransaction() {
		return saveOnTransaction;
	}
	
	public static void setSaveOnTransaction(boolean saveOnTransaction) {
		APIImplementation.saveOnTransaction = saveOnTransaction;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static void setPlugin(Plugin plugin) {
		APIImplementation.plugin = plugin;
	}
}