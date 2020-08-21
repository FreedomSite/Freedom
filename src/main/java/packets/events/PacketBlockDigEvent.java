package packets.events;

import net.minecraft.server.v1_16_R2.PacketPlayInBlockDig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketBlockDigEvent extends Event
{

    private Player player;
    private PacketPlayInBlockDig packet;
    private Location location;


    private boolean isCancelled;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PacketBlockDigEvent(Player player, PacketPlayInBlockDig packet, Location location)
    {
        super(true);
        this.player = player;
        this.packet = packet;
        this.location = location;
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

    public PacketPlayInBlockDig getPacket() {
        return packet;
    }

    public Location getLocation() {
        return location;
    }
}
