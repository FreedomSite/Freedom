package gg.freedomsite.freedom.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils
{

    public static long toFutureDate(String duration)
    {
        if (duration.endsWith("m")) // minutes
        {
            duration = duration.replace("m", "");
            long durationAsLong = Long.parseLong(duration);
            return LocalDateTime.now().plusMinutes(durationAsLong).toEpochSecond(ZoneOffset.UTC);
        }
        else if (duration.endsWith("s")) //seconds
        {
            duration = duration.replace("s", "");
            long durationAsLong = Long.parseLong(duration);
            return LocalDateTime.now().plusSeconds(durationAsLong).toEpochSecond(ZoneOffset.UTC);
        }
        else if (duration.endsWith("mo"))
        {
            duration = duration.replace("d", "");
            long durationAsLong = Long.parseLong(duration);
            return LocalDateTime.now().plusDays(durationAsLong).toEpochSecond(ZoneOffset.UTC);
        }
        return 0;
    }

}
