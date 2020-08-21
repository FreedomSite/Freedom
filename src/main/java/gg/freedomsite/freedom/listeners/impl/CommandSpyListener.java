package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandSpyListener extends FreedomListener
{

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();
        FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
        Bukkit.getOnlinePlayers().stream()
                .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                .filter(FPlayer::isAdmin)
                .forEach(staff -> {
                    if (getAllRanksBelow(staff.getRank()).contains(fPlayer.getRank()))
                    {
                        if (staff.isCommandspy())
                        {
                            Date date = new Date();
                            if (event.getMessage().startsWith("//"))
                            {
                                staff.getPlayer().sendMessage("§c["
                                    + (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":"
                                    + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) + ":"
                                    + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds())
                                    + "] " + "[W/E] " + player.getName() + ": " + event.getMessage());
                            } else if (event.getMessage().startsWith("/")){
                                staff.getPlayer().sendMessage("§7["
                                    + (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":"
                                    + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) + ":"
                                    + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds())
                                    + "] " + player.getName() + ": " + event.getMessage());
                            }
                        }
                    }
                });
    }


    public List<Rank> getAllRanksBelow(Rank rank)
    {
        List<Rank> rankList = new ArrayList<>();
        for (Rank ranks : Rank.values())
        {
            if (ranks.getRankLevel() < rank.getRankLevel())
            {
                rankList.add(ranks);
            }
        }
        return rankList;
    }

}
