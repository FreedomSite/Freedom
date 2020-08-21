package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayCMD extends FreedomCommand
{


    public SayCMD() {
        super("/say <message>", "say", "Broadcasts a message sent by the sender", Rank.MOD);
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

        String message = StringUtils.join(args, " ", 0, args.length);

        if (!(sender instanceof Player))
        {
            if (sender.getName().equalsIgnoreCase("CONSOLE"))
            {
                bcastMsg(String.format("§c[Freedom] %s", message));
            } else {
                bcastMsg(String.format("§c[Freedom:%s] %s", sender.getName(), message));
            }
        } else {
            bcastMsg(String.format("§c[Freedom:%s] %s", sender.getName(), message));
        }


    }
}
