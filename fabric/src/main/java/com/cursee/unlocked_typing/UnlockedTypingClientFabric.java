package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.platform.FabricPlatformHelper.FabricCompatiblePacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class UnlockedTypingClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    // register client receiver of packet
    // ClientPlayNetworking.registerGlobalReceiver(Packets.CONFIG_SYNC_ID, (packet, context) -> packet.handle());
    ClientPlayNetworking.registerGlobalReceiver(FabricCompatiblePacket.TYPE, (packet, player, responseSender) -> packet.handle());
  }
}
