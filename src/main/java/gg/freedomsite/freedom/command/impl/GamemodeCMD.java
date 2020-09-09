package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import gg.freedomsite.freedom.utils.FreedomUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCMD extends FreedomCommand
{
    public GamemodeCMD() {
        super("/gamemode <s[urvival] | c[creative]>", "gamemode", "Change your gamemode", new String[]{"gm"}, Rank.OP);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.GRAY + "Correct usage: " + ChatColor.YELLOW + getUsage());
            return;
        }

        if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative"))
        {
            ((Player) sender).setGameMode(GameMode.CREATIVE);
            sender.sendMessage(ChatColor.GRAY + "Gamemode set to creative");
            return;
        }

        if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival"))
        {
            ((Player) sender).setGameMode(GameMode.SURVIVAL);
            sender.sendMessage(ChatColor.GRAY + "Gamemode set to survival");
            return;
        } else {
            sender.sendMessage(ChatColor.GRAY + "Correct usage: " + ChatColor.YELLOW + getUsage());
        }

    }
}