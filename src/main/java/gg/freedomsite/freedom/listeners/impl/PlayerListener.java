package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;

public class PlayerListener extends FreedomListener
{

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        PlayerData playerData = getPlugin().getPlayerData();
        FPlayer fPlayer;
        if (!playerData.exists(player.getUniqueId()))
        {
            fPlayer = new FPlayer(player.getUniqueId());
            fPlayer.setUsername(player.getName());
            fPlayer.setIp(player.getAddress().getAddress().getHostAddress().trim());

            playerData.insert(fPlayer);

            getPlugin().getLogger().info("Creating a new entry for player: " + player.getName());
        } else {
            fPlayer = playerData.getDataFromSQL(player.getUniqueId());
            getPlugin().getLogger().info("Loading entry for player: " + player.getName());
        }

        fPlayer.setUsername(player.getName());

        playerData.getPlayers().put(player.getUniqueId(), fPlayer);

        //registering perms
        fPlayer.setAttachment(player.addAttachment(Freedom.get()));
        getPlugin().getRankConfig().setPlayerPermissions(fPlayer);

        //checking if imposter
        if (!player.getAddress().getAddress().getHostAddress().trim().equalsIgnoreCase(fPlayer.getIp()))
        {
            fPlayer.setImposter(true);
        }

        if (fPlayer.isImposter() && fPlayer.isAdmin())
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + Rank.IMPOSTER.getLoginMsg());
        }
        else if (!fPlayer.isImposter() && fPlayer.isAdmin())
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + fPlayer.getRank().getLoginMsg());
        }



    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        PlayerData playerData = getPlugin().getPlayerData();
        event.getPlayer().removeAttachment(playerData.getData(event.getPlayer().getUniqueId()).getAttachment());
        playerData.getPlayers().remove(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        PlayerData playerData = getPlugin().getPlayerData();
        FPlayer fPlayer = playerData.getData(event.getPlayer().getUniqueId());
        if (fPlayer.isImposter())
        {
            event.setFormat(Rank.IMPOSTER.getPrefix() + " " + ChatColor.WHITE + fPlayer.getUsername() + "§7:§r " + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
            return;
        }
        event.setFormat(fPlayer.getTag().isEmpty() ? fPlayer.getRank().getPrefix() + " " + ChatColor.WHITE + fPlayer.getUsername() + "§7:§r " + ChatColor.translateAlternateColorCodes('&', event.getMessage()) :
                ChatColor.translateAlternateColorCodes('&', fPlayer.getTag()) + " " + ChatColor.WHITE + fPlayer.getUsername() + "§7:§r " + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
    }

}