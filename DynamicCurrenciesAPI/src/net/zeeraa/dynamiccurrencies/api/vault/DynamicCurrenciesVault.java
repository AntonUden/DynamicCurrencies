package net.zeeraa.dynamiccurrencies.api.vault;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerDataManager;

public class DynamicCurrenciesVault implements Economy {

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getName() {
		return "DynamicCurrencies";
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public int fractionalDigits() {
		return -1;
	}
	
	@Override
	public String format(double amount) {
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(DynamicCurrenciesAPI.formatCurrency(amount));
	}

	@Override
	public String currencyNamePlural() {
		return DynamicCurrenciesAPI.getPrimaryCurrency().getDisplayNamePlural();
	}

	@Override
	public String currencyNameSingular() {
		return DynamicCurrenciesAPI.getPrimaryCurrency().getDisplayNameSingular();
	}

	/**
	 * 
	 * Checks if this player has an account on the server yet This will always
	 * return true if the player has joined the server at least once as all major
	 * economy plugins auto-generate a player account when the player joins the
	 * server
	 * <p>
	 * Even if the player does not have any player data it will be created when
	 * trying to get the account from
	 * {@link PlayerDataManager#getPlayerEconomyData(java.util.UUID)} and there is
	 * no good way right now to check if the player has player data so instead this
	 * will always return true
	 * 
	 * @param player to check
	 * @return if the player has an account
	 */

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		return true;
	}

	@Override
	/**
	 * Checks if this player has an account on the server yet on the given world
	 * This will always return true if the player has joined the server at least
	 * once as all major economy plugins auto-generate a player account when the
	 * player joins the server
	 * <p>
	 * see {@link DynamicCurrenciesVault#hasAccount(OfflinePlayer)} for more info
	 * 
	 * @param player    to check in the world
	 * @param worldName world-specific account
	 * @return if the player has an account
	 */
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		return hasAccount(player);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return DynamicCurrenciesAPI.getPlayerEconomyData(player.getUniqueId()).getPrimaryServerCurrencyBalance();
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		return DynamicCurrenciesAPI.getPlayerEconomyData(player.getUniqueId()).getPrimaryServerCurrencyBalance() >= amount;
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		return has(player, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		if (amount < 0) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cant withdraw a negative amount");
		}

		if (!has(player, amount)) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "The player does not have enough money");
		}

		if (DynamicCurrenciesAPI.getPlayerEconomyData(player).withdraw(amount)) {
			return new EconomyResponse(amount, DynamicCurrenciesAPI.getPlayerEconomyData(player).getPrimaryServerCurrencyBalance(), ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Could not withdraw money");
		}
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		return withdrawPlayer(player, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		if (amount < 0) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cant deposit a negative amount");
		}

		if (DynamicCurrenciesAPI.getPlayerEconomyData(player).depositInPlayersPrimaryCurrency(amount)) {
			return new EconomyResponse(amount, DynamicCurrenciesAPI.getPlayerEconomyData(player).getPrimaryServerCurrencyBalance(), ResponseType.SUCCESS, "");
		} else {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Could not deposit money");
		}
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		return depositPlayer(player, amount);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		DynamicCurrenciesAPI.getPlayerDataManager().getPlayerEconomyData(player.getUniqueId()).save();
		return true;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		return createPlayerAccount(player);
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "DynamicCurrencies does not support banks");
	}

	@Override
	public List<String> getBanks() {
		// This plugin does not support banks
		return new ArrayList<String>();
	}

	// Names are not supported

	@Override
	@Deprecated
	public boolean createPlayerAccount(String playerName) {
		return false;
	}

	@Override
	@Deprecated
	public boolean createPlayerAccount(String playerName, String worldName) {
		return false;
	}

	@Override
	@Deprecated
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Player names are not supported");
	}

	@Override
	@Deprecated
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Player names are not supported");
	}

	@Override
	@Deprecated
	public EconomyResponse depositPlayer(String playerName, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Player names are not supported");
	}

	@Override
	@Deprecated
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Player names are not supported");
	}

	@Override
	@Deprecated
	public boolean has(String playerName, String worldName, double amount) {
		return false;
	}

	@Override
	@Deprecated
	public boolean has(String playerName, double amount) {
		return false;
	}

	@Override
	@Deprecated
	public double getBalance(String playerName, String world) {
		return 0;
	}

	@Override
	@Deprecated
	public double getBalance(String playerName) {
		return 0;
	}

	@Override
	@Deprecated
	public boolean hasAccount(String playerName, String worldName) {
		return false;
	}

	@Override
	@Deprecated
	public boolean hasAccount(String playerName) {
		return false;
	}
}