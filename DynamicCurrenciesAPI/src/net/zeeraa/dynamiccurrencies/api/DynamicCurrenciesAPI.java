package net.zeeraa.dynamiccurrencies.api;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.dynamiccurrencies.api.currency.CurrencyDataManager;
import net.zeeraa.dynamiccurrencies.api.implementation.APIImplementation;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerDataManager;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerEconomyData;

public class DynamicCurrenciesAPI {
	/**
	 * Get the primary {@link Currency} that is used for the server
	 * 
	 * @return The default {@link Currency}
	 */
	public static Currency getPrimaryCurrency() {
		return APIImplementation.getCurrencyDataManager().getPrimaryCurrency();
	}

	/**
	 * Check if a player has an account
	 * 
	 * @param uuid The {@link UUID} of the player
	 * @return <code>true</code> if the player has an account
	 */
	public static boolean hasAccount(UUID uuid) {
		return APIImplementation.hasAccount(uuid);
	}

	/**
	 * Check if an account can be created for the player.
	 * 
	 * @param uuid {@link UUID} of the player
	 * @return <code>true</code> if an account can be created for the player
	 */
	public static boolean canCreateAccount(UUID uuid) {
		return APIImplementation.canCreateAccount(uuid);
	}

	/**
	 * Try to create an account for a player
	 * 
	 * @param uuid {@link UUID} of the player
	 * @return <code>true</code> if an account was created
	 */
	public static boolean createAccount(UUID uuid) {
		return APIImplementation.createAccount(uuid);
	}

	/**
	 * Get the instance of the {@link PlayerDataManager} used
	 * 
	 * @return The {@link PlayerDataManager}
	 */
	public static PlayerDataManager getPlayerDataManager() {
		return APIImplementation.getPlayerDataManager();
	}

	/**
	 * Get the instance of the {@link CurrencyDataManager} used
	 * 
	 * @return The {@link CurrencyDataManager}
	 */
	public static CurrencyDataManager getCurrencyDataManager() {
		return APIImplementation.getCurrencyDataManager();
	}

	/**
	 * Get the {@link PlayerEconomyData} of a {@link OfflinePlayer}
	 * <p>
	 * See {@link PlayerDataManager#getPlayerEconomyData(UUID)} for more info about
	 * the behavior of this function
	 * 
	 * @param player The {@link OfflinePlayer} to get the data for
	 * @return The {@link PlayerEconomyData} for the player
	 */
	public static PlayerEconomyData getPlayerEconomyData(OfflinePlayer player) {
		return getPlayerEconomyData(player.getUniqueId());
	}

	/**
	 * Get the {@link PlayerEconomyData} of a player by their {@link UUID}
	 * <p>
	 * See {@link PlayerDataManager#getPlayerEconomyData(UUID)} for more info about
	 * the behavior of this function
	 * 
	 * @param uuid The {@link UUID} of the player
	 * @return The {@link PlayerEconomyData} for the player
	 */
	public static PlayerEconomyData getPlayerEconomyData(UUID uuid) {
		return getPlayerDataManager().getPlayerEconomyData(uuid);
	}

	/**
	 * Check if the player data should be saved on every transaction
	 * <p>
	 * Causes higher disk IO but also prevents data loss on crashes or power loss
	 * 
	 * @return <code>true</code> if player data should be saved on transaction
	 */
	public static boolean isSaveDataOnTransaction() {
		return APIImplementation.isSaveOnTransaction();
	}

	/**
	 * Check if the DynamicCurrencies plugin in enabled
	 * 
	 * @return <code>true</code> if DynamicCurrencies is enabled
	 */
	public static boolean isEnabled() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("DynamicCurrencies");

		if (plugin != null) {
			return plugin.isEnabled();
		}
		return false;
	}

	/**
	 * Get an instance of the DynamicCurrencies {@link Plugin}
	 * 
	 * @return Plugin instance
	 */
	public static Plugin getPlugin() {
		return APIImplementation.getPlugin();
	}

	/**
	 * Get the version of the plugin
	 * 
	 * @return Version
	 */
	public static String getPluginVersion() {
		if (APIImplementation.getPlugin() != null) {
			return APIImplementation.getPlugin().getDescription().getVersion();
		}
		return null;
	}

	/**
	 * Get the currency amount to display
	 * 
	 * @param amount The real amount
	 * @return The amount to display
	 */
	public static double formatCurrency(Double amount) {
		return ((int) (amount * 100)) / 100.00;
	}
}