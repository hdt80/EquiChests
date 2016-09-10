package me.hpt.EquiChests.Utils;

import me.hpt.EquiChests.Chests.ChestData;
import mkremins.fanciful.FancyMessage;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MessageUtils {

	/**
	 * Send a message to the player containing all the data about a ChestData
	 * @param p Player to send the message to
	 * @param vec Vector to measure the distance from
	 * @param data ChestData to get the data about
	 */
	public static void sendChestDataMessage(Player p, Vector vec, ChestData data) {
		String msg =  new FancyMessage("[").
				color(ChatColor.DARK_GRAY).
				then(data.getLocation().getBlockX() + " " + data.getLocation().getBlockY() + " " + data.getLocation().getBlockZ()).
				color(ChatColor.GRAY).
				tooltip("Click to teleport here").
				command("/tp " + data.getLocation().getBlockX() + " " + data.getLocation().getBlockY() + " " + data.getLocation().getBlockZ()).
				then("]").
				color(ChatColor.DARK_GRAY).
				then(" (" + Math.abs(vec.getBlockX() - data.getLocation().getBlockX())
						+ " " + Math.abs(vec.getBlockY() - data.getLocation().getBlockY	())
						+ " " + Math.abs(vec.getBlockZ() - data.getLocation().getBlockZ()) + ") ").
				color(ChatColor.WHITE).
				tooltip("Block diffs").
				then(String.valueOf((int)data.distanceTo(vec))).
				color(ChatColor.BLUE).
				tooltip("Euclidean dist").
				then(" " + String.valueOf(data.blockDist(vec))).
				color(ChatColor.DARK_PURPLE).
				tooltip("Manhattan dist").
				toJSONString();

		Packet packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(msg), (byte) 1);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
}
