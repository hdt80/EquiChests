package me.hpt.EquiChests.Chests;

import me.hpt.EquiChests.Utils.Sorting;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChestLocationManager {
	public ChestLocationManager(String world) {
		this.world = world;
	}

	private ArrayList<ChestData> data = new ArrayList<>();
	private String world;

	/**
	 * Get a List of Data that this ChestLocationManager contains
	 * @return List of Vectors with each Vector a chest block
	 */
	public List<Vector> getData() {
		List<Vector> toReturn = new ArrayList<>();
		for (ChestData cdata : data) {
			toReturn.add(cdata.getLocation());
		}
		return toReturn;
	}

	/**
	 * Add a new Block to manage to this Manager
	 * @param b Block to add
	 */
	public void addBlock(Block b) {
		if (!validBlock(b)) {
			return;
		}
		if (chestAt(b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ())) {
			return;
		}
		data.add(new ChestData(b));
	}

	/**
	 * Used to load data from the config
	 * @param configData List of Vectors to load into this manager
	 */
	public void loadConfigData(List<Vector> configData) {
		for (Vector v : configData) {
			data.add(new ChestData(v));
		}
	}

	/**
	 * Check if there is a chest stored in data
	 * @param x X coord
	 * @param y Y coord
	 * @param z Z coord
	 * @return If there is a chest at the coords
	 */
	public boolean chestAt(int x, int y, int z) {
		for (Vector v : getData()) {
			if (v.getBlockX() == x
					&& v.getBlockY() == y
					&& v.getBlockZ() == z) {

				return true;
			}
		}
		return false;
	}

	/**
	 * Remove a block from the manager
	 * @param b Block to remove
	 */
	public void removeBlock(Block b) {
		if (!validBlock(b) && (getBlock(b.getLocation().toVector()) != null)) {
			return;
		}
		for (int i = 0; i < data.size(); ++i) {
			if (data.get(i).getLocation().getBlockX() == b.getLocation().getBlockX()
					&& data.get(i).getLocation().getBlockY() == b.getLocation().getBlockY()
					&& data.get(i).getLocation().getBlockZ() == b.getLocation().getBlockZ()) {
				data.remove(i);
				return;
			}
		}
	}

	/**
	 * Return the Location of a Chest managed by this Manager, if one exists
	 * @param loc Location to find if a Chest exists
	 * @return Location of the block, or null if no chest exists there
	 */
	public Vector getBlock(Vector loc) {
		for (ChestData cdata : data) {
			if (cdata.getLocation() == loc) {
				return loc;
			}
		}
		return null;
	}

	/**
	 * Sort all the data based on the pythagorean distance to loc
	 * @param loc Location to measure the distance from
	 * @return ChestData sorted from smallest to largest
	 */
	public ChestData[] sort(Vector loc) {
		ChestData[] toSort = new ChestData[data.size()];
		for (int i = 0; i < data.size(); ++i) {
			toSort[i] = data.get(i);
		}
		Sorting.startSort(loc, toSort);

		return toSort;
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
		return (b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST)
				&& (b.getWorld().getName().equals(world));
	}

}
