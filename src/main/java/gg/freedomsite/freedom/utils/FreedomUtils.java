package gg.freedomsite.freedom.utils;

import net.minecraft.server.v1_16_R2.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;

import java.util.concurrent.ThreadLocalRandom;

public class FreedomUtils
{

    private static final ChatColor[] colorfulColors = new ChatColor[]{
            ChatColor.AQUA,
            ChatColor.GREEN,
            ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW,
            ChatColor.GOLD,
            ChatColor.RED,
            ChatColor.BLUE,
            ChatColor.DARK_AQUA
    };

    public static ChatColor randomColor()
    {
        return colorfulColors[ThreadLocalRandom.current().nextInt(colorfulColors.length)];
    }

    public static String getVersion()
    {
        return getServer().getVersion();
    }

    private static MinecraftServer getServer()
    {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

}
