package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class WipeFlatlandsCMD extends FreedomCommand
{

    public WipeFlatlandsCMD() {
        super("/wipeflatlands", "wipeflatlands", "Wipes the flatlands", Rank.ADMIN);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("§cServer is going down to wipe the flatlands! Join back in a minute!"));

        World world = getPlugin().getServer().getWorld("flatlands");
        assert world != null;
        world.setAutoSave(false);
        File worldFolder = world.getWorldFolder();
        try {
            FileUtils.deleteDirectory(worldFolder);
            getPlugin().getServer().spigot().restart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
