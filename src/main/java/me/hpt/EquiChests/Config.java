package me.hpt.EquiChests;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {

	private static FileConfiguration config;
	private static final String CHEST_FIELD = ".chests";

	/**
	 * Load the plugin's yaml file
	 * @param p Plugin's instance
	 */
	public static void load(EquiChests p) {
		p.saveDefaultConfig();
		config = p.getConfig();
	}

	/**
	 * Save the loaded config file to the disk
	 */
	private static void save() {
		try {
			config.save(new File(EquiChests.get().getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all the chests that a world has
	 * @param worldName Name of the world to get the chests of
	 * @return A List of Vectors, each Vector being a chest
	 */
	public static List<Vector> getChests(String worldName) {
		if (!hasWorld(worldName)) {
			config.createSection(worldName + CHEST_FIELD);
		}
		// TODO: Is there a way to check the type at runtime?
		return (List<Vector>)config.getList(worldName + CHEST_FIELD);
	}

	/**
	 * Add a new chest to the config section
	 * @param worldName Name of the world
	 * @param loc Location of the chest to add
	 */
	public static void addChest(String worldName, Location loc) {
		if (!hasWorld(worldName)) {
			config.createSection(worldName + CHEST_FIELD);
		}
		List<Vector> curr = getChests(worldName);
		curr.add(new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
		setChests(worldName, curr);
	}

	/**
	 * Set the chests of a world
	 * @param worldName Name of the world
	 * @param chests List of Vectors that contain where all the chests are
	 */
	public static void setChests(String worldName, List<Vector> chests) {
		if (!hasWorld(worldName)) {
			config.createSection(worldName + CHEST_FIELD);
		}
		config.set(worldName + CHEST_FIELD, chests);
		save();
	}

	/**
	 * Check if the config has a world's chests saved
	 * @param worldName Name of the world
	 * @return If the config has the world's chests saved
	 */
	public static boolean hasWorld(String worldName) {
		return config.contains(worldName + CHEST_FIELD);
	}

}
