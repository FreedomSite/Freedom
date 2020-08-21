package packets.events;

import net.minecraft.server.v1_16_R1.PacketPlayInArmAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketArmAnimationEvent extends Event
{
    private Player player;
    private PacketPlayInArmAnimation packet;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PacketArmAnimationEvent(Player player, PacketPlayInArmAnimation packet)
    {
        super(true);
        this.player = player;
        this.packet = packet;
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

    public PacketPlayInArmAnimation getPacket() {
        return packet;
    }
}
