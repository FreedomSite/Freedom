package gg.freedomsite.freedom.tasks;

import gg.freedomsite.freedom.Freedom;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class UnbannerTask extends BukkitRunnable
{
    public UnbannerTask()
    {
        this.runTaskTimer(Freedom.get(), 0,  20);
    }

    @Override
    public void run()
    {
        LocalDateTime time = LocalDateTime.from(LocalDateTime.now().atOffset(ZoneOffset.UTC));

        Freedom.get().getBanManager().getBans().forEach(ban -> {
            if (ban.isBanned())
            {
                if (ban.getDuration() != 0)
                {
                    LocalDateTime duration = LocalDateTime.ofInstant(Instant.ofEpochSecond(ban.getDuration()), ZoneId.ofOffset("UTC", ZoneOffset.UTC));
                    if (time.getHour() == duration.getHour() && time.getMinute() == duration.getMinute())
                    {
                        Freedom.get().getServer().broadcastMessage("§cFreedom - Unbanning " + Bukkit.getOfflinePlayer(ban.getBannedUUID()).getName());
                        ban.setBanned(false);
                        Freedom.get().getBanManager().removeBan(ban);
                    }
                }

            }
        });
    }
}
