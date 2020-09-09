package gg.freedomsite.freedom.listeners.impl;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.banning.Ban;
import gg.freedomsite.freedom.listeners.FreedomListener;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import gg.freedomsite.freedom.ranking.Rank;
import gg.freedomsite.freedom.utils.FreedomUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;

import java.awt.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

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
        if (!fPlayer.isVanished() && fPlayer.isImposter() && !fPlayer.isAdmin() && fPlayer.getLinkedDiscordID() != 0) //if they aren't vanished, are an imposter, are not a staff member, and are linked
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + ChatColor.YELLOW + "Player Imposter" + ChatColor.AQUA + "!");
        }
        else if (!fPlayer.isVanished() && fPlayer.isImposter() && fPlayer.isAdmin()) //are not vanished, is an imposter, and is a staff
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + Rank.IMPOSTER.getLoginMsg());
        }
        else if (!fPlayer.isVanished() && !fPlayer.isImposter() && fPlayer.isAdmin() && fPlayer.getLoginMSG().isEmpty()) //is not vanished, is not an imposter, is a staff, and doesnt have a custom login msg
        {
            Bukkit.broadcastMessage("§b" + fPlayer.getUsername() + " is " + fPlayer.getRank().getLoginMsg());
        } else if (!fPlayer.isVanished() && !fPlayer.isImposter() && fPlayer.isAdmin() && !fPlayer.getLoginMSG().isEmpty()) //is not vanished, is not an imposter, is a staff, and does have a custom login msg
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

        getPlugin().getWorldEditBridge().setLimit(fPlayer.getPlayer(), 8000);
        if (!fPlayer.isAdmin() && !fPlayer.isImposter() && !fPlayer.getRank().isAtleast(Rank.OP)) {
            fPlayer.getPlayer().sendMessage(ChatColor.GRAY + "Setting you to OP...");
            fPlayer.setRank(Rank.OP);
            playerData.update(fPlayer);
        }

        getPlugin().getDiscordBot().getChatChannel().sendMessage(new EmbedBuilder().setDescription(player.getName() + " has connected.").setColor(Color.GREEN).build()).queue();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        PlayerData playerData = getPlugin().getPlayerData();
        event.getPlayer().removeAttachment(playerData.getData(event.getPlayer().getUniqueId()).getAttachment());
        playerData.getPlayers().remove(event.getPlayer().getUniqueId());


        getPlugin().getDiscordBot().getChatChannel().sendMessage(new EmbedBuilder().setDescription(event.getPlayer().getName() + " has disconnected.").setColor(Color.RED).build()).queue();
    }

    @EventHandler
    public void onDamageEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player player = (Player) event.getDamager();
            if (player.getGameMode()== GameMode.CREATIVE) {
                event.getDamager().sendMessage(ChatColor.RED + "[Freedom] Creative PVP is disabled!");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        PlayerData playerData = getPlugin().getPlayerData();
        FPlayer fPlayer = playerData.getData(event.getPlayer().getUniqueId());

        if (fPlayer.isMuted())
        {
            fPlayer.getPlayer().sendMessage("§cYou are currently muted!");
            event.setCancelled(true);
            return;
        }

        if (fPlayer.isImposter())
        {
            getPlugin().getDiscordBot().getChatChannel().sendMessage(new EmbedBuilder().setDescription("(IMP) " + ChatColor.stripColor(fPlayer.getPlayer().getDisplayName()) + ": " + ChatColor.stripColor(event.getMessage())).setColor(Color.YELLOW).build()).queue();
            event.setFormat(Rank.IMPOSTER.getPrefix() + " " + ChatColor.WHITE + fPlayer.getPlayer().getDisplayName() + "§7:§r " + event.getMessage());
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
                    .forEach(staff -> staff.getPlayer().sendMessage(String.format("§a(STAFF) %s §7%s: §f%s", fPlayer.getRank().getPrefix(), fPlayer.getPlayer().getName(), event.getMessage())));
            event.setCancelled(true);
        } else {
            event.setFormat(fPlayer.getTag().isEmpty() ? fPlayer.getRank().getPrefix() + " " + ChatColor.WHITE + fPlayer.getPlayer().getDisplayName() + "§7:§r " + message :
                    ChatColor.translateAlternateColorCodes('&', fPlayer.getTag()) + " " + ChatColor.WHITE + fPlayer.getPlayer().getDisplayName() + "§7:§r " + message);
            getPlugin().getDiscordBot().getChatChannel().sendMessage(new EmbedBuilder().setDescription(ChatColor.stripColor(fPlayer.getRank().getPrefix()) + " " + ChatColor.stripColor(fPlayer.getPlayer().getDisplayName()) + ": " + ChatColor.stripColor(event.getMessage())).setColor(Color.YELLOW).build()).queue();
        }
    }


    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        FPlayer fPlayer = getPlugin().getPlayerData().getData(player.getUniqueId());
        if (fPlayer.isFrozen())
        {
            player.teleport(player.getLocation());
            player.sendMessage("§cYou're currently frozen!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event)
    {

        FPlayer fPlayer = getPlugin().getPlayerData().getPlayerFromIP(event.getAddress().getHostAddress().trim());

        if (fPlayer == null) {
            if (getPlugin().getConfig().getBoolean("server.staffmode")) {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                event.setKickMessage("§cStaffMode is currently enabled, only staff may join at this time.");
                return;
            }
        }

        assert fPlayer != null;
        if (getPlugin().getConfig().getBoolean("server.staffmode") && !fPlayer.isAdmin())
        {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§cStaffMode is currently enabled, only staff may join at this time.");
            return;
        }

        for (Ban ban : getPlugin().getBanManager().getBans())
        {
            if (ban.isBanned())
            {
                if (ban.getBannedUUID().toString().equalsIgnoreCase(fPlayer.getUuid().toString()))
                {
                    if (ban.getDuration() == 0)
                    {
                        OfflinePlayer banner = (ban.getBanner() == null ? null : Bukkit.getOfflinePlayer(ban.getBanner()));
                         event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                         event.setKickMessage("§cYou have been permanently banned by §f" + (banner == null ? "Freedom" : banner.getName()) + "\n" +
                                 "§cReason: §f" + ban.getReason() + "\n" +
                                 "§cAppeal @ §fhttps://freedomsite.boards.net");
                    } else {
                        OfflinePlayer banner = (ban.getBanner() == null ? null : Bukkit.getOfflinePlayer(ban.getBanner()));

                        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(ban.getDuration()), TimeZone.getTimeZone("America/New_York").toZoneId());

                        ZonedDateTime convertedTime = time.atZone(ZoneId.of("UTC")).withZoneSameInstant(TimeZone.getTimeZone("America/New_York").toZoneId());
                        convertedTime = convertedTime.minusHours(1);
                        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                        event.setKickMessage("§cYou have been banned by §f" + (banner == null ? "Freedom" : banner.getName()) + " §cfor §f10 minutes!\n" +
                                "§cReason: §f" + ban.getReason() + "\n" +
                                "§cUnban Date: §f" + Timestamp.valueOf(convertedTime.toLocalDateTime()) + " EST");
                    }

                }
            }
        }
    }

}
