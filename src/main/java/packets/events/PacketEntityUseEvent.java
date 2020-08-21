package packets.events;

import net.minecraft.server.v1_16_R2.Entity;
import net.minecraft.server.v1_16_R2.PacketPlayInUseEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import packets.enums.InteractAction;

public class PacketEntityUseEvent extends Event
{

    private Player player;
    private PacketPlayInUseEntity packet;
    private int entityID;
    private InteractAction interactAction;
    private Entity entity;

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PacketEntityUseEvent(Player player, PacketPlayInUseEntity packet, int entityID, InteractAction action, Entity entity)
    {
        super(true);
        this.player = player;
        this.packet = packet;
        this.entityID = entityID;
        this.interactAction = action;
        this.entity = entity;
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

    public PacketPlayInUseEntity getPacket() {
        return packet;
    }

    public Entity getEntity()
    {
        return entity;
    }

    public InteractAction getInteractAction() {
        return interactAction;
    }

    public int getEntityID() {
        return entityID;
    }

}
