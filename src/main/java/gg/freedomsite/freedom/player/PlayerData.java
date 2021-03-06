package gg.freedomsite.freedom.player;

import com.google.common.collect.Maps;
import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class PlayerData
{
    private final Map<UUID, FPlayer> players = Maps.newHashMap();

    public final String SELECT = "SELECT * FROM `players` WHERE uuid=?";
    public final String INSERT = "INSERT INTO `players` (`uuid`, `username`, `rank`, `ip`, `customtag`, `loginmessage`, `muted`, `frozen`, `imposter`, `commandspy`, `vanished`, `discordID`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public final String UPDATE = "UPDATE `players` SET uuid=?, username=?, rank=?, ip=?, customtag=?, loginmessage=?, muted=?, frozen=?, imposter=?, commandspy=?, vanished=?, discordID=? WHERE uuid=?";

    private Freedom plugin = Freedom.get();


    public Map<UUID, FPlayer> getPlayers() {
        return players;
    }

    public FPlayer getData(UUID uuid)
    {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null)
        {
            return getDataFromSQL(uuid);
        } else {
            return getPlayers().get(uuid);
        }
    }

    public FPlayer getDataFromSQL(UUID uuid)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SELECT);
            statement.setString(1, uuid.toString());
            ResultSet set = statement.executeQuery();
            if (set.next())
            {
                FPlayer fplayer = new FPlayer(uuid);
                String username = set.getString("username");
                int rank = set.getInt("rank");
                String ip = set.getString("ip");
                String customtag = set.getString("customtag");
                String loginMSG = set.getString("loginmessage");
                boolean muted = set.getBoolean("muted");
                boolean frozen = set.getBoolean("frozen");
                boolean imposter = set.getBoolean("imposter");
                boolean commandspy = set.getBoolean("commandspy");
                boolean vanished = set.getBoolean("vanished");
                long discordID = set.getLong("discordID");

                fplayer.setUsername(username);
                fplayer.setRank(rank);
                fplayer.setIp(ip);
                fplayer.setTag(customtag);
                fplayer.setLoginMSG(loginMSG);
                fplayer.setMuted(muted);
                fplayer.setFrozen(frozen);
                fplayer.setImposter(imposter);
                fplayer.setCommandspy(commandspy);
                fplayer.setVanished(vanished);
                fplayer.setLinkedDiscordID(discordID);
                return fplayer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FPlayer getPlayerFromIP(String address)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `players` WHERE ip=?");
            statement.setString(1, address);
            ResultSet set = statement.executeQuery();
            if (set.next())
            {
                FPlayer fplayer = new FPlayer(UUID.fromString(set.getString("uuid")));
                String username = set.getString("username");
                int rank = set.getInt("rank");
                String ip = set.getString("ip");
                String customtag = set.getString("customtag");
                String loginMSG = set.getString("loginmessage");
                boolean muted = set.getBoolean("muted");
                boolean frozen = set.getBoolean("frozen");
                boolean imposter = set.getBoolean("imposter");
                boolean commandspy = set.getBoolean("commandspy");
                boolean vanished = set.getBoolean("vanished");
                long discordID = set.getLong("discordID");

                fplayer.setUsername(username);
                fplayer.setRank(rank);
                fplayer.setIp(ip);
                fplayer.setTag(customtag);
                fplayer.setLoginMSG(loginMSG);
                fplayer.setMuted(muted);
                fplayer.setFrozen(frozen);
                fplayer.setImposter(imposter);
                fplayer.setCommandspy(commandspy);
                fplayer.setVanished(vanished);
                fplayer.setLinkedDiscordID(discordID);
                return fplayer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean exists(UUID uuid)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SELECT);
            statement.setString(1, uuid.toString());
            ResultSet set = statement.executeQuery();
            return set.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insert(FPlayer player)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, player.getUuid().toString());
            statement.setString(2, player.getUsername());
            statement.setInt(3, player.getRank().getRankLevel());
            statement.setString(4, player.getIp());
            statement.setString(5, player.getTag());
            statement.setString(6, player.getLoginMSG());
            statement.setBoolean(7, player.isMuted());
            statement.setBoolean(8, player.isFrozen());
            statement.setBoolean(9, player.isImposter());
            statement.setBoolean(10, player.isCommandspy());
            statement.setBoolean(11, player.isVanished());
            statement.setLong(12, player.getLinkedDiscordID());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(FPlayer player)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, player.getUuid().toString());
            statement.setString(2, player.getUsername());
            statement.setInt(3, player.getRank().getRankLevel());
            statement.setString(4, player.getIp());
            statement.setString(5, player.getTag());
            statement.setString(6, player.getLoginMSG());
            statement.setBoolean(7, player.isMuted());
            statement.setBoolean(8, player.isFrozen());
            statement.setBoolean(9, player.isImposter());
            statement.setBoolean(10, player.isCommandspy());
            statement.setBoolean(11, player.isVanished());
            statement.setLong(12, player.getLinkedDiscordID());
            statement.setString(13, player.getUuid().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Rank getRankFromInt(int level)
    {
        for (Rank rank : Rank.values())
        {
            if (rank.getRankLevel() == level)
            {
                return rank;
            }
        }
        return Rank.NON;
    }
}
