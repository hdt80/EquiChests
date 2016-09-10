package me.hpt.EquiChests.Listeners;

import me.hpt.EquiChests.EquiChests;
import me.hpt.EquiChests.Language;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class NetherStarUseListener implements Listener {

	EquiChests plugin;

	public NetherStarUseListener(EquiChests p) {
		plugin = p;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onItemUse(PlayerInteractEvent e) {
		if (e.getItem() == null || e.getItem().getType() != Material.NETHER_STAR || e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		p.sendMessage(Language.get("spawnpoint-coords", b.getX(), b.getY() + 1, b.getZ()));
	}
}
