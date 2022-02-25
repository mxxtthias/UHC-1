package net.saikatsune.uhc.tasks;

import net.saikatsune.uhc.Game;
import net.saikatsune.uhc.handler.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class TimeTask {

    private final Game game = Game.getInstance();

    private final String prefix = game.getPrefix();

    private final String mColor = game.getmColor();
    private final String sColor = game.getsColor();

    private int taskID;

    private int uptimeHours;
    private int uptimeMinutes;
    private int uptimeSeconds;

    private int borderMinutes;

    public int getNextBorder() {
        if(game.getConfigManager().getBorderSize() == 3500) {
            return 3000;
        } else if(game.getConfigManager().getBorderSize() == 3000) {
            return 2500;
        } else if(game.getConfigManager().getBorderSize() == 2500) {
            return 2000;
        } else if(game.getConfigManager().getBorderSize() == 2000) {
            return 1500;
        } else if(game.getConfigManager().getBorderSize() == 1500) {
            return 1000;
        } else if(game.getConfigManager().getBorderSize() == 1000) {
            return 500;
        } else if(game.getConfigManager().getBorderSize() == 500) {
            return 100;
        } else if(game.getConfigManager().getBorderSize() == 100) {
            return 50;
        } else if(game.getConfigManager().getBorderSize() == 50) {
            return 25;
        }
        return 0;
    }

    public void runTask() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(game, new BukkitRunnable() {
            @Override
            public void run() {
                uptimeSeconds++;

                if (uptimeSeconds == 60) {
                    uptimeSeconds = 0;
                    uptimeMinutes += 1;
                    borderMinutes += 1;

                    if(game.getGameManager().isTeamGame()) {
                        game.getGameManager().removeDeadTeams();
                    }

                    if (uptimeMinutes == 60) {
                        uptimeMinutes = 0;
                        uptimeHours += 1;
                    }
                }

                if (uptimeMinutes == game.getConfigManager().getFinalHeal()) {
                    if (uptimeSeconds == 0) {
                        if(game.isChatMuted()) {
                            game.setChatMuted(false);
                            Bukkit.broadcastMessage(mColor + "Global Chat " + sColor + "has been " + ChatColor.GREEN + "enabled" + sColor + ".");
                        }

                        if(!game.isFinalHealHappened()) {
                            game.getGameManager().executeFinalHeal();
                            game.setFinalHealHappened(true);
                        }
                    }
                }

                if (uptimeMinutes == game.getConfigManager().getGraceTime()) {
                    if (uptimeSeconds == 0) {
                        if(game.isInGrace()) {
                            game.getGameManager().endGracePeriod();
                        }
                    }
                }

                if (borderMinutes == game.getConfigManager().getBorderTime() - 5) {
                    if (uptimeSeconds == 0) {
                        Bukkit.broadcastMessage(sColor + "The border is going to shrink by 500 blocks every 5 minutes now!");
                        game.getGameManager().playSound();

                        World uhcWorld = Bukkit.getWorld("uhc_world");

                        uhcWorld.setTime(0);
                        uhcWorld.setGameRuleValue("doDaylightCycle", "false");

                        Bukkit.broadcastMessage(sColor + "Permanent day is now active.");
                    }
                }

                if(game.getConfigManager().getBorderSize() > 25) {
                    if (borderMinutes == game.getConfigManager().getBorderTime() || borderMinutes == game.getConfigManager().getBorderTime() + 5 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 10 || borderMinutes == game.getConfigManager().getBorderTime() + 15 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 20 || borderMinutes == game.getConfigManager().getBorderTime() + 25 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 30 || borderMinutes == game.getConfigManager().getBorderTime() + 35 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 40) {
                        if (uptimeSeconds == 0) {
                            game.getWorldManager().createTotalShrink();
                        }
                    } else if (borderMinutes == game.getConfigManager().getBorderTime() - 1 || borderMinutes == game.getConfigManager().getBorderTime() + 4 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 9 || borderMinutes == game.getConfigManager().getBorderTime() + 14 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 19 || borderMinutes == game.getConfigManager().getBorderTime() + 24 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 29 || borderMinutes == game.getConfigManager().getBorderTime() + 34 ||
                            borderMinutes == game.getConfigManager().getBorderTime() + 39) {
                        switch (uptimeSeconds) {
                            case 30:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "30 seconds" + sColor + ".");
                                break;
                            case 40:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "20 seconds" + sColor + ".");
                                break;
                            case 50:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "10 seconds" + sColor + ".");
                                break;
                            case 51:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "9 seconds" + sColor + ".");
                                break;
                            case 52:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "8 seconds" + sColor + ".");
                                break;
                            case 53:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "7 seconds" + sColor + "."");
                                break;
                            case 54:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "6 seconds" + sColor + ".");
                                break;
                            case 55:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "5 seconds" + sColor + ".");
                                break;
                            case 56:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "4 seconds" + sColor + ".");
                                break;
                            case 57:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "3 seconds" + sColor + ".");
                                break;
                            case 58:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "2 seconds" + sColor + ".");
                                break;
                            case 59:
                                game.getGameManager().playSound();
                                Bukkit.broadcastMessage("§7[§b§lBorder§7] " + sColor + "The border is going to shrink to " + mColor + getNextBorder() + "x" + getNextBorder() +
                                        " blocks" + sColor + " in " + mColor + "1 second" + sColor + ".");
                                break;
                        }
                    }
                }

            }
        }, 0, 20);
    }

    public void cancelTask() {
        Bukkit.getScheduler().cancelTask(taskID);
    }

    public String getFormattedTime() {
        String formattedTime = "";

        if(uptimeHours >= 1) {
            if(uptimeHours < 10)
                formattedTime += "0";
            formattedTime += uptimeHours + ":";
        }

        if (uptimeMinutes < 10)
            formattedTime += "0";
        formattedTime += uptimeMinutes + ":";

        if (uptimeSeconds < 10)
            formattedTime += "0";
        formattedTime += uptimeSeconds;

        return formattedTime;
    }

}


