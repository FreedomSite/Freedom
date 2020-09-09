package gg.freedomsite.freedom.discord;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.discord.listeners.ChatListener;
import gg.freedomsite.freedom.discord.listeners.DMListener;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    @Getter
    private JDA bot;

    public void start() {
        try {
            bot = JDABuilder.createDefault(getToken()).disableIntents(GatewayIntent.DIRECT_MESSAGE_TYPING,
                    GatewayIntent.GUILD_MESSAGE_TYPING,
                    GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_VOICE_STATES).disableCache(CacheFlag.VOICE_STATE).build();
            bot.addEventListener(new ChatListener(), new DMListener());
        } catch (LoginException e) {
            e.printStackTrace();

        }
    }

    public Guild getMainGuild() {
        Guild guild = getBot().getGuildById(getGuildID());
        assert guild != null;
        return guild;
    }

    public TextChannel getChatChannel()
    {
        TextChannel channel = getMainGuild().getTextChannelById(getChatChannelID());
        assert channel != null;
        return channel;
    }

    public TextChannel getConsoleChannel()
    {
        TextChannel channel = getMainGuild().getTextChannelById(getConsoleChannelID());
        assert channel != null;
        return channel;
    }


    private String getToken()
    {
        return Freedom.get().getConfig().getString("discord.token");
    }

    private String getGuildID()
    {
        return Freedom.get().getConfig().getString("discord.guild-id");
    }

    private String getChatChannelID()
    {
        return Freedom.get().getConfig().getString("discord.channels.chat-id");
    }
    private String getConsoleChannelID()
    {
        return Freedom.get().getConfig().getString("discord.channels.console-id");
    }
}
