package gg.freedomsite.freedom;

import gg.freedomsite.freedom.config.RankConfig;
import gg.freedomsite.freedom.handlers.CommandHandler;
import gg.freedomsite.freedom.handlers.ListenerHandler;
import gg.freedomsite.freedom.httpd.HttpdServerHandler;
import gg.freedomsite.freedom.httpd.modules.UserModule;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import gg.freedomsite.freedom.sql.SQLConnection;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public class Freedom extends JavaPlugin
{
    @Getter(AccessLevel.NONE)
    private static Freedom plugin;

    private SQLConnection sql;
    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;

    private HttpdServerHandler httpdServerHandler;

    private RankConfig rankConfig;

    private PlayerData playerData;

    @Override
    public void onLoad()
    {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        this.rankConfig = new RankConfig();
        rankConfig.generate();

        if (!getConfig().getBoolean("mysql.enabled") && !new File(getDataFolder(), "database.db").exists())
        {
            plugin.saveResource("database.db", false);
        }

        this.httpdServerHandler = new HttpdServerHandler();
        httpdServerHandler.addHandler("/test", new UserModule());
        httpdServerHandler.start();
    }


    @Override
    public void onEnable()
    {
        this.playerData = new PlayerData();

        // -------------HANDLERS -------------- //
        this.sql = new SQLConnection(this);

        this.commandHandler = new CommandHandler();
        commandHandler.register();

        this.listenerHandler = new ListenerHandler();
        listenerHandler.register();

        if (getServer().getOnlinePlayers().size() > 0)
        {
            for (Player players : getServer().getOnlinePlayers())
            {
                FPlayer fplayer = getPlayerData().getDataFromSQL(players.getUniqueId());
                getPlayerData().getPlayers().put(players.getUniqueId(), fplayer);
                //registering perms
                fplayer.setAttachment(players.addAttachment(Freedom.get()));
                getRankConfig().setPlayerPermissions(fplayer);
            }
        }
    }


    @Override
    public void onDisable()
    {

        if (getServer().getOnlinePlayers().size() > 0)
        {
            for (Player players : getServer().getOnlinePlayers())
            {
                getPlayerData().getPlayers().remove(players.getUniqueId());
            }
        }

        httpdServerHandler.stop();

        //commandHandler.unregister(); disable this for now
    }


    public static Freedom get() {
        return plugin;
    }
}
