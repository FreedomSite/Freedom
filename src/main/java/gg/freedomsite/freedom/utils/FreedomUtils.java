package gg.freedomsite.freedom.utils;

import net.minecraft.server.v1_16_R2.MinecraftServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class FreedomUtils
{

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

}
