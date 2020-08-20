package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCMD extends FreedomCommand
{

    //TODO: ADD FILTERS (VANISHED, ADMIN, IMPOSTER)

    public ListCMD() {
        super("/list [-v | -a | -i]", "list", "Views the list of players on the server", new String[]{"who"}, Rank.NON);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            List<String> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .collect(Collectors.toList()).forEach(fplayer -> players.add(fplayer.getRank().getPrefix() + " " + ChatColor.WHITE + fplayer.getUsername()));
            sender.sendMessage("§eThere are currently §6" + players.size() + " §eout of §6" + Bukkit.getMaxPlayers() + " §eplayers online");
            sender.sendMessage(StringUtils.join(players, ChatColor.RESET + ", "));
            return;
        }
    }
}
