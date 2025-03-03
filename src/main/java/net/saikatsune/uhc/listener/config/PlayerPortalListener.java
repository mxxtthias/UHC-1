package net.saikatsune.uhc.listener.config;

import net.saikatsune.uhc.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerPortalListener implements Listener {

    private final Game game = Game.getInstance();

    private final String prefix = game.getPrefix();

    @EventHandler
    public void handlePlayerPortalEvent(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        if(!game.getConfigManager().isNether()) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Nether is currently disabled.");
        } else {
            if(!game.getSpectators().contains(player)) {
                if(event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
                    if(event.getFrom().getWorld().getName().equalsIgnoreCase("uhc_world")) {

                        double x = player.getLocation().getX() / 8;
                        double y = player.getLocation().getY() / 8;
                        double z = player.getLocation().getZ() / 8;

                        Location location = new Location(Bukkit.getWorld("uhc_nether"), x, y, z);

                        event.setTo(event.getPortalTravelAgent().findOrCreate(location));
                    } else if(event.getFrom().getWorld().getName().equalsIgnoreCase("uhc_nether")) {

                        double x = player.getLocation().getX() * 8;
                        double y = player.getLocation().getY() * 8;
                        double z = player.getLocation().getZ() * 8;

                        Location location = new Location(Bukkit.getWorld("uhc_world"), x, y, z);
                        event.setTo(event.getPortalTravelAgent().findOrCreate(location));
                    }
                }
            } else {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You cannot access the nether as spectator.");
            }
        }
    }

}
