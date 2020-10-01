package net.zeeraa.dynamiccurrencies.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;

public class UnloadDataOnQuit implements Listener {
	private boolean saveOnQuit;

	public UnloadDataOnQuit(boolean saveOnQuit) {
		this.saveOnQuit = saveOnQuit;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent e) {
		DynamicCurrenciesAPI.getPlayerDataManager().unloadPlayerEconomyData(e.getPlayer().getUniqueId(), saveOnQuit);
	}
}