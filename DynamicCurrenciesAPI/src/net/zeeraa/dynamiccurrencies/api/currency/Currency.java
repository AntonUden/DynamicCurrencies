package net.zeeraa.dynamiccurrencies.api.currency;

public class Currency {
	private String name;
	private String displayNameSingular;
	private String displayNamePlural;
	private String shortName;

	private double exchangeRate;

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
	 * Get the display name of the currency in plural
	 * 
	 * @return The currency display name in plural
	 */
	public String getDisplayNamePlural() {
		return displayNamePlural;
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
	 * Get the exchange rate to the vault economy
	 * 
	 * @return exchange rate
	 */
	public double getExchangeRate() {
		return exchangeRate;
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
}