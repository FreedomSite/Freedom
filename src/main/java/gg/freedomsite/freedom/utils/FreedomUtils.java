package gg.freedomsite.freedom.utils;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import net.minecraft.server.v1_16_R2.MinecraftServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FreedomUtils
{

    private static final Pattern hexPattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");

    private static final ChatColor[] colorfulColors = new ChatColor[]{
            ChatColor.AQUA,
            ChatColor.GREEN,
            ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW,
            ChatColor.GOLD,
            ChatColor.RED,
            ChatColor.BLUE,
            ChatColor.DARK_AQUA
    };

    public static ChatColor randomColor()
    {
        return colorfulColors[ThreadLocalRandom.current().nextInt(colorfulColors.length)];
    }



    public static String format(String message) {
        Matcher matcher = hexPattern.matcher(message);

        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, "" + net.md_5.bungee.api.ChatColor.of(color));
        }

        return message;
    }

    public static String getVersion()
    {
        return getServer().getVersion();
    }

    private static MinecraftServer getServer()
    {
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

    public static String getUUID(String name)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://api.mojang.com/users/profiles/minecraft/" + name);
        try {
            HttpResponse response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200)
            {
                String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JSONObject object = new JSONObject(json);
                client.close();
                String uuid = object.getString("id");
                String formatted = new StringBuilder(uuid).insert(12, '-')
                .insert(8, '-')
                .insert(18, '-')
                .insert(23, '-').toString();
                return formatted;
            } else {
                return "not found";
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return "not found";
    }

    public static void vanish(FPlayer player, Collection<? extends Player> players, boolean vanish)
    {
        if (vanish)
        {
            player.getPlayer().setPlayerListName("§7§o" + player.getPlayer().getName());
            players.stream()
                    .map(p -> Freedom.get().getPlayerData().getData(p.getUniqueId()))
                    .filter(p -> !p.isAdmin())
                    .forEach(fPlayer -> {
                        fPlayer.getPlayer().hidePlayer(Freedom.get(), player.getPlayer());
                    });
        } else {
            player.getPlayer().setPlayerListName(player.getRank().getColor() + player.getPlayer().getName());
            players.forEach(p -> {
                p.showPlayer(Freedom.get(), player.getPlayer());
            });
        }
    }

    public static void resetToOP(FPlayer fPlayer)
    {
        fPlayer.setVanished(false);
        fPlayer.setCommandspy(false);
        fPlayer.setLoginMSG("");
        fPlayer.setStaffchat(false);
    }


}
