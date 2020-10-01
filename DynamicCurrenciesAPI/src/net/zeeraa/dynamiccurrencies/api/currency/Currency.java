package net.zeeraa.dynamiccurrencies.api.currency;

import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;

public class Currency {
	protected String name;
	protected String displayNameSingular;
	protected String displayNamePlural;
	protected String shortName;

	protected double exchangeRate;

	public Currency(String name, String displayNameSingular, String displayNamePlural, String shortName, double exchangeRate) {
		this.name = name;
		this.displayNameSingular = displayNameSingular;
		this.displayNamePlural = displayNamePlural;
		this.shortName = shortName;
		this.exchangeRate = exchangeRate;
	}

	/**
	 * Get the internal name for the currency
	 * 
	 * @return The internal currency name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the display name of the currency in singular
	 * 
	 * @return The currency display name in singular
	 */
	public String getDisplayNameSingular() {
		return displayNameSingular;
	}

	/**
	 * Set the new singular display name of the currency
	 * <p>
	 * This wont be saved to the currencies.yml file
	 * 
	 * @param displayNameSingular The new singular display name
	 */
	public void setDisplayNameSingular(String displayNameSingular) {
		this.displayNameSingular = displayNameSingular;
	}

	/**
	 * Get the display name of the currency in plural
	 * 
	 * @return The currency display name in plural
	 */
	public String getDisplayNamePlural() {
		return displayNamePlural;
	}

	/**
	 * Set the new plural display name of the currency
	 * <p>
	 * This wont be saved to the currencies.yml file
	 * 
	 * @param displayNamePlural The new plural display name
	 */
	public void setDisplayNamePlural(String displayNamePlural) {
		this.displayNamePlural = displayNamePlural;
	}

	/**
	 * Get the short name of the currency
	 * 
	 * @return The short name of the currency
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Set the short name for the currency
	 * <p>
	 * This wont be saved to the currencies.yml file
	 * 
	 * @param shortName The new short name
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Get the exchange rate to the vault economy
	 * 
	 * @return exchange rate
	 */
	public double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * Set the exchange rate of the currency
	 * <p>
	 * This will return false if this is the primary currency since the exchange
	 * rate of the primary currency will always be 1
	 * <p>
	 * This wont be saved to the currencies.yml file
	 * 
	 * @param exchangeRate The new exchange rate
	 * @return <code>true</code> on success
	 */
	public boolean setExchangeRate(double exchangeRate) {
		return this.setExchangeRate(exchangeRate, false);
	}

	/**
	 * Set the exchange rate of the currency
	 * <p>
	 * This will return false if this is the primary currency since the exchange
	 * rate of the primary currency will always be 1
	 * <p>
	 * This wont be saved to the currencies.yml file
	 * 
	 * @param exchangeRate The new exchange rate
	 * @param force        <code>true</code> to force update even if the currency is
	 *                     the primary currency for the server. Only use this if you
	 *                     know what you are doing!
	 * @return <code>true</code> on success
	 */
	public boolean setExchangeRate(double exchangeRate, boolean force) {
		if (isPrimary() || !force) {
			return false;
		}

		this.exchangeRate = exchangeRate;
		return true;
	}

	/**
	 * Check if this is the primary currency of the server
	 * 
	 * @return <code>true</code> if this is the primary currency
	 */
	public boolean isPrimary() {
		return this.equals(DynamicCurrenciesAPI.getPrimaryCurrency());
	}

	/**
	 * Check if this {@link Currency} is equal to another {@link Currency} or string
	 * <p>
	 * If a string is passed this will compare {@link Currency#getName()} and The
	 * string using {@link String#equalsIgnoreCase(String)}
	 * 
	 * @param obj An instance of {@link Currency} or a string to compare with
	 * 
	 * @return True if this {@link Currency} and the object is matching
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Currency) {
				return this.getName().equalsIgnoreCase(((Currency) obj).getName());
			}

			if (obj instanceof String) {
				return this.getName().equalsIgnoreCase((String) obj);
			}
		}

		return false;
	}
	
	/**
	 * Get a string with info used to debug currencies
	 * @return String with debug info
	 */
	public String getDebugData() {
		return "name: " + name + " | displayNameSingular: " + displayNameSingular + " | displayNamePlural: " + displayNamePlural + " | shortName: " + shortName + " | exchangeRate: "  + exchangeRate;
	}
}