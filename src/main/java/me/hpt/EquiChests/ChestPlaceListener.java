package me.hpt.EquiChests;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ChestPlaceListener implements Listener {

	EquiChests plugin;

	public ChestPlaceListener(EquiChests p) {
		this.plugin = p;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void chestPlaceListener(BlockPlaceEvent e) {
		if (e.getBlockPlaced().getType() != Material.CHEST && e.getBlockPlaced().getType() != Material.TRAPPED_CHEST) {
			return;
		}
		if (e.getPlayer().isSneaking()) {
			e.getPlayer().sendMessage("Placed a new chest at (" + e.getBlockPlaced().getLocation().getBlockX() + ", " +
					e.getBlockPlaced().getLocation().getBlockY() + ", " + e.getBlockPlaced().getLocation().getBlockZ() + ")");
			EquiChests.getChestManager().addBlock(e.getBlockPlaced());
		} else {
			Block placed = e.getBlockPlaced();
			double[] blocks = EquiChests.getChestManager().getDistances(placed);

			// Only the 3 closest blocks
			for (int i = 0; i < Math.min(blocks.length, 3); ++i) {
				e.getPlayer().sendMessage((i + 1) + " : " + blocks[i]);
			}

			e.setCancelled(true);
		}
	}

	@EventHandler
	public void chestBreakListener(BlockBreakEvent e) {
		if (e.getBlock().getType() != Material.CHEST && e.getBlock().getType() != Material.TRAPPED_CHEST) {
			return;
		}

		if (e.getPlayer().isSneaking()) {
			e.getPlayer().sendMessage("Removed the chest at (" + e.getBlock().getLocation().getBlockX() + " ,"
					+ e.getBlock().getLocation().getBlockY() + ", " + e.getBlock().getLocation().getBlockZ() + ")");
			EquiChests.getChestManager().removeBlock(e.getBlock());
		} else {
			double[] blocks = EquiChests.getChestManager().getDistances(e.getBlock());

			// Only the 3 closest blocks
			for (int i = 0; i < Math.min(blocks.length, 3); ++i) {
				e.getPlayer().sendMessage((i + 1) + " : " + blocks[i]);
			}

			e.setCancelled(true);
		}
	}
}
