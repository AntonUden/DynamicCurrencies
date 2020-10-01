package net.zeeraa.dynamiccurrencies;

import java.io.File;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import net.zeeraa.dynamiccurrencies.api.APIImplementation;
import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.dynamiccurrencies.api.currency.CurrencyDataManager;
import net.zeeraa.dynamiccurrencies.datamanagers.DefaultCurrencyDataManager;
import net.zeeraa.dynamiccurrencies.datamanagers.DefaultPlayerDataManager;


public class DynamicCurrencies extends JavaPlugin {
	private static DynamicCurrencies instance;
	
	private File playerDataFolder;
	private File currenciesFile;

	public static DynamicCurrencies getInstance() {
		return instance;
	}
	
	public File getPlayerDataFolder() {
		return playerDataFolder;
	}
	
	public File getCurrenciesFile() {
		return currenciesFile;
	}
	
	@Override
	public void onEnable() {
		DynamicCurrencies.instance = this;
		
		ConfigurationSerialization.registerClass(Account.class);
		
		saveDefaultConfig();

		playerDataFolder = getFileFromPlaceholders(getConfig().getString("player-data-folder"));
		currenciesFile = getFileFromPlaceholders(getConfig().getString("currencies-file"));
		
	
		
		APIImplementation.setCurrencyDataManager(new DefaultCurrencyDataManager());
		APIImplementation.setPlayerDataManager(new DefaultPlayerDataManager());
		
		DynamicCurrenciesAPI.getCurrencyDataManager().loadCurrencies();
	}

	private File getFileFromPlaceholders(String path) {
		path = path.replace("{PluginDirectory}", getDataFolder().getPath());

		return new File(path);
	}
}
