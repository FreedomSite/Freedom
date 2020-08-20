package gg.freedomsite.freedom.sql;

import gg.freedomsite.freedom.Freedom;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection
{

    private Connection connection;
    private Freedom plugin;

    public SQLConnection(Freedom plugin)
    {
        this.plugin = plugin;
    }

    public Connection getConnection()
    {
        String host = plugin.getConfig().getString("mysql.host");
        int port = plugin.getConfig().getInt("mysql.port");
        String username = plugin.getConfig().getString("mysql.username");
        String password = plugin.getConfig().getString("mysql.password");
        String database = plugin.getConfig().getString("mysql.database");

        try {
            if (!plugin.getConfig().getBoolean("mysql.enabled"))
            {
                connection = DriverManager.getConnection("jdbc:sqlite:" + new File(plugin.getDataFolder(), "database.db").getAbsolutePath());
            } else {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            }

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS `players` (\n" +
                    "\t`uuid` VARCHAR(36),\n" +
                    "\t`username` VARCHAR(16),\n" +
                    "\t`rank` INT,\n" +
                    "\t`ip` VARCHAR(15),\n" +
                    "\t`customtag` VARCHAR(16),\n" +
                    "\t`muted` BOOLEAN,\n" +
                    "\t`frozen` BOOLEAN,\n" +
                    "\t`imposter` BOOLEAN,\n" +
                    "\t`commandspy` BOOLEAN,\n" +
                    "\tPRIMARY KEY (`uuid`)\n" +
                    ");").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}