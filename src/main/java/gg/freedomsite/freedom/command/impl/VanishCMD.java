package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import gg.freedomsite.freedom.utils.FreedomUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCMD extends FreedomCommand
{
    public VanishCMD() {
        super("/vanish", "vanish", "Vanish into thin air", new String[]{"v"}, Rank.MOD);
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
        FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
        if (fPlayer.isVanished())
        {
            fPlayer.setVanished(false);
            getPlugin().getPlayerData().update(fPlayer);
            FreedomUtils.vanish(fPlayer, Bukkit.getOnlinePlayers(), false);
        } else {
            fPlayer.setVanished(true);
            getPlugin().getPlayerData().update(fPlayer);
            FreedomUtils.vanish(fPlayer, Bukkit.getOnlinePlayers(), true);
        }

        player.sendMessage("§7You have toggled vanish " + (fPlayer.isVanished() ? "on" : "off"));
    }
}
