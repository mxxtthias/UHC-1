package net.saikatsune.uhc.commands;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListCommand implements CommandExecutor {

    private final Game game = Game.getInstance();

    private final String prefix = game.getPrefix();

    private final String mColor = game.getmColor();
    private final String sColor = game.getsColor();

    @Override
    public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("list")) {

            List<String> moderators = new ArrayList<>();

            for (Player allSpectators : game.getSpectators()) {
                if(allSpectators.hasPermission("uhc.host")) {
                    moderators.add(allSpectators.getName());
                }
            }

            String[] stringArray = moderators.toArray(new String[moderators.size()]);

            player.sendMessage("§8§m---------------------------");
            player.sendMessage(sColor + "Game Host: " + mColor + game.getGameHost());
            player.sendMessage("");
            player.sendMessage(sColor + "Online Players: " + mColor + Bukkit.getOnlinePlayers().size());
            player.sendMessage(sColor + "Alive Players: " + mColor + game.getPlayers().size());
            player.sendMessage("");
            player.sendMessage(sColor + "Spectators: " + mColor + game.getSpectators().size());
            player.sendMessage(sColor + "Moderators: " + mColor + Arrays.toString(stringArray).
                    replace("[", "").replace("]", ""));
            player.sendMessage("§8§m---------------------------");
        }
        return false;
    }
}
