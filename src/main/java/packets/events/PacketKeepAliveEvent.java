package packets.events;

import net.minecraft.server.v1_16_R2.PacketPlayInKeepAlive;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketKeepAliveEvent extends Event
{

    private Player player;
    private PacketPlayInKeepAlive packet;
    private long keepAliveID;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PacketKeepAliveEvent(Player player, PacketPlayInKeepAlive packet, long keepAliveID)
    {
        super(true);
        this.player = player;
        this.packet = packet;
        this.keepAliveID = keepAliveID;
    }

    @Override
    public HandlerList getHandlers()
    {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public PacketPlayInKeepAlive getPacket() {
        return packet;
    }

    public long getKeepAliveID() {
        return keepAliveID;
    }

}
