package gg.freedomsite.freedom.ranking;

public enum Rank
{
    NON(-2, "§aNon-Op", "§a"),
    IMPOSTER(-1, "an §eImposter", "§e§lIMPOSTER"),
    OP(0, "an §cOp", "§c§lOP"),
    MOD(1, "a §9Mod", "§9§lMOD"),
    ADMIN(2, "an §2Admin", "§2§lADMIN"),
    EXECUTIVE(3, "an §6Executive", "§6§lEXECUTIVE"),
    MANAGEMENT(4, "a part of §4Management", "§4§lMANAGEMENT");

    private int rankLevel;
    private String loginMsg;
    private String prefix;

    Rank(int rankLevel, String loginMsg, String prefix)
    {
        this.rankLevel = rankLevel;
        this.loginMsg = loginMsg;
        this.prefix = prefix;
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
