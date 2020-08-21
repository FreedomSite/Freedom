package gg.freedomsite.freedom.banning;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Ban
{
    private UUID bannedUUID;
    private UUID banner;
    private String reason;
    private long duration;
    private long date;
    @Setter
    private boolean banned;

    public Ban(UUID bannedUUID, UUID banner, String reason, long duration, long date)
    {
        this.bannedUUID = bannedUUID;
        this.banner = banner;
        this.reason = reason;
        this.duration = duration;
        this.date = date;
        this.banned = true;
    }


}
