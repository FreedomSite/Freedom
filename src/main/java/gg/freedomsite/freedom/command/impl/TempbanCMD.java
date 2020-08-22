package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.banning.Ban;
import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import gg.freedomsite.freedom.utils.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TempbanCMD extends FreedomCommand
{
    public TempbanCMD() {
        super("/tempban <player> <reason>", "tempban", "Tempban a player", new String[]{"tban"}, Rank.MOD);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());

        if (fPlayer == null)
        {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        //tempban0 player1 reason2
        if (args.length < 2)
        {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }
        long time = TimeUtils.toFutureDate("10m");
        long dateNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        String reason = StringUtils.join(args, " ", 1, args.length);

        if (reason.length() > 255)
        {
            sender.sendMessage("§cPlease choose a reason shorter than 255!");
            return;
        }

        if (getPlugin().getBanManager().isBanned(player.getUniqueId()))
        {
            sender.sendMessage("§cThis user is already banned!");
            return;
        }

        Ban ban;
        if (sender instanceof Player)
        {
            ban = new Ban(fPlayer.getUuid(), ((Player)sender).getUniqueId(), reason, time, dateNow);
        } else {
            ban = new Ban(fPlayer.getUuid(), null, reason, time, dateNow);
        }
        ban.setBanned(true);
        getPlugin().getBanManager().executeBan(ban);
        if (player.isOnline())
        {
            fPlayer.getPlayer().kickPlayer("§cYou have been banned by §f" + sender.getName() + " §cfor §f10 minutes!\n" +
                    "§cReason: §f" + reason);
        }

        if (sender instanceof Player)
        {
            bcastMsg("§c" + sender.getName() + " - Temporarily banning " + fPlayer.getUsername() + " for 10 minutes\n" +
                    "§cReason: §f" + reason);
        } else {
            bcastMsg("§cFreedom - Temporarily banning " + fPlayer.getUsername() + " for 10 minutes\n" +
                    "§cReason: §f" + reason);
        }


    }
}
