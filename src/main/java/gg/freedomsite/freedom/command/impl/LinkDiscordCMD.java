package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.cache.DiscordCache;
import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LinkDiscordCMD extends FreedomCommand {

    public LinkDiscordCMD() {
        super("/discord", "discord", "Links your discord", Rank.OP);
        setEnabled(true);
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
        player.sendMessage("§7Your temporary code is §e" + code);
        player.sendMessage("§7This code will expire in 1 minute.");

        new BukkitRunnable() {
            @Override
            public void run() {
                DiscordCache.getLinkedCodes().remove(code);
            }
        }.runTaskLater(getPlugin(), 20 * 60);

    }
}
