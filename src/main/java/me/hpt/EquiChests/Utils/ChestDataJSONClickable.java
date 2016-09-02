package me.hpt.EquiChests.Utils;

import me.hpt.EquiChests.ChestData;
import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;

public class ChestDataJSONClickable {

	/**
	 * Get a JSON message that allows players to click on this message to teleport to this block
	 *
	 * @param x X coord to tp to
	 * @param y Y coord to tp to
	 * @param z Z coord to tp to
	 * @return JSON String
	 */
	public static String getLocationClickableMessage(int x, int y, int z) {
		return new FancyMessage("[")
				.color(ChatColor.DARK_GRAY)
				.then(x + " " + y + " " + z)
				.color(ChatColor.GRAY)
				.tooltip("Click to teleport here")
				.command("/tp " + x + " " + y + " " + z)
				.then("]")
				.color(ChatColor.DARK_GRAY)
				.toJSONString();
	}

	/**
	 * Get a JSON message that allows players to click on this message to teleport to this block
	 * @param data Data to create the clickable message of
	 * @return JSON String
	 */
	public static String getChestDataClickableMessage(ChestData data) {
		return getLocationClickableMessage(data.getLocation().getBlockX(),
				data.getLocation().getBlockY(), data.getLocation().getBlockZ());
	}
}
