package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OPCMD extends FreedomCommand
{


    public OPCMD() {
        super("/op <player>", "op", "Ops a player", Rank.OP);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length != 1)
        {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
        fPlayer.setRank(Rank.OP);
        Bukkit.broadcastMessage("§b" + sender.getName() + " - Opping " + player.getName());
        player.sendMessage("§eYou are now op!");
    }
}
