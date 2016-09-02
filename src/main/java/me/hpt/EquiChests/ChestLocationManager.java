package me.hpt.EquiChests;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;

public class ChestLocationManager {

	private class ChestData {
		Location loc;

		public ChestData(Block b) {
			loc = b.getLocation();
		}

		/**
		 * Pythagorean distance between two chests
		 * @param data ChestData to find the distance to
		 * @return The Pythagorean distance between the two blocks
		 */
		double distanceTo(ChestData data) {
			return loc.distance(data.getLocation());
		}

		/**
		 * Pythoagorean distance between two points
		 * @param b Block to find the distance to
		 * @return The Pythagorean distance between the two blocks
		 */
		double distanceTo(Block b) {
			return loc.distance(b.getLocation());
		}

		/**
		 * Get the Location stored by this Data
		 * @return Location of this ChestData
		 */
		Location getLocation() {
			return loc;
		}
	}

	public ChestLocationManager() {}

	private ArrayList<ChestData> data = new ArrayList<>();

	/**
	 * Add a new Block to manage to this Manager
	 * @param b Block to add
	 */
	public void addBlock(Block b) {
		if (!validBlock(b)) {
			return;
		}
		if (getBlock(b.getLocation()) != null) {
			return;
		}
		data.add(new ChestData(b));
	}

	/**
	 * Remove a block from the manager
	 * @param b Block to remove
	 */
	public void removeBlock(Block b) {
		if (!validBlock(b) && (getBlock(b.getLocation()) != null)) {
			return;
		}
		data.remove(new ChestData(b));
	}

	/**
	 * Return the Location of a Chest managed by this Manager, if one exists
	 * @param loc Location to find if a Chest exists
	 * @return Location of the block, or null if no chest exists there
	 */
	public Location getBlock(Location loc) {
		for (ChestData cdata : data) {
			if (cdata.getLocation() == loc) {
				return loc;
			}
		}
		return null;
	}

	/**
	 * Get the distances from the block to all blocks
	 * @param b Block to check the distance from
	 * @return An array of all the distances from the block
	 */
	public double[] getDistances(Block b) {
		double[] closest = new double[data.size()];
		if (!validBlock(b)) {
			return closest;
		}

		for (int i = 0; i < data.size(); ++i) {
			closest[i] = data.get(i).distanceTo(b);
		}

		Arrays.sort(closest);

		return closest;
	}

	/**
	 * Only Chests or Trapped chests can be added to ChestManagers
	 * @param b Block to check the type of
	 * @return If the block is a valid block that can be added
	 */
	private boolean validBlock(Block b) {
		return b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST;
	}

}
