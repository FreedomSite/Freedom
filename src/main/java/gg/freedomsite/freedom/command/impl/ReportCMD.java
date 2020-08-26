package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.logging.Logger;

public class ReportCMD extends FreedomCommand
{


    public ReportCMD() {
        super("/report <player> <reason>", "report", "Report a player to online staff", Rank.OP);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command is for in-game use only!");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }

        // Reason
        String reason = StringUtils.join(args, " ", 1, args.length);

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§7User not online! Check spelling");
            return;
        }
        if (sender.getName().equalsIgnoreCase(target.getName())) {
            sender.sendMessage("§7You may not report yourself!");
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(target.getUniqueId());
        if (fPlayer.isAdmin()) {
            sender.sendMessage("§7You may not report staff members!");
            return;
        }

        // Get all online staff, and broadcast report
        Bukkit.getOnlinePlayers().stream()
                .map(staff -> getPlugin().getPlayerData().getData(staff.getUniqueId()))
                .filter(FPlayer::isAdmin)
                .forEach(staff -> staff.getPlayer().sendMessage("§c[REPORTS] §e" + sender.getName() + "§c has reported §e" + target.getName() + "§c for §e" + reason));

        Bukkit.getConsoleSender().sendMessage("§c[REPORTS] §e" + sender.getName() + "§c has reported §e" + target.getName() + "§c for §e" + reason);
        sender.sendMessage(ChatColor.GREEN + "You have succesfully reported " + target.getName());
    }
}

