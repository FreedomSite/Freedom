package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCMD extends FreedomCommand
{


    public StaffChatCMD() {
        super("/staffchat [message]", "staffchat", "Sends a message or toggles staffchat", new String[]{"sc"}, Rank.MOD);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (!(sender instanceof Player))
        {
            if (args.length == 0)
            {
                sender.sendMessage("§7Correct usage: §e" + getUsage());
                return;
            }
            String message = StringUtils.join(args, " ", 0, args.length);
            message = ChatColor.translateAlternateColorCodes('&', message);

            String finalMessage = message;
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .filter(FPlayer::isAdmin)
                    .forEach(staff -> staff.getPlayer().sendMessage(String.format("§a(STAFF) " + (sender.getName().equalsIgnoreCase("CONSOLE") ? "§5[Console]" : "§5[Telnet]") + " §7%s §f%s", sender.getName(), finalMessage)));
            getPlugin().getServer().getConsoleSender().sendMessage(String.format("§a(STAFF) " + (sender.getName().equalsIgnoreCase("CONSOLE") ? "§5[Console]" : "§5[Telnet]") + "  §7%s §f%s", sender.getName(), finalMessage));
            return;
        } else {
            Player player = (Player) sender;
            FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
            if (args.length == 0)
            {
                if (fPlayer.isStaffchat())
                {
                    fPlayer.setStaffchat(false);
                } else {
                    fPlayer.setStaffchat(true);
                }

                player.sendMessage("§7Your staffchat has been toggled " + (fPlayer.isStaffchat() ? "on" : "off"));
                return;
            }

            String message = StringUtils.join(args, " ", 0, args.length);
            message = ChatColor.translateAlternateColorCodes('&', message);

            String finalMessage = message;
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .filter(FPlayer::isAdmin)
                    .forEach(staff -> staff.getPlayer().sendMessage(String.format("§a(STAFF) %s §7%s §f%s", fPlayer.getRank().getPrefix(), player.getName(), finalMessage)));
            getPlugin().getServer().getConsoleSender().sendMessage(String.format("§a(STAFF) %s §7%s §f%s", fPlayer.getRank().getPrefix(), player.getName(), message));
            return;
        }
    }
}
