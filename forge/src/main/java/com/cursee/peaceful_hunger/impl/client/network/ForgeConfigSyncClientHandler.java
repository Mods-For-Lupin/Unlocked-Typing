package com.cursee.peaceful_hunger.impl.client.network;

import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import net.minecraftforge.network.NetworkEvent;

public class ForgeConfigSyncClientHandler {

  public static void handle(ConfigSyncS2CPacket packet, NetworkEvent.Context context) {
    context.enqueueWork(packet::handle);
  }

}
