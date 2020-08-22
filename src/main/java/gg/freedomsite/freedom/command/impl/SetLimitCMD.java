package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLimitCMD extends FreedomCommand
{


    public SetLimitCMD() {
        super("/setlimit [limit]", "setlimit", "Change the default server limit for worldedit", Rank.MOD);
        setEnabled(getPlugin().getServer().getPluginManager().isPluginEnabled("WorldEdit"));
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        Bukkit.getOnlinePlayers().forEach(p -> {
            getPlugin().getWorldEditBridge().setLimit(p, 20000);
        });

        if (!(sender instanceof Player))
        {
            if (sender.getName().equalsIgnoreCase("CONSOLE"))
            {
                bcastMsg("§cFreedom - Setting default WorldEdit limit to 20k blocks");
            } else {
                bcastMsg("§c" + sender.getName() + " - Setting default WorldEdit limit to 20k blocks");
            }
        } else {
            bcastMsg("§c" + sender.getName() + " - Setting default WorldEdit limit to 20k blocks");
        }


    }
}
