package net.zeeraa.dynamiccurrencies;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.milkbowl.vault.economy.Economy;
import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.implementation.APIImplementation;
import net.zeeraa.dynamiccurrencies.api.vault.DynamicCurrenciesVault;
import net.zeeraa.dynamiccurrencies.commands.dynamiccurrencies.DynamicCurrenciesCommand;
import net.zeeraa.dynamiccurrencies.datamanagers.DefaultCurrencyDataManager;
import net.zeeraa.dynamiccurrencies.datamanagers.DefaultPlayerDataManager;
import net.zeeraa.dynamiccurrencies.listener.LoadDataOnJoin;
import net.zeeraa.dynamiccurrencies.listener.UnloadDataOnQuit;
import net.zeeraa.zcommandlib.command.registrator.ZCommandRegistrator;

public class DynamicCurrencies extends JavaPlugin {
	private static DynamicCurrencies instance;

	private File playerDataFolder;
	private File currenciesFile;

	private boolean saveOnShutdown;

	private boolean showDebugMessages;

	private boolean saveOnTransaction;

	private boolean vaultIntegrationAdded;
	private boolean dynamicCurrenciesCommandAdded;

	private BukkitTask clearCacheTask;

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

	public boolean isSaveOnTransaction() {
		return saveOnTransaction;
	}

	public boolean isVaultIntegrationAdded() {
		return vaultIntegrationAdded;
	}

	public boolean isDynamicCurrenciesCommandAdded() {
		return dynamicCurrenciesCommandAdded;
	}

	@Nullable
	public BukkitTask getClearCacheTask() {
		return clearCacheTask;
	}

	@Override
	public void onEnable() {
		DynamicCurrencies.instance = this;
		saveOnShutdown = false;
		vaultIntegrationAdded = false;
		dynamicCurrenciesCommandAdded = false;

		clearCacheTask = null;

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
		saveOnTransaction = getConfig().getBoolean("save-data-on-transaction");
		saveOnShutdown = getConfig().getBoolean("save-data-on-shutdown");

		if (showDebugMessages) {
			System.out.println("Debug messages enabled");
		}

		APIImplementation.setPlugin(this);
		APIImplementation.setSaveOnTransaction(saveOnTransaction);
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

		if (getConfig().getBoolean("enable-vault-integratio")) {
			ServicePriority servicePriority = null;

			try {
				servicePriority = ServicePriority.valueOf(getConfig().getString("vault-service-priority"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (servicePriority == null) {
				getLogger().severe("Invalid service priority " + getConfig().getString("vault-service-priority") + " in config.yml. Valid values can be found here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/plugin/ServicePriority.html");
				servicePriority = ServicePriority.Normal;
			}

			Bukkit.getServicesManager().register(Economy.class, new DynamicCurrenciesVault(), this, servicePriority);
			vaultIntegrationAdded = true;
		}

		if (getConfig().getBoolean("enable-dynamic-currencies-command")) {
			List<String> aliases = getConfig().getStringList("dynamic-currencies-command-aliases");

			System.out.println(aliases);

			ZCommandRegistrator.registerCommand(this, new DynamicCurrenciesCommand(aliases));
			dynamicCurrenciesCommandAdded = true;
		}

		long clearCacheDelay = getConfig().getLong("clear-cache-delay");
		if (clearCacheDelay > 0) {
			clearCacheTask = new BukkitRunnable() {
				@Override
				public void run() {
					DynamicCurrenciesAPI.getPlayerDataManager().clearCache(true);
				}
			}.runTaskTimer(this, clearCacheDelay, clearCacheDelay);
		}
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll((Plugin) this);
		DynamicCurrenciesAPI.getPlayerDataManager().unloadAll(saveOnShutdown);
	}

	private File getFileFromPlaceholders(String path) {
		path = path.replace("(PluginDirectory)", getDataFolder().getPath());

		return new File(path);
	}
}