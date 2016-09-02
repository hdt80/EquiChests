package me.hpt.EquiChests;

import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Language {
	private static  Map<String, String> messages = Maps.newHashMap();

	/**
	 * Load the plugin's lang file
	 * @param plugin Plugin instance
	 * @param fileName File where the lang Strings are stored
	 */
	public static void load(Plugin plugin, String fileName) {
		try {
			messages.clear();
			File messagesFile = FileUtils.getFile(plugin.getDataFolder(), fileName);
			if (!messagesFile.exists()) {
				plugin.saveResource(fileName, true);
			}
			List<String> lines = FileUtils.readLines(messagesFile);

			for (String line : lines) {
				String[] split = line.split("=");
				messages.put(split[0], split[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get a String from the lang file
	 * @param key Local name of the String
	 * @param args Arguments that will be filled from the message
	 * @return String formatted with arguments
	 */
	public static String get(String key, Object... args) {
		String msg = "No string found for \"" + key + "\".";
		if (messages.containsKey(key)) {
			msg = messages.get(key);
		}
		for (int i = 0; i < args.length; i++) {
			msg = msg.replace("{" + i + "}", args[i].toString());
		}
		msg = ChatColor.translateAlternateColorCodes('`', msg);
		return msg;
	}

}
