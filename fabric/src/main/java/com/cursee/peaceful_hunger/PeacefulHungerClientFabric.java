package com.cursee.peaceful_hunger;

import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import com.cursee.peaceful_hunger.platform.FabricPlatformHelper;
import com.cursee.peaceful_hunger.platform.FabricPlatformHelper.FabricCompatiblePacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class PeacefulHungerClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    // register client receiver of packet
    // ClientPlayNetworking.registerGlobalReceiver(Packets.CONFIG_SYNC_ID, (packet, context) -> packet.handle());
    ClientPlayNetworking.registerGlobalReceiver(FabricCompatiblePacket.TYPE, (packet, player, responseSender) -> packet.handle());
  }
}
