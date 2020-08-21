package packets.events;

import net.minecraft.server.v1_16_R2.PacketPlayInTeleportAccept;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketTeleportAcceptEvent extends Event
{
    private Player player;
    private PacketPlayInTeleportAccept packet;
    private int teleportID;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PacketTeleportAcceptEvent(Player player, PacketPlayInTeleportAccept packet, int teleportID)
    {
        super(true);
        this.player = player;
        this.packet = packet;
        this.teleportID = teleportID;
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

    public PacketPlayInTeleportAccept getPacket() {
        return packet;
    }

    public int getTeleportID() {
        return teleportID;
    }
}
