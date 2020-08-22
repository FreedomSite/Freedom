package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCMD extends FreedomCommand {


    public FreezeCMD() {
        super("/freeze <player>", "freeze", "Freezes a player", Rank.MOD);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length == 0) {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;

        }
        if (args.length < 1) {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }

        if (args[0].equalsIgnoreCase("-a"))
        {
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .forEach(p -> getPlugin().getFreezer().freeze(p));
            bcastMsg("§c" + getName(sender) + " - Freezing all players on the server!");

            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(target.getUniqueId());

        if (sender instanceof Player)
        {
            FPlayer playerSender = getPlugin().getPlayerData().getData(((Player) sender).getUniqueId());
            if (!getPlugin().getRankConfig().inheritedRanks(playerSender.getRank()).contains(fPlayer.getRank()))
            {
                sender.sendMessage("§cYou can't freeze this player!");
            }
        }

        if (fPlayer.isFrozen()) {
            sender.sendMessage("§cThis player is already frozen!");
            return;
        }

        getPlugin().getFreezer().freeze(fPlayer);
        bcastMsg("§c" + sender.getName() + " - Freezing " + fPlayer.getPlayer().getName());
    }
}
