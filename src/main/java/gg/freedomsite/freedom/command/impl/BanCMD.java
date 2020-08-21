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

public class BanCMD extends FreedomCommand
{
    public BanCMD() {
        super("/ban <player> <reason>", "ban", "Ban a player", new String[]{"fban"}, Rank.MOD);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
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
        long dateNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        String reason = StringUtils.join(args, " ", 1, args.length);


        if (reason.length() > 255)
        {
            sender.sendMessage("§cPlease choose a reason shorter than 255!");
            return;
        }

        if (sender instanceof Player)
        {
            FPlayer playerSender = getPlugin().getPlayerData().getData(((Player) sender).getUniqueId());
            if (!getPlugin().getRankConfig().inheritedRanks(playerSender.getRank()).contains(fPlayer.getRank()))
            {
                sender.sendMessage("§cYou can't ban this player!");
            }
        }

        if (getPlugin().getBanManager().isBanned(player.getUniqueId()))
        {
            sender.sendMessage("§cThis user is already banned!");
            return;
        }

        Ban ban;
        if (sender instanceof Player)
        {
            ban = new Ban(fPlayer.getUuid(), ((Player)sender).getUniqueId(), reason, 0, dateNow);
        } else {
            ban = new Ban(fPlayer.getUuid(), null, reason, 0, dateNow);
        }
        ban.setBanned(true);
        getPlugin().getBanManager().executeBan(ban);
        if (player.isOnline())
        {
            fPlayer.getPlayer().kickPlayer("§cYou have been banned by §f" + sender.getName() + "!\n" +
                    "§cReason: §f" + reason + "\n" +
                    "§cAppeal @ §fhttps://freedomsite.boards.net");
        }

        if (sender instanceof Player)
        {
            bcastMsg("§c" + sender.getName() + " - Banning " + fPlayer.getUsername() + "\n" +
                    "§cReason: §e" + reason);
        } else {
            bcastMsg("§cFreedom - Banning " + fPlayer.getUsername() + "\n" +
                    "§cReason: §e" + reason);
        }
    }
}
