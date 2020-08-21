package gg.freedomsite.freedom.command;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public abstract class FreedomCommand
{

    public final String IN_GAME_ONLY = ChatColor.RED + "You can only use this in-game.";
    public final String NO_PERMISSION = "§cYou do not have permission to execute this command.";
    public final String PLAYER_NOT_FOUND = "§7This player can not be found, make sure the player is online and you spelt the name correctly.";


    public String usage;
    public String name;
    public String[] aliases;
    public String description;
    public boolean enabled = false;
    
    public Rank rank; // minimum rank it can be accessed from


    public FreedomCommand(String usage, String name, String description, String[] aliases, Rank rank)
    {
        this.usage = usage;
        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.rank = rank;
    }

    public FreedomCommand(String usage, String name, String description, Rank rank)
    {
        this.usage = usage;
        this.name = name;
        this.description = description;
        this.rank = rank;
    }

    public FreedomCommand(String usage, String name, Rank rank)
    {
        this.usage = usage;
        this.name = name;
        this.rank = rank;
    }

    public abstract void run(CommandSender sender, String[] args);


    public boolean matches(String label)
    {
        label = label.toLowerCase();
        if (getAliases() != null && getAliases().length != 0)
        {
            return Arrays.asList(getAliases()).contains(label) || getName().equalsIgnoreCase(label) || label.equalsIgnoreCase(Freedom.get().getDescription().getName().toLowerCase() + ":" + getName());
        }
        return getName().equalsIgnoreCase(label) || label.equalsIgnoreCase(Freedom.get().getDescription().getName().toLowerCase() + ":" + getName());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }


    public String[] getAliases() {
        return aliases;
    }

    public Rank getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void reply(CommandSender sender, String message)
    {
        sender.sendMessage(message);
    }

    public void playSound(Player player, Sound sound)
    {
        player.playSound(player.getLocation(), sound, 5, 5);
    }

    public void saveConfig(YamlConfiguration config, String configName)
    {
        File file = new File(Freedom.get().getDataFolder(), configName);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig(String configName)
    {
        File file = new File(Freedom.get().getDataFolder(), configName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public Freedom getPlugin()
    {
        return Freedom.get();
    }

    public void bcastMsg(String message)
    {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(message);
        });
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public String getName(CommandSender sender)
    {
        if (sender instanceof Player) return sender.getName();
        else if (sender.getName().equalsIgnoreCase("CONSOLE")) return "Freedom";
        else return sender.getName();
    }

}
