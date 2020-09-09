package gg.freedomsite.freedom.cache;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DiscordCache
{

    private static final Map<Long, UUID> linkedCodes = new HashMap<>();

    public static Map<Long, UUID> getLinkedCodes() {
        return linkedCodes;
    }

    public static boolean isValidCode(long code)
    {
        for (long values : linkedCodes.keySet())
        {
            if (values == code)
            {
                return true;
            }
        }
        return false;
    }

    public static FPlayer getPlayerFromCode(long code)
    {
        FPlayer fPlayer = Freedom.get().getPlayerData().getData(getLinkedCodes().get(code));
        return fPlayer;
    }
}
