package net.zeeraa.dynamiccurrencies.datamanagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import net.zeeraa.dynamiccurrencies.DynamicCurrencies;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.dynamiccurrencies.api.currency.CurrencyDataManager;

public class DefaultCurrencyDataManager extends CurrencyDataManager {
	private Map<String, Currency> currencies;
	private Currency primaryCurrency;

	public DefaultCurrencyDataManager() {
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
	public boolean loadCurrencies() {
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(DynamicCurrencies.getInstance().getCurrenciesFile());

		if (!cfg.contains("primary-currency") || !cfg.contains("currencies")) {
			DynamicCurrencies.getInstance().getLogger().severe("currencies.yml does not contain a valid currency configuration");
			return false;
		}

		ConfigurationSection cfgCurrencies = cfg.getConfigurationSection("currencies");

		List<Currency> readCurrencies = new ArrayList<Currency>();

		for (String cName : cfgCurrencies.getKeys(false)) {
			ConfigurationSection cfgCurrency = cfgCurrencies.getConfigurationSection(cName);

			String displayNameSingular = cfgCurrency.getString("display-name-singular");
			String displayNamePlural = cfgCurrency.getString("display-name-plural");
			String shortName = cfgCurrency.getString("short-name");

			double exchangeRate = cfgCurrency.getDouble("exchange-rate");

			Currency currency = new Currency(cName, displayNameSingular, displayNamePlural, shortName, exchangeRate);

			readCurrencies.add(currency);
		}

		Map<String, Currency> newCurrenciesMap = new HashMap<String, Currency>();

		if (DynamicCurrencies.getInstance().isShowDebugMessages()) {
			System.out.println("Currency map: " + currencies);
		}

		for (Currency currency : readCurrencies) {
			if (DynamicCurrencies.getInstance().isShowDebugMessages()) {
				System.out.println("---------------------------------");
				System.out.println(currency);
				System.out.println(currency.getDebugData());
			}

			Currency currencyToAdd;

			if (currencies.containsKey(currency.getName().toLowerCase())) {
				// Update existing
				currencyToAdd = currencies.get(currency.getName().toLowerCase());

				currencyToAdd.setDisplayNamePlural(currency.getDisplayNamePlural());
				currencyToAdd.setDisplayNameSingular(currency.getDisplayNameSingular());
				currencyToAdd.setShortName(currency.getShortName());

				// Force the change and fix any issues later
				currencyToAdd.setExchangeRate(currency.getExchangeRate(), true);
			} else {
				// Create new
				currencyToAdd = currency;
			}

			newCurrenciesMap.put(currency.getName().toLowerCase(), currencyToAdd);

			currencies = newCurrenciesMap;
		}

		Currency newPrimaryCurrency = getCurrency(cfg.getString("primary-currency").toLowerCase());

		if (newPrimaryCurrency == null) {
			DynamicCurrencies.getInstance().getLogger().severe("Primary currency " + cfg.getString("primary-currency") + " could not be found");
			return false;
		}

		if (newPrimaryCurrency.getExchangeRate() != 1) {
			DynamicCurrencies.getInstance().getLogger().warning("Primary currency " + newPrimaryCurrency.getName() + " does not have an exchange rate of 1 configured. The server will change the exchange rate to 1 to prevent problems");
			newPrimaryCurrency.setExchangeRate(1, true);
		}

		primaryCurrency = newPrimaryCurrency;

		return true;
	}
}