package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffWorldCMD extends FreedomCommand
{
    public StaffWorldCMD() {
        super("/staffworld", "staffworld", "Teleports you to the staff world", Rank.OP);
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
        if (player.getWorld().getName().equalsIgnoreCase("staffworld"))
        {
            player.sendMessage("§cYou are currently in StaffWorld!");
            return;
        }
        player.teleport(getPlugin().getStaffWorld().getWorld().getSpawnLocation());
        player.sendMessage("§7Teleporting to StaffWorld..");
    }
}
