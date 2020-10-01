package net.zeeraa.dynamiccurrencies.api.playerdata;

import java.io.File;
import java.util.UUID;

public abstract class PlayerDataManager {
	/**
	 * Get the {@link PlayerEconomyData} for a player by their {@link UUID}
	 * <p>
	 * When this is called the first time it will try to read the file from
	 * {@link PlayerDataManager#getPlayerDataFile(UUID)} and it that file does not
	 * exits a new {@link PlayerEconomyData} will be created with the default
	 * currency for the server as the players primary currency, this will also call
	 * the {@link PlayerDataManager#savePlayerEconomyData(UUID)} to create the data
	 * file
	 * <p>
	 * The {@link PlayerEconomyData} will be cached in memory until
	 * {@link PlayerDataManager#unloadAll()}
	 * 
	 * @param uuid The {@link UUID} of the player
	 * @return The {@link PlayerEconomyData} for the player
	 */
	public abstract PlayerEconomyData getPlayerEconomyData(UUID uuid);

	/**
	 * Unload the economy data for a player from memory and save their data
	 * <p>
	 * This function calls
	 * {@link PlayerDataManager#unloadPlayerEconomyData(UUID, boolean)} with the
	 * save parameter set to <code>true</code>
	 * 
	 * @param uuid The {@link UUID} of the player to unload data from
	 * @return returns <code>true</code> if the data was unloaded,
	 *         <code>false</code> if the player did not have any loaded data
	 */
	public boolean unloadPlayerEconomyData(UUID uuid) {
		return unloadPlayerEconomyData(uuid, true);
	}

	/**
	 * Unload the economy data for a player from memory
	 * 
	 * @param uuid The {@link UUID} of the player to unload data from
	 * @param save If <code>true</code> the player data will be saved on unload
	 * @return returns <code>true</code> if the data was unloaded,
	 *         <code>false</code> if the player did not have any loaded data
	 */
	public abstract boolean unloadPlayerEconomyData(UUID uuid, boolean save);

	/**
	 * Save the {@link PlayerEconomyData} for a player by their {@link UUID}
	 * <p>
	 * If a player does not have any economy data loaded this will return true but
	 * not save anything
	 * 
	 * @param uuid The {@link UUID} of the player to save data for
	 * @return returns <code>false</code> if the file could not be written
	 */
	public abstract boolean savePlayerEconomyData(UUID uuid);

	/**
	 * Unload and save all loaded {@link PlayerEconomyData} instances
	 */
	public void unloadAll() {
		unloadAll(true);
	}

	/**
	 * Unload all loaded {@link PlayerEconomyData} instances
	 * 
	 * @param Set to <code>true</code> to also call
	 *            {@link PlayerDataManager#savePlayerEconomyData(UUID)} on the
	 *            player data
	 */
	public abstract void unloadAll(boolean save);

	/**
	 * Get the {@link File} used to store player data files in
	 * <p>
	 * This should only return a folder and never a file
	 * 
	 * @return The {@link File} used to store player data files in
	 */
	public abstract File getPlayerDataFolder();

	/**
	 * Get the data file for a player
	 * 
	 * @param uuid The {@link UUID} of the player
	 * @return A file that should be used to store data for the player. Note that
	 *         this file may not exist
	 */
	public abstract File getPlayerDataFile(UUID uuid);
}