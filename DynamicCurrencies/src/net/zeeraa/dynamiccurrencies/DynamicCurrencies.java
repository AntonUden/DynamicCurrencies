package net.zeeraa.dynamiccurrencies;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.implementation.APIImplementation;
import net.zeeraa.dynamiccurrencies.datamanagers.DefaultCurrencyDataManager;
import net.zeeraa.dynamiccurrencies.datamanagers.DefaultPlayerDataManager;
import net.zeeraa.dynamiccurrencies.listener.LoadDataOnJoin;
import net.zeeraa.dynamiccurrencies.listener.UnloadDataOnQuit;

public class DynamicCurrencies extends JavaPlugin {
	private static DynamicCurrencies instance;

	private File playerDataFolder;
	private File currenciesFile;

	private boolean saveOnShutdown;

	private boolean showDebugMessages;

	public static DynamicCurrencies getInstance() {
		return instance;
	}

	public File getPlayerDataFolder() {
		return playerDataFolder;
	}

	public File getCurrenciesFile() {
		return currenciesFile;
	}

	public boolean isShowDebugMessages() {
		return showDebugMessages;
	}

	@Override
	public void onEnable() {
		DynamicCurrencies.instance = this;
		saveOnShutdown = false;

		ConfigurationSerialization.registerClass(Account.class);

		saveDefaultConfig();

		playerDataFolder = getFileFromPlaceholders(getConfig().getString("player-data-folder"));
		currenciesFile = getFileFromPlaceholders(getConfig().getString("currencies-file"));

		try {
			FileUtils.forceMkdir(playerDataFolder);
		} catch (IOException e) {
			e.printStackTrace();
			getLogger().severe("Failed to create player data directory: " + playerDataFolder.getPath());
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		try {
			if (!currenciesFile.exists()) {
				getLogger().info("Creating default currencies.yml at: " + currenciesFile.getPath());
				InputStream in = getClass().getClassLoader().getResourceAsStream("currencies.yml");
				Files.copy(in, currenciesFile.toPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			getLogger().severe("Failed to create currencies.yml at: " + currenciesFile.getPath());
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		showDebugMessages = getConfig().getBoolean("print-debug-info");

		if (showDebugMessages) {
			System.out.println("Debug messages enabled");
		}

		APIImplementation.setCurrencyDataManager(new DefaultCurrencyDataManager());
		APIImplementation.setPlayerDataManager(new DefaultPlayerDataManager());

		if (!DynamicCurrenciesAPI.getCurrencyDataManager().loadCurrencies()) {
			getLogger().severe("Failed to read currencies.yml at: " + currenciesFile.getPath() + ". Check if there is any error above this one");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		if (getConfig().getBoolean("preload-player-data")) {
			if (showDebugMessages) {
				System.out.println("Registering LoadDataOnJoin()");
			}
			Bukkit.getPluginManager().registerEvents(new LoadDataOnJoin(), this);
		}

		if (getConfig().getBoolean("unload-data-on-quit")) {
			boolean saveOnQuit = getConfig().getBoolean("save-data-on-quit");
			if (showDebugMessages) {
				System.out.println("Registering UnloadDataOnQuit() Save on quit: " + saveOnQuit);
			}
			Bukkit.getPluginManager().registerEvents(new UnloadDataOnQuit(saveOnQuit), this);
		}

		saveOnShutdown = getConfig().getBoolean("save-data-on-shutdown");
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll((Plugin) this);
		DynamicCurrenciesAPI.getPlayerDataManager().unloadAll(saveOnShutdown);
	}

	private File getFileFromPlaceholders(String path) {
		path = path.replace("(PluginDirectory)", getDataFolder().getPath());

		return new File(path);
	}
}
