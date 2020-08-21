package packets.events;

import net.minecraft.server.v1_16_R1.PacketPlayInSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import packets.enums.ChatMode;
import packets.enums.MainHand;

public class PacketSettingsEvent extends Event
{
    private Player player;
    private PacketPlayInSettings packet;

    private String locale;
    private int viewDistance;
    private ChatMode chatMode;
    private boolean chatColors;
    private int skinPart;
    private MainHand mainHand;


    private static final HandlerList HANDLERS_LIST = new HandlerList();

    public PacketSettingsEvent(Player player, PacketPlayInSettings packet, String locale, int viewDistance, ChatMode chatMode, boolean chatColors, int skinPart, MainHand hand)
    {
        super(true);
        this.player = player;
        this.packet = packet;

        this.locale = locale;
        this.viewDistance = viewDistance;
        this.chatMode = chatMode;
        this.chatColors = chatColors;
        this.skinPart = skinPart;
        this.mainHand = hand;

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

    public PacketPlayInSettings getPacket() {
        return packet;
    }

    public boolean isChatColors() {
        return chatColors;
    }

    public ChatMode getChatMode() {
        return chatMode;
    }

    public int getSkinPart() {
        return skinPart;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public MainHand getMainHand() {
        return mainHand;
    }

    public String getLocale() {
        return locale;
    }

}
