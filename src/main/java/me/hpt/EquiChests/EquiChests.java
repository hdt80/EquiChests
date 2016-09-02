package me.hpt.EquiChests;

import com.google.common.base.Preconditions;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class EquiChests extends JavaPlugin {
	private static EquiChests instance;

	private static HashMap<String, ChestLocationManager> managers; // world name, manager

	private CommandsManager<CommandSender> commands;

	@Override
	public void onEnable() {
		instance = this;
		Logger.setLogger(getLogger());

		Language.load(this, "messages.lang");
		Config.load(this);

		managers = new HashMap<>();

		new WorldListener(this);
		new ChestPlaceListener(this);

		setupCommands();
	}

	@Override
	public void onDisable() {
		for (Map.Entry<String, ChestLocationManager> entry : managers.entrySet()) {
			Config.setChests(entry.getKey(), entry.getValue().getData());
		}
		managers.clear();
	}

	/**
	 * Get the instance of this Plugin
	 * @return Instance of this Plugin
	 */
	public static EquiChests get() {
		return instance;
	}

	/**
	 * Get the ChestLocationManager of this Plugin
	 * @return ChestLocationManager of this Plugin
	 */
	public ChestLocationManager getChestManager(String world) {
		if (!managers.containsKey(world)) {
			Logger.warn("No chest manager for %s loaded!", world);
			loadWorldManager(world);
		}
		return managers.get(world);
	}

	/**
	 * Load a new ChestLocationManager from config. If one doesn't exist, one will be created for it
	 * @param world Name of the world to load
	 */
	public void loadWorldManager(String world) {
		if (managers.containsKey(world)) {
			Logger.warn("World %s already has a ChestLocationManager loaded!", world);
			return;
		}
		ChestLocationManager manager = new ChestLocationManager(world);
		if (Config.hasWorld(world)) {
			manager.loadConfigData(Config.getChests(world));
		}
		managers.put(world, manager);
	}

	/**
	 * Check if a world's ChestLocationManager is loaded, or if one exists
	 * @param world World name to check
	 * @return If the String world has a key in the managers HashMap
	 */
	public boolean worldManagerIsLoaded(String world) {
		return managers.containsKey(world);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		try {
			this.commands.execute(cmd.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(Language.get("command-noperm"));
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(Language.get("command-numberformatexception"));
			} else if (e.getCause() instanceof IllegalArgumentException) {
				sender.sendMessage(ChatColor.RED + e.getMessage().replace("java.lang.IllegalArgumentException: ", ""));
			} else if (e.getCause() instanceof NullPointerException && e.getCause().getStackTrace()[0].getClassName().equals(Preconditions.class.getName())) {
				sender.sendMessage(ChatColor.RED + e.getCause().getMessage());
			} else {
				sender.sendMessage(Language.get("command-exception"));
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}
		return true;
	}

	private void setupCommands() {
		this.commands = new CommandsManager<CommandSender>() {
			@Override
			public boolean hasPermission(CommandSender sender, String perm) {
				return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
			}
		};

		CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
	}

}
