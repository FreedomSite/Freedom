package packets.events;

import net.minecraft.server.v1_16_R2.PacketPlayInChat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketChatEvent extends Event
{
    private Player player;
    private PacketPlayInChat packet;
    private String message;
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PacketChatEvent(Player player, PacketPlayInChat packet, String message)
    {
        super(true);
        this.player = player;
        this.packet = packet;
        this.message = message;
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

    public PacketPlayInChat getPacket() {
        return packet;
    }


    public String getMessage() {
        return message;
    }
}
