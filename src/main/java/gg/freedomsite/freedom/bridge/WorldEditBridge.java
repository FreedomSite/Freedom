package gg.freedomsite.freedom.bridge;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import gg.freedomsite.freedom.Freedom;
import org.bukkit.entity.Player;

public class WorldEditBridge
{


    public WorldEditPlugin getWorldEditPlugin()
    {
        if (Freedom.get().getServer().getPluginManager().isPluginEnabled("WorldEdit"))
        {
            return WorldEditPlugin.getPlugin(WorldEditPlugin.class);
        }
        return null;
    }

    public void setLimit(Player player, int limit)
    {
        try
        {
            final LocalSession session = getPlayerSession(player);
            if (session != null)
            {
                session.setBlockChangeLimit(limit);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public int getDefaultLimit()
    {
        final WorldEditPlugin wep = getWorldEditPlugin();
        if (wep == null)
        {
            return 0;
        }

        return wep.getLocalConfiguration().defaultChangeLimit;

    }

    public int getMaxLimit()
    {
        final WorldEditPlugin wep = getWorldEditPlugin();
        if (wep == null)
        {
            return 0;
        }

        return wep.getLocalConfiguration().maxChangeLimit;

    }

    private LocalSession getPlayerSession(Player player)
    {
        final WorldEditPlugin wep = getWorldEditPlugin();
        if (wep == null)
        {
            return null;
        }

        try
        {
            return wep.getSession(player);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


}
