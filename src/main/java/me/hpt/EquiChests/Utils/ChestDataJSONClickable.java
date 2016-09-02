package me.hpt.EquiChests.Utils;

import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;

public class ChestDataJSONClickable {

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
}
