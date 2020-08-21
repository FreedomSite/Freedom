package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import gg.freedomsite.freedom.utils.FreedomUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StaffCMD extends FreedomCommand
{
    public StaffCMD() {
        super("/staff <add | remove | setrank> <player> [rank]", "staff", "Manages adding, removing, and setting the rank of players to and from staff", Rank.ADMIN);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("add"))
            {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                FPlayer fplayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                if (fplayer == null)
                {
                    sender.sendMessage(PLAYER_NOT_FOUND);
                    return;
                }

                if (fplayer.isAdmin())
                {
                    sender.sendMessage("§7This player is already a staff member.");
                    return;
                }

                fplayer.setRank(Rank.MOD);
                fplayer.setCommandspy(true);
                if (player.isOnline())
                {
                    getPlugin().getRankConfig().setPlayerPermissions(fplayer);
                }
                getPlugin().getPlayerData().update(fplayer);
                bcastMsg("§c" + sender.getName() + " - Adding " + fplayer.getUsername() + " to the staff list");
                return;
            }
            else if (args[0].equalsIgnoreCase("remove"))
            {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                FPlayer fplayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                if (fplayer == null)
                {
                    sender.sendMessage(PLAYER_NOT_FOUND);
                    return;
                }

                if (!fplayer.isAdmin())
                {
                    sender.sendMessage("§7This player is not a staff member.");
                    return;
                }

                fplayer.setRank(Rank.OP);
                fplayer.setCommandspy(false);
                FreedomUtils.resetToOP(fplayer);
                if (player.isOnline())
                {
                    getPlugin().getRankConfig().setPlayerPermissions(fplayer);
                }
                getPlugin().getPlayerData().update(fplayer);
                bcastMsg("§c" + sender.getName() + " - Removing " + player.getName() + " from the staff list");
                return;
            } else {
                sender.sendMessage("§7Correct usage: §e" + getUsage());
                return;
            }
        } else if (args.length == 3)
        {
            if (args[0].equalsIgnoreCase("setrank"))
            {

                if (sender instanceof Player)
                {
                    Player playerSender = (Player) sender;
                    FPlayer fSender = getPlugin().getPlayerData().getData(playerSender.getUniqueId());
                    if (!fSender.getRank().isAtleast(Rank.EXECUTIVE))
                    {
                        sender.sendMessage(NO_PERMISSION);
                        return;
                    }
                }

                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                FPlayer fplayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                if (fplayer == null)
                {
                    sender.sendMessage(PLAYER_NOT_FOUND);
                    return;
                }

                if (!fplayer.isAdmin())
                {
                    sender.sendMessage("§7This player is not a staff member, please add them to the staff list then retry this command.");
                    return;
                }

                Rank rank = Rank.findRank(args[2]);
                if (rank == null)
                {
                    sender.sendMessage("§7This is an invalid rank, the correct ranks are: " + StringUtils.join(Arrays.stream(Rank.values()).map(Rank::name).collect(Collectors.toList()), ", "));
                    return;
                }

                if (rank.getRankLevel() < 1)
                {
                    sender.sendMessage("§7You can only use ranks that are the following: " + StringUtils.join(Arrays.stream(Rank.values()).filter(r -> r.getRankLevel() >= 1).map(Rank::name).collect(Collectors.toList()), ", "));
                    return;
                }

                if (sender instanceof Player)
                {
                    Player playerSender = (Player) sender;
                    FPlayer fSender = getPlugin().getPlayerData().getData(playerSender.getUniqueId());
                    if (!fSender.getRank().isAtleast(rank))
                    {
                        sender.sendMessage("§cYou can't set a rank higher than yours!");
                        return;
                    }
                }

                fplayer.setRank(rank);

                fplayer.setCommandspy(false);
                if (player.isOnline())
                {
                    getPlugin().getRankConfig().setPlayerPermissions(fplayer);
                }
                getPlugin().getPlayerData().update(fplayer);
                bcastMsg("§c" + sender.getName() + " - Setting " + player.getName() + "'s rank to " + rank.name().toUpperCase());
                return;
            }
        } else {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }


    }
}
