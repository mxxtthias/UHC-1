package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealthCommand implements CommandExecutor {

    private final Game game = Game.getInstance();

    private final String prefix = game.getPrefix();

    private final String mColor = game.getmColor();
    private final String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("health")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        player.sendMessage(sColor + target.getName() + "'s health: " + mColor + Math.round(target.getHealth()) + " ❤");
                    } else {
                        player.sendMessage(ChatColor.RED + args[0] + " is currently offline.");
                    }
                } else {
                    player.sendMessage(sColor + "Your health: " + mColor + Math.round(player.getHealth()) + " ❤");
                }
            }
        }
        return false;
    }
}
