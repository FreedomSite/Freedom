package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpyCMD extends FreedomCommand
{
    public CommandSpyCMD() {
        super("/commandspy", "cmdspy", "Toggles commandspy", new String[]{"cmdspy"}, Rank.MOD);
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
        if (fPlayer.isCommandspy())
        {
            fPlayer.setCommandspy(false);
            getPlugin().getPlayerData().update(fPlayer);
        } else {
            fPlayer.setCommandspy(true);
            getPlugin().getPlayerData().update(fPlayer);
        }

        player.sendMessage("§7Your commandspy has been toggled " + (fPlayer.isCommandspy() ? "on" : "off"));
    }
}
