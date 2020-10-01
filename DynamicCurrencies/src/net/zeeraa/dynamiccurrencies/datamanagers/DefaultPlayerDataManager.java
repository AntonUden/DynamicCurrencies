package net.zeeraa.dynamiccurrencies.datamanagers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;

import net.zeeraa.dynamiccurrencies.DynamicCurrencies;
import net.zeeraa.dynamiccurrencies.api.DynamicCurrenciesAPI;
import net.zeeraa.dynamiccurrencies.api.account.Account;
import net.zeeraa.dynamiccurrencies.api.currency.Currency;
import net.zeeraa.dynamiccurrencies.api.implementation.APIImplementation;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerDataManager;
import net.zeeraa.dynamiccurrencies.api.playerdata.PlayerEconomyData;

public class DefaultPlayerDataManager extends PlayerDataManager {
	private Map<UUID, PlayerEconomyData> loadedPlayerData;

	public DefaultPlayerDataManager() {
		loadedPlayerData = new HashMap<>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlayerEconomyData getPlayerEconomyData(UUID uuid) {
		if (loadedPlayerData.containsKey(uuid)) {
			return loadedPlayerData.get(uuid);
		}

		File playerDataFile = getPlayerDataFile(uuid);

		if (DynamicCurrencies.getInstance().isShowDebugMessages()) {
			System.out.println("Trying to read player data file at " + playerDataFile.getPath() + " Exists: " + playerDataFile.exists());
		}

		PlayerEconomyData result;

		if (playerDataFile.exists()) {
			YamlConfiguration data = YamlConfiguration.loadConfiguration(playerDataFile);

			// Check if the configuration file contains any data
			if (data.contains("primary-currency-name")) {
				Currency primaryCurrency = DynamicCurrenciesAPI.getCurrencyDataManager().getCurrency(data.getString("primary-currency-name"));

				// Default to the primary currency of the server if the data fails to load
				if (primaryCurrency == null) {
					primaryCurrency = APIImplementation.getCurrencyDataManager().getPrimaryCurrency();
				}

				List<Account> accounts = (List<Account>) data.getList("accounts");
				
				result = new PlayerEconomyData(uuid, primaryCurrency, accounts);

				loadedPlayerData.put(uuid, result);

				return result;
			}
		}

		result = new PlayerEconomyData(uuid, DynamicCurrenciesAPI.getPrimaryCurrency(), new ArrayList<>());

		loadedPlayerData.put(uuid, result);

		return result;
	}

	@Override
	public boolean unloadPlayerEconomyData(UUID uuid, boolean save) {
		if (save) {
			savePlayerEconomyData(uuid);
		}

		return loadedPlayerData.remove(uuid) != null;
	}

	@Override
	public boolean savePlayerEconomyData(UUID uuid) {
		if (loadedPlayerData.containsKey(uuid)) {
			PlayerEconomyData playerData = getPlayerEconomyData(uuid);

			YamlConfiguration data = new YamlConfiguration();

			data.set("primary-currency-name", playerData.getPrimaryCurrency().getName());
			data.set("accounts", playerData.getAccounts());

			try {
				data.save(getPlayerDataFile(uuid));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	@Override
	public void unloadAll(boolean save) {
		if (save) {
			for (UUID uuid : loadedPlayerData.keySet()) {
				savePlayerEconomyData(uuid);
			}
		}

		loadedPlayerData.clear();
	}

	@Override
	public File getPlayerDataFolder() {
		return DynamicCurrencies.getInstance().getPlayerDataFolder();
	}

	@Override
	public File getPlayerDataFile(UUID uuid) {
		return new File(getPlayerDataFolder().getPath() + File.separator + uuid.toString() + ".yml");
	}
}