package com.cursee.peaceful_hunger;

import com.cursee.peaceful_hunger.impl.client.network.ForgeConfigSyncClientHandler;
import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Constants.MOD_ID)
public class PeacefulHungerForge {

  private static int packetId = 0;
  private static SimpleChannel NETWORK;

  public PeacefulHungerForge(FMLJavaModLoadingContext context) {
    PeacefulHunger.init();

//    IEventBus modEventBus = context.getModEventBus();
//
//    modEventBus.addListener((Consumer<RegisterPayloadHandlersEvent>) event -> {
//      final PayloadRegistrar registrar = event.registrar("1");
//      registrar.playToClient(
//          Packets.CONFIG_SYNC_ID,
//          Packets.CONFIG_SYNC_CODEC,
//          NeoForgeConfigSyncClientHandler::handle // handle as enqueued work
//      );
//    });

    SimpleChannel net = NetworkRegistry.ChannelBuilder.named(PeacefulHunger.identifier("network")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true)
        .serverAcceptedVersions(s -> true).simpleChannel();

    NETWORK = net;

    net.messageBuilder(ConfigSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(ConfigSyncS2CPacket::read).encoder(ConfigSyncS2CPacket::write)
        .consumerMainThread((packet, contextSupplier) -> ForgeConfigSyncClientHandler.handle(packet, contextSupplier.get())).add();

    MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> ConfigSyncS2CPacket.createAndSend(event.getEntity(), event.getLevel()));
  }

  public PeacefulHungerForge() {
    this(FMLJavaModLoadingContext.get());
  }

  private static int id() {
    return packetId++;
  }

  public static <MSG> void sendToPlayer(ServerPlayer serverPlayer, MSG message) {
    NETWORK.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
  }
}