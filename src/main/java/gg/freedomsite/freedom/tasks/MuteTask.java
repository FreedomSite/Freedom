package gg.freedomsite.freedom.tasks;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class MuteTask extends BukkitRunnable
{
    private FPlayer player;
    public MuteTask(FPlayer player)
    {
        this.player = player;
    }
    @Override
    public void run() {
        if (player.isMuted())
        {
            player.setMuted(false);
            Freedom.get().getPlayerData().update(player);
            Bukkit.broadcastMessage("§cFreedom - Auto unmuting " + player.getUsername());
        }
    }
}