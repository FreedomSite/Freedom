package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpallCMD extends FreedomCommand
{


    public OpallCMD() {
        super("/opall", "opall", "Ops all players", Rank.OP);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (Bukkit.getOnlinePlayers().size() == 0)
        {
            sender.sendMessage(ChatColor.GRAY + "Nobody is online to OP!");
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers())
        {
            FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());

            if (fPlayer.getRank() == Rank.OP) {
                sender.sendMessage(ChatColor.GRAY + "Everyone is op!");
                return;
            }

            if (fPlayer.isAdmin())
            {
                continue;
            }

            if (fPlayer.isImposter())
            {
                continue;
            }
            bcastMsg("§b" + sender.getName() + " - Opping all players on the server!");
            fPlayer.setRank(Rank.OP);
            getPlugin().getRankConfig().setPlayerPermissions(fPlayer);
            getPlugin().getPlayerData().update(fPlayer);
            player.sendMessage("§eYou are now op!");
        }


    }
}