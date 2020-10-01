package net.zeeraa.dynamiccurrencies.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;

public class LoadDataOnJoin implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		DynamicCurrenciesAPI.getPlayerEconomyData(e.getPlayer().getUniqueId());
	}
}