package net.zeeraa.dynamiccurrencies.api.playerdata;

import java.util.List;
import java.util.UUID;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;

public class PlayerEconomyData {
	private UUID playerUuid;
	private Currency primaryCurrency;

	private List<Account> accounts;

	public PlayerEconomyData(UUID playerUuid, Currency primaryCurrency, List<Account> accounts) {
		this.playerUuid = playerUuid;
		this.primaryCurrency = primaryCurrency;

		this.accounts = accounts;
	}

	/**
	 * Get the {@link UUID} of the player
	 * 
	 * @return The player {@link UUID}
	 */
	public UUID getPlayerUuid() {
		return playerUuid;
	}

	/**
	 * Get the primary {@link Currency} that the player should receive when getting
	 * payments thru vault
	 * <p>
	 * If this has not been set or the old {@link Currency} no longer exist this
	 * will return the servers default {@link Currency}
	 * 
	 * @return The primary {@link Currency} this player is using
	 */
	public Currency getPrimaryCurrency() {
		return primaryCurrency;
	}

	/**
	 * Set the primary {@link Currency} that the player should receive when getting
	 * payments thru vault
	 * 
	 * @param primaryCurrency The new {@link Currency} to use
	 */
	public void setPrimaryCurrency(Currency primaryCurrency) {
		this.primaryCurrency = primaryCurrency;
	}

	/**
	 * Get a list containing all the players {@link Account}s
	 * 
	 * @return List with {@link Account}s
	 */
	public List<Account> getAccounts() {
		return accounts;
	}

	/**
	 * Save the {@link PlayerEconomyData} to a file
	 * <p>
	 * This calls {@link PlayerDataManager#savePlayerEconomyData(UUID)} with the
	 * {@link UUID} from {@link PlayerEconomyData#getPlayerUuid()}
	 * <p>
	 * See {@link PlayerDataManager#savePlayerEconomyData(UUID)} for more info about
	 * the behavior and return value of this function
	 * 
	 * @return See {@link PlayerDataManager#savePlayerEconomyData(UUID)}
	 */
	public boolean save() {
		return DynamicCurrenciesAPI.getPlayerDataManager().savePlayerEconomyData(getPlayerUuid());
	}
}