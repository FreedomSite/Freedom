package gg.freedomsite.freedom.discord.listeners;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.cache.DiscordCache;
import gg.freedomsite.freedom.player.FPlayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang.math.NumberUtils;


public class DMListener extends ListenerAdapter
{

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        Member member = event.getMessage().getMember();
        assert member != null;
        if (member.getUser().isBot()) return;
        if (!NumberUtils.isNumber(msg)) return;

        long code = Long.parseLong(msg);
        if (DiscordCache.isValidCode(code)) {
            FPlayer fPlayer = DiscordCache.getPlayerFromCode(code);
            fPlayer.setLinkedDiscordID(member.getIdLong());
            Freedom.get().getPlayerData().update(fPlayer);
            event.getMessage().getChannel().sendMessage("Successfully linked your account to: **" + fPlayer.getUsername() + "**.").queue();
        }
    }

}
