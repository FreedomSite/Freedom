package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VerifyCMD extends FreedomCommand
{
    public VerifyCMD() {
        super("/verify <player>", "verify", "Manually verify a player", Rank.MOD);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return;
        }

        FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
        if (!fPlayer.isImposter())
        {
            sender.sendMessage("§cThis player is not an imposter!");
            return;
        }

        fPlayer.setImposter(false);
        fPlayer.setIp(player.getAddress().getAddress().getHostAddress().trim());
        getPlugin().getPlayerData().update(fPlayer);
        bcastMsg("§b" + getName(sender) + " - Manually verifying " + player.getName());
    }
}
