package me.hpt.EquiChests.Commands;

import com.sk89q.minecraft.util.commands.*;
import org.bukkit.command.CommandSender;

public class EquiChestsParentCommand {
	@Command(
			aliases = {"eq", "chest", "equichests"},
			desc = "EquiChests parent command",
			usage = "/equichests help"
	)
	@NestedCommand(EquiChestsCommands.class)
	public static void equiChestsParentCommand(final CommandContext args, CommandSender sender) throws CommandException {

	}
}
