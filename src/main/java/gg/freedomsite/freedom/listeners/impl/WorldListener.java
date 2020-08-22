package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.listeners.FreedomListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

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

    @EventHandler
    public void explodeEvent(EntityExplodeEvent event) {
        if(event.getEntityType() == EntityType.PRIMED_TNT) {
            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            if (tnt.getSource() instanceof Player) {
                Player player = (Player) tnt.getSource();
                player.sendMessage(ChatColor.RED + "TNT explosions are disabled on this server!");
                event.getEntity().getLastDamageCause().setCancelled(true);
                event.setCancelled(true);
            }
        }
    }
}
