package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class BukkitCommandListener extends FreedomListener
{

    @EventHandler
    public void onCommandPreprocessEvent(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();

        for (Plugin plugin : getPlugin().getServer().getPluginManager().getPlugins())
        {
            if (event.getMessage().startsWith("/" + plugin.getDescription().getName().toLowerCase() + ":") ||
            event.getMessage().startsWith("//" + plugin.getDescription().getName().toLowerCase() + ":"))
            {
                player.sendMessage("§7You may not cast plugin specific commands.");
                event.setCancelled(true);
            }
        }

        FPlayer fplayer = getPlugin().getPlayerData().getData(player.getUniqueId());

        if (event.getMessage().startsWith("/bukkit:") || event.getMessage().startsWith("/minecraft:"))
        {
            if (!fplayer.getRank().isAtleast(Rank.EXECUTIVE))
            {
                return;
            }
            {
                player.sendMessage("§7You may not cast plugin specific commands.");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onReload(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();
        if (event.getMessage().startsWith("/reload") || event.getMessage().startsWith("/rl"))
        {
            event.setCancelled(true);
            String[] args = event.getMessage().split(" ");

            if (!getPlugin().getPlayerData().getData(player.getUniqueId()).getRank().isAtleast(Rank.ADMIN))
            {
                player.sendMessage("§cYou do not have permission to execute this command.");
                return;
            }


            if (args.length == 1)
            {
                player.sendMessage("§cAre you sure you want to use this command? Please type /reload confirm to confirm.");
                return;
            }
            else if (args.length == 2)
            {
                if (args[1].equalsIgnoreCase("confirm"))
                {
                    getPlugin().getServer().reload();
                    getPlugin().getServer().getOnlinePlayers().stream()
                            .map(p -> getPlugin().getPlayerData().getDataFromSQL(p.getUniqueId()))
                            .filter(p -> p.getRank().isAtleast(Rank.ADMIN))
                            .forEach(p -> p.getPlayer().sendMessage("§e" + player.getName() + " has reloaded the server"));
                    return;
                }
            }

        }
    }

}
