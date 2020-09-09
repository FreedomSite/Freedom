package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.cache.DiscordCache;
import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LinkDiscordCMD extends FreedomCommand {

    public LinkDiscordCMD() {
        super("/linkdiscord", "linkdiscord", "Links your discord", Rank.OP);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(IN_GAME_ONLY);
            return;
        }

        Player player = (Player) sender;

        if (DiscordCache.getLinkedCodes().containsValue(player.getUniqueId()))
        {
            player.sendMessage(ChatColor.RED + "You were already given a code.");
            return;
        }

        long code = Long.parseLong(RandomStringUtils.randomNumeric(6));
        DiscordCache.getLinkedCodes().put(code, player.getUniqueId());


    }
}
