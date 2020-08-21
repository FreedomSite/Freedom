package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.banning.Ban;
import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import gg.freedomsite.freedom.ranking.Rank;
import gg.freedomsite.freedom.utils.FreedomUtils;
import gg.freedomsite.freedom.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class PlayerListener extends FreedomListener
{
    //TODO: PERMISSIONS DON'T UNSET!

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

        //login msgs
        if (!fPlayer.isVanished() && fPlayer.isImposter() && fPlayer.isAdmin())
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + Rank.IMPOSTER.getLoginMsg());
        }
        else if (!fPlayer.isVanished() && !fPlayer.isImposter() && fPlayer.isAdmin() && fPlayer.getLoginMSG().isEmpty())
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + fPlayer.getRank().getLoginMsg());
        } else if (!fPlayer.isVanished() && !fPlayer.isImposter() && fPlayer.isAdmin() && !fPlayer.getLoginMSG().isEmpty())
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + fPlayer.getLoginMSG());
        }

        //now set tab list colors
        if (fPlayer.isAdmin())
        {
            player.setPlayerListName(fPlayer.getRank().getColor() + player.getName());
        }

        //vanished
        if (fPlayer.isVanished())
        {
            FreedomUtils.vanish(fPlayer, Bukkit.getOnlinePlayers(), true);
            player.sendMessage("§7§oYou have joined silently..");
            event.setJoinMessage("");
        }

        //ban testing x2

        if (fPlayer.getPlayer().getName().equalsIgnoreCase("Taahh"))
        {
            long tenMinutes = TimeUtils.toFutureDate("1m");
            Ban ban = new Ban(fPlayer.getPlayer().getUniqueId(), fPlayer.getPlayer().getUniqueId(), "TESTING TESTING", tenMinutes, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            if (!getPlugin().getBanManager().isBanned(fPlayer.getUuid()))
            {
                getPlugin().getBanManager().executeBan(ban);
            } else {
                getPlugin().getBanManager().getBans(fPlayer.getUuid()).forEach(p -> {
                    getPlugin().getLogger().info(p.getBannedUUID().toString());
                    getPlugin().getLogger().info(p.getBanner().toString());
                    getPlugin().getLogger().info(p.getReason());
                    getPlugin().getLogger().info(String.valueOf(p.getDuration()));
                    getPlugin().getLogger().info(String.valueOf(p.getDate()));
                });
            }
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
            event.setFormat(Rank.IMPOSTER.getPrefix() + " " + ChatColor.WHITE + fPlayer.getUsername() + "§7:§r " + event.getMessage());
            return;
        }

        String message = event.getMessage();
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = FreedomUtils.format(message);

        if (fPlayer.isStaffchat())
        {
            Bukkit.getOnlinePlayers().stream()
                    .map(p -> getPlugin().getPlayerData().getData(p.getUniqueId()))
                    .filter(FPlayer::isAdmin)
                    .forEach(staff -> staff.getPlayer().sendMessage(String.format("§a(STAFF) %s §7%s §f%s", fPlayer.getRank().getPrefix(), fPlayer.getPlayer().getName(), event.getMessage())));
            event.setCancelled(true);
        } else {
            event.setFormat(fPlayer.getTag().isEmpty() ? fPlayer.getRank().getPrefix() + " " + ChatColor.WHITE + fPlayer.getUsername() + "§7:§r " + message :
                    ChatColor.translateAlternateColorCodes('&', fPlayer.getTag()) + " " + ChatColor.WHITE + fPlayer.getUsername() + "§7:§r " + message);
        }


    }

}
