package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCMD extends FreedomCommand
{

    public UnmuteCMD() {
        super("/unmute <player | -a>", "unmute", "Unmutes a player, -a mutes all non staff", Rank.MOD);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {

        if (args[0].equalsIgnoreCase("-a"))
        {
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .forEach(p -> getPlugin().getMuter().unmute(p));
            bcastMsg("§c" + getName(sender) + " - Unmuting all players on the server!");

            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(target.getUniqueId());
        if (!fPlayer.isMuted())
        {
            sender.sendMessage("§cThis player is not muted!");
            return;
        }


        getPlugin().getMuter().unmute(fPlayer);
        bcastMsg("§c" + sender.getName() + " - Unmuting " + fPlayer.getPlayer().getName());
    }

}
