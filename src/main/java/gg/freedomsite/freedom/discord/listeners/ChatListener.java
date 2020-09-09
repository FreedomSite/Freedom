package gg.freedomsite.freedom.discord.listeners;

import gg.freedomsite.freedom.Freedom;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatListener extends ListenerAdapter
{

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        Member member = event.getMember();

        assert member != null;

        Role role = member.getRoles().size() > 0 ? event.getMember().getRoles().get(0) : event.getGuild().getPublicRole();

        if (event.getChannel().getId().equalsIgnoreCase(Freedom.get().getDiscordBot().getChatChannel().getId()))
        {
            if (member.getIdLong() == Freedom.get().getDiscordBot().getBot().getSelfUser().getIdLong()) return;
            if (member.getUser().isBot()) return;

            Bukkit.broadcastMessage(ChatColor.YELLOW + "[DISCORD] " + ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + role.getName() + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + member.getUser().getName() + ": " + msg);
            return;
        }

    }

}
