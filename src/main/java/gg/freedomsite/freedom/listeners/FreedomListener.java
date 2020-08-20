package gg.freedomsite.freedom.listeners;

import gg.freedomsite.freedom.Freedom;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;

public abstract class FreedomListener implements Listener
{

    public Freedom getPlugin()
    {
        return Freedom.get();
    }

    public YamlConfiguration getConfig(String configName)
    {
        File file = new File(getPlugin().getDataFolder(), configName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

}
