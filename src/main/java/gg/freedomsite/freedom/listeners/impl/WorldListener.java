package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.listeners.FreedomListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class WorldListener extends FreedomListener
{

    @EventHandler
    public void onNaturalSpawn(CreatureSpawnEvent event)
    {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL)
        {
            event.setCancelled(true);
        }
    }

}
