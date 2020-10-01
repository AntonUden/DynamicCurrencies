package net.zeeraa.dynamiccurrencies.api.implementation;

import net.zeeraa.dynamiccurrencies.api.currency.CurrencyDataManager;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerDataManager;

public class APIImplementation {
	private static PlayerDataManager playerDataManager;
	private static CurrencyDataManager currencyDataManager;
	
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
}