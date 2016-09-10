package me.hpt.EquiChests.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import me.hpt.EquiChests.*;
import me.hpt.EquiChests.Utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EquiChestsCommands {
	@Command(
			aliases = {},
			desc = "",
			usage = "",
			min = 0,
			max = 0
	)
	public static void NAME (final CommandContext args, final CommandSender sender) throws CommandException {

	}

	@Command(aliases = {"reload"},
			desc = "Reload the config files",
			usage = "reload",
			min = 0,
			max = 0
	)
	public static void reloadConfig(final CommandContext args, final CommandSender sender) throws CommandException {
		Config.load(EquiChests.get());
		Language.load(EquiChests.get(), "messages.lang");
		sender.sendMessage(Language.get("command-configreload-done"));
	}

	@Command(aliases = {"check", "ck"},
			desc = "Check to see if each chest is a world still exists",
			usage = "<World name>",
			min = 0,
			max = 1,
			flags = "rR"
	)
	public static void checkChests(final CommandContext args, CommandSender sender) throws CommandException {
		String worldName = "";
		Player p = null;

		if (!(sender instanceof Player)) {
			if (args.argsLength() != 1) {
				sender.sendMessage(Language.get("command-consoleprovidearg"));
				return;
			} else {
				worldName = args.getString(1);
			}
		} else {
			p = (Player) sender;
			worldName = (args.argsLength() == 1 ? args.getString(1) : p.getWorld().getName());
		}

		World world = Bukkit.getWorld(worldName);
		ChestLocationManager manager = EquiChests.get().getChestManager(worldName);

		if (args.hasFlag('r') || args.hasFlag('R')) {
			sender.sendMessage(Language.get("chests-puttingmissing"));
		} else {
			sender.sendMessage(Language.get("command-howtocheck"));
		}

		for (Vector v : manager.getData()) {
			// TODO: Ew
			// Check if the block at the location stored in ChestData is a chest or a trapped chest
			if (world.getBlockAt(v.getBlockX(), v.getBlockY(), v.getBlockZ()).getType() != Material.CHEST
					&& world.getBlockAt(v.getBlockX(), v.getBlockY(), v.getBlockZ()).getType() != Material.TRAPPED_CHEST) {
				sender.sendMessage(Language.get("chests-notfound", v.getBlockX(), v.getBlockY(), v.getBlockZ(), worldName));

				if (args.hasFlag('r') || args.hasFlag('R')) {
					world.getBlockAt(v.getBlockX(), v.getBlockY(), v.getBlockZ()).setType(Material.DIAMOND_BLOCK);
				}
			}
		}

	}

	@Command(aliases = {"list", "ls"},
			desc = "List each chest stored in a chest manager",
			usage = "<World name>",
			min = 0,
			max = 1
	)
	public static void listChests(final CommandContext args, CommandSender sender) throws CommandException {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Language.get("command-playersonly"));
			return;
		}

		Player p = (Player) sender;
		String worldName = (args.argsLength() == 1 ? args.getString(1) : p.getWorld().getName());

		sender.sendMessage(Language.get("chests-listintro", worldName));

		ChestLocationManager manager = EquiChests.get().getChestManager(worldName);
		ChestData[] sorted = manager.sort(p.getLocation().toVector());
		// List each ChestData in order of closest
		for (ChestData v : sorted) {
			MessageUtils.sendChestDataMessage(p, p.getLocation().toVector(), v);
		}
	}
}
