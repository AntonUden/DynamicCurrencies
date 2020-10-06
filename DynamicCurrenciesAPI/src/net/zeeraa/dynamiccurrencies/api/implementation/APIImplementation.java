package net.zeeraa.dynamiccurrencies.api.implementation;

import java.util.UUID;

import org.bukkit.plugin.Plugin;

import net.zeeraa.dynamiccurrencies.api.currency.CurrencyDataManager;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerDataManager;

public class APIImplementation {
	private static PlayerDataManager playerDataManager;
	private static CurrencyDataManager currencyDataManager;
	private static boolean saveOnTransaction;
	private static Plugin plugin;
	
	public static PlayerDataManager getPlayerDataManager() {
		return APIImplementation.playerDataManager;
	}
	
	public static void setPlayerDataManager(PlayerDataManager playerDataManager) {
		APIImplementation.playerDataManager = playerDataManager;
	}
	
	public static CurrencyDataManager getCurrencyDataManager() {
		return APIImplementation.currencyDataManager;
	}
	
	public static void setCurrencyDataManager(CurrencyDataManager currencyDataManager) {
		APIImplementation.currencyDataManager = currencyDataManager;
	}
	
	public static boolean isSaveOnTransaction() {
		return APIImplementation.saveOnTransaction;
	}
	
	public static void setSaveOnTransaction(boolean saveOnTransaction) {
		APIImplementation.saveOnTransaction = saveOnTransaction;
	}
	
	public static Plugin getPlugin() {
		return APIImplementation.plugin;
	}
	
	public static void setPlugin(Plugin plugin) {
		APIImplementation.plugin = plugin;
	}

	public static boolean hasAccount(UUID uuid) {
		return APIImplementation.playerDataManager.hasAccount(uuid);
	}

	public static boolean canCreateAccount(UUID uuid) {
		return APIImplementation.playerDataManager.canCreateAccount(uuid);
	}
	
	public static boolean createAccount(UUID uuid) {
		return APIImplementation.playerDataManager.hasAccount(uuid);
	}
}