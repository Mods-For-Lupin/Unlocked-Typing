package com.cursee.peaceful_hunger;

import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class PeacefulHungerFabric implements ModInitializer {

  @Override
  public void onInitialize() {
    PeacefulHunger.init();

    // register server packets in common
//    PayloadTypeRegistry.playS2C().register(Packets.CONFIG_SYNC_ID, Packets.CONFIG_SYNC_CODEC);
//    ServerSidePacketRegistry.INSTANCE.register();

    ServerEntityEvents.ENTITY_LOAD.register(ConfigSyncS2CPacket::createAndSend);
  }
}
