package net.zeeraa.dynamiccurrencies.api.playerdata;

import java.util.List;
import java.util.UUID;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;

public class PlayerEconomyData {
	protected UUID playerUuid;
	protected Currency primaryCurrency;

	protected List<Account> accounts;

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
	 * Get an account used to store a type of currency
	 * <p>
	 * If the player does not have that type of currency the account will be created
	 * for that player
	 * 
	 * @param currency The {@link Currency} to get or create an account for
	 * @return an account used to store a type of currency
	 * @throws NullPointerException if the currency is <code>null</code>
	 */
	public Account getAccount(Currency currency) {
		if (currency == null) {
			throw new NullPointerException("Can't get account for currency: null");
		}

		for (Account account : accounts) {
			if (account.getCurrency().equals(currency)) {
				return account;
			}
		}

		Account newAccount = new Account(currency, 0);

		accounts.add(newAccount);

		return newAccount;
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