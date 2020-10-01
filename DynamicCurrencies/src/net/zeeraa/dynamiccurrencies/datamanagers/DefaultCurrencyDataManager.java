package net.zeeraa.dynamiccurrencies.datamanagers;

import java.util.HashMap;
import java.util.Map;

import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.dynamiccurrencies.api.currency.CurrencyDataManager;

public class DefaultCurrencyDataManager extends CurrencyDataManager {
	private Map<String, Currency> currencies;
	private Currency primaryCurrency;

	public void CurrencyDataManager() {
		currencies = new HashMap<>();
		primaryCurrency = null;
	}

	@Override
	public Map<String, Currency> getCurrencies() {
		return currencies;
	}

	@Override
	public Currency getPrimaryCurrency() {
		return primaryCurrency;
	}

	@Override
	public void loadCurrencies() {

	}
}