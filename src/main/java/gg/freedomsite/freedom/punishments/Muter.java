package gg.freedomsite.freedom.punishments;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import org.bukkit.Bukkit;

public class Muter
{
    private int secondsUntilUnmute = 60 * 5; // 5 min

    public void mute(FPlayer player)
    {
        player.setMuted(true);
        Freedom.get().getPlayerData().update(player);

        if (!Bukkit.getScheduler().isCurrentlyRunning(player.getMuteTask().getTaskId())) {
            player.getMuteTask().runTaskLater(Freedom.get(), secondsUntilUnmute * 20);
        } else {
            player.getMuteTask().cancel();
            player.getMuteTask().runTaskLater(Freedom.get(), secondsUntilUnmute * 20);
        }
    }

    public void unmute(FPlayer player)
    {
        player.setMuted(false);
        Freedom.get().getPlayerData().update(player);
        if (Bukkit.getScheduler().isCurrentlyRunning(player.getMuteTask().getTaskId()))
        {
            player.getMuteTask().cancel();
        }
    }


}
