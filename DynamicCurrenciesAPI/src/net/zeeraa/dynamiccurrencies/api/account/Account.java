package net.zeeraa.dynamiccurrencies.api.account;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.NumberConversions;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerEconomyData;

/**
 * Accounts are used to store multiple {@link Currency} in a single
 * {@link PlayerEconomyData} file
 * 
 * @author Zeeraa
 */
public class Account implements ConfigurationSerializable {
	protected Currency currency;
	protected double balance;

	public Account(Currency currency, double balance) {
		this.currency = currency;
		this.balance = balance;
	}

	/**
	 * Get the {@link Currency} for this account
	 * 
	 * @return The {@link Currency} used
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Get the balance of the account
	 * 
	 * @return The balance of the account
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Set the balance of the account
	 * 
	 * @param balance The new balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> serialized = new HashMap<>();
        
		serialized.put("currency", getCurrency().getName());
        serialized.put("balance", getBalance());
        
        return serialized;
	}

	public static Account deserialize(Map<String, Object> serialized) {
		Currency currency = DynamicCurrenciesAPI.getCurrencyDataManager().getCurrency((String) serialized.get("currency"));
		double balance = NumberConversions.toDouble(serialized.get("balance"));
		
        return new Account(currency, balance);
    }
}