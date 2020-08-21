package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlatlandsCMD extends FreedomCommand
{
    public FlatlandsCMD() {
        super("/flatlands", "flatlands", "Teleports you to the staff world", Rank.OP);
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
        if (player.getWorld().getName().equalsIgnoreCase("flatlands"))
        {
            player.sendMessage("§cYou are currently in the Flatlands!");
            return;
        }
        player.teleport(getPlugin().getFlatlands().getWorld().getSpawnLocation());
        player.sendMessage("§7Teleporting to Flatlands..");
    }
}
