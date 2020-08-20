package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import gg.freedomsite.freedom.ranking.Rank;
import me.totalfreedom.bukkittelnet.api.TelnetCommandEvent;
import me.totalfreedom.bukkittelnet.api.TelnetPreLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

public class TelnetListener extends FreedomListener
{

    @EventHandler
    public void onLogin(TelnetPreLoginEvent event)
    {
        PlayerData data = getPlugin().getPlayerData();
        Bukkit.getLogger().info(event.getIp());
        FPlayer fPlayer = data.getPlayerFromIP(event.getIp());
        Bukkit.getLogger().info(fPlayer.getRank().name());
        if (fPlayer.getRank().isAtleast(Rank.ADMIN))
        {
            event.setName(fPlayer.getUsername());
            event.setBypassPassword(true);
        }
    }

    @EventHandler
    public void onCmd(TelnetCommandEvent event)
    {

    }

}
