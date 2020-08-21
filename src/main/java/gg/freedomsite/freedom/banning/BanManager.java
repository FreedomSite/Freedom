package gg.freedomsite.freedom.banning;

import gg.freedomsite.freedom.Freedom;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BanManager
{

    private final String SELECT = "SELECT * FROM `bans` WHERE banneduuid=?";
    private final String INSERT = "INSERT INTO `bans` (`banneduuid`, `banner`, `reason`, `duration`, `banneddate`, `banned`) VALUES (?, ?, ?, ?, ?, ?);";
    private final String UPDATE = "UPDATE `bans` SET banner=?, reason=?, duration=?, banneddate=?, banned=? WHERE banneduuid=?";

    private Freedom plugin;

    public BanManager(Freedom plugin)
    {
        this.plugin = plugin;
    }

    public void executeBan(Ban ban)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, ban.getBannedUUID().toString());
            statement.setString(2, ban.getBanner().toString());
            statement.setString(3, ban.getReason());
            statement.setLong(4, ban.getDuration());
            statement.setLong(5, ban.getDate());
            statement.setBoolean(6, ban.isBanned());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isBanned(UUID uuid)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SELECT);
            statement.setString(1, uuid.toString());
            ResultSet set = statement.executeQuery();
            if (set.next())
            {
                return set.getBoolean("banned"); //default true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Ban> getBans(UUID uuid)
    {
        try (Connection connection = plugin.getSql().getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(SELECT);
            statement.setString(1, uuid.toString());
            ResultSet set = statement.executeQuery();
            List<Ban> bans = new ArrayList<>();
            if (set.next())
            {
                Ban ban = new Ban(
                        UUID.fromString(set.getString("banneduuid")),
                        UUID.fromString(set.getString("banner")),
                        set.getString("reason"),
                        set.getLong("duration"),
                        set.getLong("banneddate"));
                ban.setBanned(set.getBoolean("banned"));
                bans.add(ban);
            }
            return bans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeBan(Ban ban)
    {
        try (Connection con = plugin.getSql().getConnection())
        {
            PreparedStatement statement = con.prepareStatement(UPDATE);
            statement.setString(1, ban.getBanner().toString());
            statement.setString(2, ban.getReason());
            statement.setLong(3, ban.getDuration());
            statement.setLong(4, ban.getDate());
            statement.setBoolean(5, ban.isBanned());
            statement.setString(6, ban.getBannedUUID().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ban> getBans()
    {
        List<Ban> bans = new ArrayList<>();
        try (Connection con = plugin.getSql().getConnection())
        {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM `bans`");
            ResultSet set = statement.executeQuery();
            if (set.next())
            {
                Ban ban = new Ban(
                        UUID.fromString(set.getString("banneduuid")),
                        UUID.fromString(set.getString("banner")),
                        set.getString("reason"),
                        set.getLong("duration"),
                        set.getLong("banneddate"));
                ban.setBanned(set.getBoolean("banned"));
                bans.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bans;
    }


}
