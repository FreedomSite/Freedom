package gg.freedomsite.freedom;

import gg.freedomsite.freedom.banning.BanManager;
import gg.freedomsite.freedom.bridge.WorldEditBridge;
import gg.freedomsite.freedom.config.RankConfig;
import gg.freedomsite.freedom.handlers.CommandHandler;
import gg.freedomsite.freedom.handlers.ListenerHandler;
import gg.freedomsite.freedom.httpd.HttpdServerHandler;
import gg.freedomsite.freedom.httpd.modules.UserModule;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.player.PlayerData;
import gg.freedomsite.freedom.punishments.Freezer;
import gg.freedomsite.freedom.punishments.Muter;
import gg.freedomsite.freedom.sql.SQLConnection;
import gg.freedomsite.freedom.tasks.UnbannerTask;
import gg.freedomsite.freedom.world.Flatlands;
import gg.freedomsite.freedom.world.StaffWorld;
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

    private BanManager banManager;
    private Muter muter;
    private Freezer freezer;

    private StaffWorld staffWorld;
    private Flatlands flatlands;

    private RankConfig rankConfig;

    private PlayerData playerData;

    private WorldEditBridge worldEditBridge;

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
        //httpdServerHandler.addHandler("/test", new UserModule());
        //httpdServerHandler.start();

    }


    @Override
    public void onEnable()
    {
        this.banManager = new BanManager(this);
        this.muter = new Muter();
        this.freezer = new Freezer();

        this.staffWorld = new StaffWorld();
        this.flatlands = new Flatlands();


        this.playerData = new PlayerData();

        // -------------HANDLERS -------------- //
        this.sql = new SQLConnection(this);

        this.commandHandler = new CommandHandler();
        commandHandler.register();

        this.listenerHandler = new ListenerHandler();
        listenerHandler.register();

        if (getServer().getPluginManager().isPluginEnabled("WorldEdit"))
        {
            this.worldEditBridge = new WorldEditBridge();
        }


        getServer().getOnlinePlayers().forEach(player -> {
            FPlayer fplayer = getPlayerData().getDataFromSQL(player.getUniqueId());
            getPlayerData().getPlayers().put(player.getUniqueId(), fplayer);

            //registering perms
            fplayer.setAttachment(player.addAttachment(Freedom.get()));
            getRankConfig().setPlayerPermissions(fplayer);
        });

        new UnbannerTask();
    }



    @Override
    public void onDisable()
    {

        getServer().getOnlinePlayers().forEach(player -> {
            player.removeAttachment(getPlayerData().getData(player.getUniqueId()).getAttachment());
            getPlayerData().getPlayers().remove(player.getUniqueId());
        });

       // httpdServerHandler.stop();

        //commandHandler.unregister(); disable this for now
    }


    public static Freedom get() {
        return plugin;
    }
}
