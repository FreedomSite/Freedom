package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        PlayerData playerData = getPlugin().getPlayerData();
        FPlayer fPlayer = playerData.getData(event.getPlayer().getUniqueId());
        playerData.update(fPlayer);
        playerData.getPlayers().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        PlayerData playerData = getPlugin().getPlayerData();
        FPlayer fPlayer = playerData.getData(event.getPlayer().getUniqueId());
        event.setFormat(fPlayer.getTag().isEmpty() ? fPlayer.getRank().getPrefix() + " " + ChatColor.AQUA + fPlayer.getUsername() + "§7:§r " + ChatColor.translateAlternateColorCodes('&', event.getMessage()) :
                ChatColor.translateAlternateColorCodes('&', fPlayer.getTag()) + " " + ChatColor.AQUA + fPlayer.getUsername() + "§7:§r " + ChatColor.translateAlternateColorCodes('&', event.getMessage()));
    }

}
