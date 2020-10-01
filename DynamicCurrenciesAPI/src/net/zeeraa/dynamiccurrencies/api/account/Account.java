package net.zeeraa.dynamiccurrencies.api.account;

import net.zeeraa.dynamiccurrencies.api.currency.Currency;

public class Account {
	private Currency currency;
	private double balance;

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
}