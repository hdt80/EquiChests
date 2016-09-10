package me.hpt.EquiChests.Chests;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class ChestData {
	Vector loc;

	public ChestData(Block b) {
		loc = new Vector(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ());
	}

	public ChestData(Vector v) {
		loc = v;
	}

	/**
	 * Pythagorean distance between two chests
	 *
	 * @param data ChestData to find the distance to
	 * @return The Pythagorean distance between the two blocks
	 */
	public double distanceTo(ChestData data) {
		return loc.distance(data.getLocation());
	}

	/**
	 * Pythoagorean distance between two points
	 *
	 * @param b Block to find the distance to
	 * @return The Pythagorean distance between the two blocks
	 */
	public double distanceTo(Block b) {
		return loc.distance(b.getLocation().toVector());
	}

	public double distanceTo(Vector v) {
		return loc.distance(v);
	}

	/**
	 * Get the number of blocks between two points
	 *
	 * @param b Block to find the distance to
	 * @return The Manhattan distance between the two blocks
	 */
	public int blockDist(Block b) {
		return blockDist(b.getLocation().toVector());
	}

	/**
	 * Get the number of blocks between two points
	 *
	 * @param data Block to find the distance to
	 * @return The Manhattan distance between the two blocks
	 */
	public int blockDist(ChestData data) {
		return blockDist(data.getLocation());
	}

	public int blockDist(Vector v) {
		// TODO: Do this better
		int xdiff = Math.abs(v.getBlockX() - loc.getBlockX());
		int ydiff = Math.abs(v.getBlockY() - loc.getBlockY());
		int zdiff = Math.abs(v.getBlockZ() - loc.getBlockZ());
		return xdiff + ydiff + zdiff;
	}

	/**
	 * Get the Location stored by this Data
	 *
	 * @return Location of this ChestData
	 */
	public Vector getLocation() {
		return loc;
	}

	@Override
	public String toString() {
		return "ChestData{loc=" + loc.toString() + "}";
	}
}