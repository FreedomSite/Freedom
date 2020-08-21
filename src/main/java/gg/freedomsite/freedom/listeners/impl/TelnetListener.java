package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import gg.freedomsite.freedom.ranking.Rank;
import me.totalfreedom.bukkittelnet.api.TelnetPreLoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class TelnetListener extends FreedomListener
{

    @EventHandler
    public void onLogin(TelnetPreLoginEvent event)
    {
        PlayerData data = getPlugin().getPlayerData();
        FPlayer fPlayer = data.getPlayerFromIP(event.getIp());
        if (fPlayer.getRank().isAtleast(Rank.ADMIN))
        {
            event.setName(fPlayer.getUsername());
            event.setBypassPassword(true);
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .filter(p -> p.getRank().isAtleast(Rank.EXECUTIVE))
                    .forEach(p -> p.getPlayer().sendMessage("§cEstablished Telnet Connection: §e" + event.getIp() + " §c- §e" + event.getName()));
        }

    }

}
