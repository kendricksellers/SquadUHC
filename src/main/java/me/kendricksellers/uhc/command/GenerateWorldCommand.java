package me.kendricksellers.uhc.command;

import me.kendricksellers.uhc.match.Match;
import me.kendricksellers.uhc.util.ChatUtils;
import me.kendricksellers.uhc.util.UHCMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GenerateWorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("generate") && sender.hasPermission("squaduhc.command.generate")
                && args.length == 0) {
            if (Match.getInstance().isGenerated()) {
                ChatUtils.message((Player) sender, UHCMessage.COMMAND_GENERATE_GENERATED);
                return true;
            }
            Match.getInstance().generateWorld();
        }
        return true;
    }
}
