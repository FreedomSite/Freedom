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
        if (args.length == 0) {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
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

        if (args.length < 2) {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }

        if (sender.getName() == target.getName()) {
            sender.sendMessage("§7You may not report yourself!");
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(target.getUniqueId());
        if (fPlayer.isAdmin()) {
            sender.sendMessage("§7You may not report staff members!");
            return;
        }

        if (sender.getName().equalsIgnoreCase("CONSOLE")) {
            sender.sendMessage("§cThis command is for in-game use only!");
            return;
        }

        // Get all online staff, and broadcast report
        for (Player onlinestaff : Bukkit.getServer().getOnlinePlayers()) {
            FPlayer staff = getPlugin().getPlayerData().getData(onlinestaff.getUniqueId());
            if (staff.isAdmin()) {
                onlinestaff.sendMessage("§c[REPORTS] §e" + sender.getName() + "§c has reported §e" + target.getName() + "§c for §e" + reason);
            }
        }

        // Establish Logger, log report to console
        Logger log = Bukkit.getLogger();
        log.info("§c[REPORTS] §e" + sender.getName() + "§c has reported §e" + target.getName() + "§c for §e" + reason);
        sender.sendMessage(ChatColor.GREEN + "You have succesfully reported " + target.getName());
    }
}

