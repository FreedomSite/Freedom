package gg.freedomsite.freedom.punishments;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import org.bukkit.Bukkit;

public class Freezer
{
    private int secondsUntilUnFreeze = 5 * 60;

    public void freeze(FPlayer player)
    {
        player.setFrozen(true);
        Freedom.get().getPlayerData().update(player);

        if (!Bukkit.getScheduler().isCurrentlyRunning(player.getFreezeTask().getTaskId())) {
            player.getFreezeTask().runTaskLater(Freedom.get(), secondsUntilUnFreeze * 20);
        } else {
            player.getFreezeTask().cancel();
            player.getMuteTask().runTaskLater(Freedom.get(), secondsUntilUnFreeze * 20);
        }
    }

    public void unfreeze(FPlayer player)
    {
        player.setFrozen(false);
        Freedom.get().getPlayerData().update(player);
        if (Bukkit.getScheduler().isCurrentlyRunning(player.getFreezeTask().getTaskId()))
        {
            player.getFreezeTask().cancel();
        }
    }

}
