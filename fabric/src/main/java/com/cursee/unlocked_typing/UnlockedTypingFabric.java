package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.impl.common.network.packet.ConfigSyncS2CPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

public class UnlockedTypingFabric implements ModInitializer {

  @Override
  public void onInitialize() {
    UnlockedTyping.init();

    // register server packets in common
//    PayloadTypeRegistry.playS2C().register(Packets.CONFIG_SYNC_ID, Packets.CONFIG_SYNC_CODEC);
//    ServerSidePacketRegistry.INSTANCE.register();

    ServerEntityEvents.ENTITY_LOAD.register(ConfigSyncS2CPacket::createAndSend);
  }
}
