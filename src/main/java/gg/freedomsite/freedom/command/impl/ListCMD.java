package gg.freedomsite.freedom.command.impl;

import gg.freedomsite.freedom.command.FreedomCommand;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCMD extends FreedomCommand
{

    public ListCMD() {
        super("/list [-v | -a | -i]", "list", "Views the list of players on the server", new String[]{"who"}, Rank.NON);
        setEnabled(true);
    }

    @Override
    public void run(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            List<String> players = new ArrayList<>();
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .collect(Collectors.toList()).forEach(fplayer -> players.add(fplayer.getRank().getPrefix() + fplayer.getUsername()));
            sender.sendMessage("§eThere are currently §6" + players.size() + " §eout of §6" + Bukkit.getMaxPlayers() + " §eplayers online");
            sender.sendMessage(StringUtils.join(players, ChatColor.RESET + ", "));
            return;
        } else if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("-v"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
                    if (!fPlayer.getRank().isAtleast(Rank.MOD))
                    {
                        player.sendMessage(NO_PERMISSION);
                        return;
                    }
                }

                List<String> players = new ArrayList<>();
                Bukkit.getOnlinePlayers().stream()
                        .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                        .filter(FPlayer::isVanished)
                        .filter(FPlayer::isAdmin)
                        .collect(Collectors.toList()).forEach(fplayer -> players.add(fplayer.getRank().getPrefix() + fplayer.getUsername()));
                sender.sendMessage("§eThere are currently §6" + players.size() + " §eout of §6" + Bukkit.getMaxPlayers() + " §evanished players online");
                sender.sendMessage(StringUtils.join(players, ChatColor.RESET + ", "));
                return;
            }
            if (args[0].equalsIgnoreCase("-a"))
            {
                List<String> players = new ArrayList<>();
                Bukkit.getOnlinePlayers().stream()
                        .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                        .filter(FPlayer::isAdmin)
                        .collect(Collectors.toList()).forEach(fplayer -> players.add(fplayer.getRank().getPrefix() + fplayer.getUsername()));
                sender.sendMessage("§eThere are currently §6" + players.size() + " §eout of §6" + Bukkit.getMaxPlayers() + " §estaff online");
                sender.sendMessage(StringUtils.join(players, ChatColor.RESET + ", "));
                return;
            }
            if (args[0].equalsIgnoreCase("-i"))
            {
                List<String> players = new ArrayList<>();
                Bukkit.getOnlinePlayers().stream()
                        .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                        .filter(FPlayer::isImposter)
                        .collect(Collectors.toList()).forEach(fplayer -> players.add(fplayer.getRank().getPrefix() + fplayer.getUsername()));
                sender.sendMessage("§eThere are currently §6" + players.size() + " §eout of §6" + Bukkit.getMaxPlayers() + " §eimposters online");
                sender.sendMessage(StringUtils.join(players, ChatColor.RESET + ", "));
                return;
            }
        }
    }
}
