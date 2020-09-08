package gg.freedomsite.freedom.config;

import gg.freedomsite.freedom.Freedom;

import java.util.ArrayList;

public enum ConfigEntry
{

    //TODO: this is untested, test it.

    SERVER_MOTD("server.motd", String.class),
    SERVER_STAFFMODE("server.staffmode", Boolean.class),

    DISCORD_TOKEN("discord.token", String.class),
    DISCORD_GUILD_ID("discord.guild-id", String.class),
    DISCORD_CHAT_CHANNEL_ID("discord.channels.chat-id", String.class),
    DISCORD_CONSOLE_CHANNEL_ID("discord.channels.console-id", String.class);

    private String key;
    private Object value;
    ConfigEntry(String key, Object type)
    {
        this.key = key;
        if (type instanceof String)
        {
            this.value = Freedom.get().getConfig().getString(key);
        }
        if (type instanceof Boolean)
        {
            this.value = Freedom.get().getConfig().getBoolean(key);
        }
        if (type instanceof Integer)
        {
            this.value = Freedom.get().getConfig().getInt(key);
        }
        if (type instanceof Double)
        {
            this.value = Freedom.get().getConfig().getDouble(key);
        }
        if (type instanceof ArrayList)
        {
            this.value = Freedom.get().getConfig().getStringList(key);
        }
    }

    public Object getValue()
    {
        return value;
    }
}
