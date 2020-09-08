package gg.freedomsite.freedom.discord;

import gg.freedomsite.freedom.Freedom;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class ConsoleAppender extends AbstractAppender {

    public ConsoleAppender() {
        super("ConsoleAppender", null, PatternLayout.createDefaultLayout(), false);
    }

    @Override
    public boolean isStarted()
    {
        return true;
    }

    @Override
    public void append(LogEvent logEvent) {
        TextChannel channel = Freedom.get().getDiscordBot().getConsoleChannel();
        channel.sendMessage(logEvent.getMessage().getFormattedMessage()).queue();

    }
}
