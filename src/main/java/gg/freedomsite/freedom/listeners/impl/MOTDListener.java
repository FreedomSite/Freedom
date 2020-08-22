package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.utils.FreedomUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

public class MOTDListener extends FreedomListener
{

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {

        if (getPlugin().getConfig().getBoolean("server.staffmode")) {
            event.setMotd("§cFreedom - Staffmode is on!");
        } else {
            String motd = Freedom.get().getConfig().getString("server.motd");
            assert motd != null;
            motd = motd.replace("&-", "§" + FreedomUtils.randomColor().getChar());
            motd = motd.replace("%mcversion%" /*tfm placeholder xd xd xd*/, FreedomUtils.getVersion());

            event.setMotd(motd);
        }
    }


}
