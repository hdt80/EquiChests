package me.hpt.EquiChests;

import org.bukkit.plugin.java.JavaPlugin;

public class EquiChests extends JavaPlugin {
	private static EquiChests instance;
	private static ChestLocationManager chestLocationManager;

	@Override
	public void onEnable() {
		chestLocationManager = new ChestLocationManager();

		new ChestPlaceListener(this);
	}

	@Override
	public void onDisable() {

	}

	/**
	 * Get the instance of this Plugin
	 * @return Instance of this Plugin
	 */
	public static EquiChests get() {
		return instance;
	}

	/**
	 * Get the ChestLocationManager of this Plugin
	 * @return ChestLocationManager of this Plugin
	 */
	public static ChestLocationManager getChestManager() {
		return chestLocationManager;
	}
}
