package gg.freedomsite.freedom.player;

import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.ranking.Rank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

@Getter
@Setter
public class FPlayer
{

    @Setter(AccessLevel.NONE)
    private UUID uuid;

    private String username;
    private int rank;
    private String ip;
    private String tag;
    private String loginMSG;
    private boolean muted;
    private boolean frozen;
    private boolean imposter;
    private boolean commandspy;
    private boolean vanished;
    private boolean staffchat; //does not need to be stored in db

    private PermissionAttachment attachment; //manages perms

    public FPlayer(UUID uuid)
    {
        this.uuid = uuid;
        this.username = "";
        this.rank = -2;
        this.ip = "";
        this.tag = "";
        this.loginMSG = "";
        this.muted = false;
        this.frozen = false;
        this.imposter = false;
        this.commandspy = false;
        this.vanished = false;
        this.staffchat = false;
        this.attachment = null;
    }

    public Rank getRank()
    {
        for (Rank rank : Rank.values())
        {
            if (rank.getRankLevel() == this.rank)
            {
                return rank;
            }
        }
        return Rank.NON;
    }

    public void setRank(Rank rank)
    {
        this.rank = rank.getRankLevel();
    }

    public void setRank(int rank)
    {
        this.rank = rank;
    }


    public boolean isAdmin()
    {
        return this.rank >= 1;
    }

    public Player getPlayer()
    {
        return Bukkit.getPlayer(getUuid());
    }


}
