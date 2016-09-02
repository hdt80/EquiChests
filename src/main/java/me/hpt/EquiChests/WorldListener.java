package me.hpt.EquiChests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener implements Listener {
	private EquiChests plugin;

	public WorldListener(EquiChests p) {
		this.plugin = p;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void worldLoad(WorldLoadEvent e) {
		String worldName = e.getWorld().getName();
		Logger.info("WorldLoadEvent %s", worldName);
		if (!EquiChests.get().worldManagerIsLoaded(worldName)) {
			Logger.info("Loading a ChestLocationManager for %s", worldName);
			EquiChests.get().loadWorldManager(worldName);
		}
	}

	@EventHandler
	public void worldInit(WorldInitEvent e) {
		String worldName = e.getWorld().getName();
		Logger.info("WorldInitEvent %s", worldName);
		if (!EquiChests.get().worldManagerIsLoaded(worldName)) {
			Logger.info("Loading a ChestLocationManager for %s", worldName);
			EquiChests.get().loadWorldManager(worldName);
		}
	}

	@EventHandler
	public void worldUnload(WorldUnloadEvent e) {

	}

}
