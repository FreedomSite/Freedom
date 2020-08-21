package packets;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_16_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import packets.enums.ChatMode;
import packets.enums.InteractAction;
import packets.enums.MainHand;
import packets.events.*;

public class PacketInterceptorUtil
{

    public static void inject(Player player)
    {


        try {
            ChannelDuplexHandler handler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                if (o instanceof PacketPlayInArmAnimation)
                {
                    PacketPlayInArmAnimation packet = (PacketPlayInArmAnimation) o;
                    PacketArmAnimationEvent event = new PacketArmAnimationEvent(player, packet);
                    Bukkit.getPluginManager().callEvent(event);
                }

                if (o instanceof PacketPlayInBlockDig)
                {
                    PacketPlayInBlockDig packet = (PacketPlayInBlockDig) o;
                    BlockPosition bp = packet.b();
                    PacketBlockDigEvent event = new PacketBlockDigEvent(player, packet, new Location(player.getWorld(), bp.getX(), bp.getY(), bp.getZ()));
                    Bukkit.getPluginManager().callEvent(event);
                }

                if (o instanceof PacketPlayInTeleportAccept)
                {
                    PacketPlayInTeleportAccept packet = (PacketPlayInTeleportAccept) o;
                    PacketTeleportAcceptEvent event = new PacketTeleportAcceptEvent(player, packet, packet.b());
                    Bukkit.getPluginManager().callEvent(event);
                }

                if (o instanceof PacketPlayInChat)
                {
                    PacketPlayInChat packet = (PacketPlayInChat) o;
                    PacketChatEvent event = new PacketChatEvent(player, packet, packet.b());
                    Bukkit.getPluginManager().callEvent(event);
                }

                if (o instanceof PacketPlayInSettings)
                {
                    PacketPlayInSettings packet = (PacketPlayInSettings) o;
                    PacketSettingsEvent event = new PacketSettingsEvent(player, packet, packet.locale, packet.viewDistance, ChatMode.findByInt(packet.d().a()), packet.e(), packet.f(), MainHand.valueOf(packet.getMainHand().name().toUpperCase()));
                    Bukkit.getPluginManager().callEvent(event);
                }

                if (o instanceof PacketPlayInUseEntity)
                {
                    PacketPlayInUseEntity packet = (PacketPlayInUseEntity) o;


                    if (packet.b() != PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT)
                    {
                        World world = ((CraftWorld) player.getWorld()).getHandle();

                        PacketEntityUseEvent event = new PacketEntityUseEvent(player, packet,
                            packet.a(world).getId(),
                            InteractAction.valueOf(packet.b().name().toUpperCase()),
                            packet.a(world));
                        Bukkit.getPluginManager().callEvent(event);
                    }


                }

                if (o instanceof PacketPlayInKeepAlive)
                {
                    PacketPlayInKeepAlive packet = (PacketPlayInKeepAlive) o;
                    PacketKeepAliveEvent event = new PacketKeepAliveEvent(player, packet, packet.b());
                    Bukkit.getPluginManager().callEvent(event);
                }

                super.channelRead(channelHandlerContext, o);
            }
        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), handler);
        } catch (Exception e)
        {

        }
    }


    public static void eject(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;

        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

    public static void injectEmergency()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            inject(player);
        }
    }


    public static void ejectEmergency()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            eject(player);
        }
    }

}
