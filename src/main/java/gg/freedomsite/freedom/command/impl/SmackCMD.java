package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SmackCMD extends FreedomCommand
{
    public SmackCMD() {
        super("/smack <player> <reason>", "smack", "Smack a player!", Rank.MOD);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length < 2)
        {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        String reason = StringUtils.join(args, " ", 1, args.length);

        player.setVelocity(new Vector(5, 5, 0));
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setHealth(0);
            }
        }.runTaskLater(getPlugin(), 30);
        bcastMsg("§c" + getName(sender) + " - Smacking " + player.getName() + "\n" +
                "§cReason: §f" + reason);
        player.sendMessage("§cYou have been smacked by §f" + getName(sender) + " §cfor breaking the rules!");

    }
}
