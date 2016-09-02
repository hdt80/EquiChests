package me.hpt.EquiChests;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
		Block placed = e.getBlockPlaced();
		Player p = e.getPlayer();
		if (p.isSneaking()) {
			p.sendMessage(Language.get("chests-new", placed.getLocation().getBlockX(), placed.getLocation().getBlockY(),
					placed.getLocation().getBlockZ(), placed.getLocation().getWorld().getName()));
			EquiChests.get().getChestManager(placed.getWorld().getName()).addBlock(placed);
		} else {
			double[] blocks = EquiChests.get().getChestManager(placed.getWorld().getName()).getDistances(placed);

			// Only the 3 closest blocks
			for (int i = 0; i < Math.min(blocks.length, 3); ++i) {
				p.sendMessage((i + 1) + " : " + (int)blocks[i]);
			}

			e.setCancelled(true);
		}
	}

	@EventHandler
	public void chestBreakListener(BlockBreakEvent e) {
		if (e.getBlock().getType() != Material.CHEST && e.getBlock().getType() != Material.TRAPPED_CHEST) {
			return;
		}

		Block b = e.getBlock();
		Player p = e.getPlayer();

		if (p.isSneaking()) {
			p.sendMessage(Language.get("chests-removed", b.getLocation().getBlockX(), b.getLocation().getBlockY(),
					b.getLocation().getBlockZ(), b.getLocation().getWorld().getName()));
			EquiChests.get().getChestManager(b.getWorld().getName()).removeBlock(b);
		} else {
			double[] blocks = EquiChests.get().getChestManager(b.getWorld().getName()).getDistances(b);

			// Only the 3 closest blocks
			for (int i = 0; i < Math.min(blocks.length, 3); ++i) {
				p.sendMessage((i + 1) + " : " + (int)blocks[i]);
			}

			e.setCancelled(true);
		}
	}
}
