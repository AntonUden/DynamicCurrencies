package net.zeeraa.dynamiccurrencies.api.currency;

public class Currency {
	private String name;
	private String displayName;
	private String shortName;

	private double exchangeRate;

	public Currency(String name, String displayName, String shortName, double exchangeRate) {
		this.name = name;
		this.displayName = displayName;
		this.shortName = shortName;
		this.exchangeRate = exchangeRate;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getShortName() {
		return shortName;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof Currency) {
				return this.getName().equalsIgnoreCase(((Currency) obj).getName());
			}
			
			if(obj instanceof String) {
				return this.getName().equalsIgnoreCase((String) obj);
			}
		}
		
		return false;
	}
}