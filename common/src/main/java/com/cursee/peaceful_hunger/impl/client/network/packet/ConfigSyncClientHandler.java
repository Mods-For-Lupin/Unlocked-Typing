package com.cursee.peaceful_hunger.impl.client.network.packet;

import com.cursee.peaceful_hunger.Constants;
import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;

public class ConfigSyncClientHandler {

  public static void handle(ConfigSyncS2CPacket packet) {

    Constants.LOG.info("Synchronizing server -> client configuration values for Peaceful Hunger's hunger difficulty and natural regeneration game rule.");

    PeacefulHungerConfig.getInstance().setHungerDifficulty(packet.getHungerDifficulty());
    PeacefulHungerConfig.getInstance().setNaturalRegenAllowedInPeaceful(packet.isNaturalRegenAllowedInPeaceful());
  }
}
