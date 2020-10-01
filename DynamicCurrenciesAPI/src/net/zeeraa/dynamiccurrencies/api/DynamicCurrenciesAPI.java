package net.zeeraa.dynamiccurrencies.api;

import java.util.UUID;

import org.bukkit.entity.Player;

import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerDataManager;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerEconomyData;

public class DynamicCurrenciesAPI {
	/**
	 * Get the instance of the {@link PlayerDataManager} used
	 * 
	 * @return The {@link PlayerDataManager}
	 */
	public static PlayerDataManager getPlayerDataManager() {
		return null; // TODO: fix
	}

	/**
	 * Get the {@link PlayerEconomyData} of a {@link Player}
	 * <p>
	 * See {@link PlayerDataManager#getPlayerEconomyData(UUID)} for more info about
	 * the behavior of this function
	 * 
	 * @param player The {@link Player} to get the data for
	 * @return The {@link PlayerEconomyData} for the player
	 */
	public static PlayerEconomyData getPlayerEconomyData(Player player) {
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
}