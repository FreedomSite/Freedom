package gg.freedomsite.freedom.tasks;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class FreezeTask extends BukkitRunnable
{
    private FPlayer player;
    public FreezeTask(FPlayer player)
    {
        this.player = player;
    }
    @Override
    public void run() {
        if (player.isFrozen())
        {
            player.setFrozen(false);
            Freedom.get().getPlayerData().update(player);
            Bukkit.broadcastMessage("§cFreedom - Auto unfreezing " + player.getUsername());
        }
    }
}