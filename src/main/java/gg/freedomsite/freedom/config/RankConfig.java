package gg.freedomsite.freedom.config;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.ranking.Rank;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class RankConfig
{

    private File file;
    private YamlConfiguration config;

    public void generate()
    {
        this.file = new File(Freedom.get().getDataFolder(), "ranks.yml");

        if (!file.exists())
        {
            try {
                file.createNewFile();
                Freedom.get().getLogger().info("Generating new configuration file, ranks.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.config = YamlConfiguration.loadConfiguration(file);
            loadDefaults();
        } else {
            this.config = YamlConfiguration.loadConfiguration(file);
        }


    }

    public void loadDefaults()
    {
        YamlConfiguration configuration = getConfig();
        configuration.createSection("ranks");
        for (Rank rank : Rank.values())
        {
            configuration.createSection("ranks." + rank.name().toLowerCase());
            configuration.set("ranks." + rank.name().toLowerCase() + ".permissions", Arrays.asList("example.permission"));
        }

        try {
            configuration.save(getFile());
            Freedom.get().getLogger().info("Saving configuration file, ranks.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPermissions(Rank rank)
    {
        List<String> permissions = new ArrayList<>();
        permissions.addAll(getInheritedRankPerms(rank));
        permissions.addAll(getConfig().getStringList("ranks." + rank.name().toLowerCase() + ".permissions"));
        return permissions;
    }

    public List<Rank> inheritedRanks(Rank rank)
    {
        List<Rank> inheritedRanks = new ArrayList<>();
        for (Rank ranks : Rank.values())
        {
            if (ranks.getRankLevel() < rank.getRankLevel())
            {
                inheritedRanks.add(ranks);
            }
        }
        return inheritedRanks;
    }
    public List<String> getInheritedRankPerms(Rank rank)
    {
        List<String> perms = new ArrayList<>();
        for (Rank ranks : inheritedRanks(rank))
        {
            perms.addAll(getConfig().getStringList("ranks." + ranks.name().toLowerCase() + ".permissions"));
        }
        return perms;
    }

    public void setPlayerPermissions(FPlayer player)
    {
        List<String> perms = getPermissions(player.getRank());
        for (String perm : player.getAttachment().getPermissions().keySet())
        {
            player.getAttachment().unsetPermission(perm);
        }
        perms.forEach(perm -> player.getAttachment().setPermission(perm, true));
    }


}
