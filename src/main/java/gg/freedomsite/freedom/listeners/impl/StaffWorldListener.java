package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class StaffWorldListener extends FreedomListener
{

    @EventHandler
    public void onPhysics(BlockFromToEvent event)
    {
        if (event.getBlock().getWorld().getName().equalsIgnoreCase("staffworld"))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent event)
    {
        if (!event.getPlayer().getWorld().getName().equalsIgnoreCase("staffworld"))
        {
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(event.getPlayer().getUniqueId());
        if (!fPlayer.isAdmin())
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        if (!event.getPlayer().getWorld().getName().equalsIgnoreCase("staffworld"))
        {
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(event.getPlayer().getUniqueId());
        if (!fPlayer.isAdmin())
        {
            event.setCancelled(true);
        }
    }

}
