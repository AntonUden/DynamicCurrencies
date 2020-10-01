package net.zeeraa.dynamiccurrencies;

import java.io.File;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import net.zeeraa.dynamiccurrencies.api.account.Account;


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
	}

	private File getFileFromPlaceholders(String path) {
		path = path.replace("{PluginDirectory}", getDataFolder().getPath());

		return new File(path);
	}
}
