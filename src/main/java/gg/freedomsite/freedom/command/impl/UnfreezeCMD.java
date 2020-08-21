package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnfreezeCMD extends FreedomCommand {


    public UnfreezeCMD() {
        super("/unfreeze <player | -a>", "unfreeze", "Unfreezes a player", Rank.MOD);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args[0].equalsIgnoreCase("-a"))
        {
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .forEach(p -> getPlugin().getFreezer().unfreeze(p));
            bcastMsg("§c" + getName(sender) + " - Unmuting all players on the server!");

            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(target.getUniqueId());
        if (!fPlayer.isFrozen()) {
            sender.sendMessage("§cThis player is not frozen!");
            return;
        }

        getPlugin().getFreezer().unfreeze(fPlayer);
        bcastMsg("§c" + sender.getName() + " - Unfreezing " + fPlayer.getPlayer().getName());
    }
}
