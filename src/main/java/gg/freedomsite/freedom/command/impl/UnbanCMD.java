package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.banning.Ban;
import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbanCMD extends FreedomCommand
{
    public UnbanCMD() {
        super("/unban <player>", "unban", "Unban a player", new String[]{"funban, unban, pardon"}, Rank.MOD);
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

        if (args.length != 1)
        {
            sender.sendMessage("§7Correct usage: §e" + getUsage());
            return;
        }

        if (!getPlugin().getBanManager().isBanned(player.getUniqueId()))
        {
            sender.sendMessage("§cThis user is not banned!");
            return;
        }

        Ban ban = getPlugin().getBanManager().getActiveBan(fPlayer.getUuid());
        getPlugin().getBanManager().removeBan(ban);

        if (sender instanceof Player)
        {
            bcastMsg("§c" + sender.getName() + " - Unbanning " + fPlayer.getUsername());
        } else {
            bcastMsg("§cFreedom - Unbanning " + fPlayer.getUsername());
        }
    }
}
