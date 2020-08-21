package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class StaffModeCMD extends FreedomCommand
{
    public StaffModeCMD() {
        super("/staffmode", "staffmode", "Toggles staffmode", Rank.ADMIN);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        boolean staffmode = getPlugin().getConfig().getBoolean("server.staffmode");
        if (staffmode)
        {
            getPlugin().getConfig().set("server.staffmode", false);
            getPlugin().saveConfig();
        } else {
            getPlugin().getConfig().set("server.staffmode", true);
            getPlugin().saveConfig();
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .filter(p -> !p.isAdmin())
                    .forEach(player -> player.getPlayer().kickPlayer("§cStaffMode was toggled by §f" + getName(sender)));
        }

        boolean modified = getPlugin().getConfig().getBoolean("server.staffmode");
        bcastMsg("§c" + getName(sender) + " - Turning StaffMode " + (modified ? "on" : "off"));
    }
}
