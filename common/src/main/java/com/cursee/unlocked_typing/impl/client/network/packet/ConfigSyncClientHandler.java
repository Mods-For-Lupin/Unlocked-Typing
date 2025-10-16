package com.cursee.unlocked_typing.impl.client.network.packet;

import com.cursee.unlocked_typing.Constants;
import com.cursee.unlocked_typing.impl.common.config.PeacefulHungerConfig;
import com.cursee.unlocked_typing.impl.common.network.packet.ConfigSyncS2CPacket;

public class ConfigSyncClientHandler {

  public static void handle(ConfigSyncS2CPacket packet) {

    Constants.LOG.info("Synchronizing server -> client configuration values for Peaceful Hunger's hunger difficulty and natural regeneration game rule.");

    PeacefulHungerConfig.getInstance().setHungerDifficulty(packet.getHungerDifficulty());
    PeacefulHungerConfig.getInstance().setNaturalRegenAllowedInPeaceful(packet.isNaturalRegenAllowedInPeaceful());
  }
}
