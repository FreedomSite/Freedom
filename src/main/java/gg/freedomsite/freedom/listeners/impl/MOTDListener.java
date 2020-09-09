package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.config.ConfigEntry;
import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.utils.FreedomUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTDListener extends FreedomListener
{

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {

        FPlayer fPlayer = getPlugin().getPlayerData().getPlayerFromIP(event.getAddress().getHostAddress().trim());
        if (getPlugin().getBanManager().isBanned(fPlayer.getUuid()))
        {
            event.setMotd("§cFreedom §8- §cYou are banned.");
        }
        if ((boolean) ConfigEntry.SERVER_STAFFMODE.getValue()) {
            event.setMotd("§cFreedom - StaffMode is On.");
        } else {
            String motd = Freedom.get().getConfig().getString("server.motd");
            assert motd != null;
            motd = motd.replace("&-", "§" + FreedomUtils.randomColor().getChar());
            motd = motd.replace("%mcversion%" /*tfm placeholder xd xd xd*/, FreedomUtils.getVersion());

            event.setMotd(motd);
        }
    }


}
