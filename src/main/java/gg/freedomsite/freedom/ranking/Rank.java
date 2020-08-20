package gg.freedomsite.freedom.ranking;

import org.bukkit.ChatColor;

public enum Rank
{
    NON(-2, "§aNon-Op", "§a", ChatColor.GREEN),
    IMPOSTER(-1, "an §eImposter", "§e§lIMPOSTER", ChatColor.YELLOW),
    OP(0, "an §7Op", "§7§lOP", ChatColor.GRAY),
    MOD(1, "a §bMod", "§b§lMOD", ChatColor.AQUA),
    ADMIN(2, "an §cAdmin", "§c§lADMIN", ChatColor.RED),
    EXECUTIVE(3, "an §6Executive", "§6§lEXECUTIVE", ChatColor.GOLD),
    MANAGEMENT(4, "apart of §4Management", "§4§lMANAGEMENT", ChatColor.DARK_RED);

    private int rankLevel;
    private String loginMsg;
    private String prefix;
    private ChatColor color;

    Rank(int rankLevel, String loginMsg, String prefix, ChatColor color)
    {
        this.rankLevel = rankLevel;
        this.loginMsg = loginMsg;
        this.prefix = prefix;
        this.color = color;
    }

    public String getLoginMsg()
    {
        return loginMsg;
    }

    public int getRankLevel()
    {
        return rankLevel;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public boolean isAtleast(Rank rank)
    {
        return rankLevel >= rank.getRankLevel();
    }

    public ChatColor getColor() {
        return color;
    }

    public static Rank findRank(String name)
    {
        for (Rank rank : Rank.values())
        {
            if (rank.name().equalsIgnoreCase(name))
            {
                return rank;
            }
        }
        return null;
    }
}
