package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeopCMD extends FreedomCommand
{
    public DeopCMD() {
        super("/deop <player>", "deop", "Deops a player", Rank.MOD);
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
        fPlayer.setRank(Rank.NON);
        Bukkit.broadcastMessage("§c" + sender.getName() + " - Deopping " + player.getName());
        player.sendMessage("§eYou have lost op!");
    }
}
