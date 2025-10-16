package com.cursee.peaceful_hunger.impl.common.network.packet;

import com.cursee.peaceful_hunger.impl.client.network.packet.ConfigSyncClientHandler;
import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import com.cursee.peaceful_hunger.platform.Services;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ConfigSyncS2CPacket {

  private Difficulty hungerDifficulty;
  private boolean naturalRegenAllowedInPeaceful;

  public ConfigSyncS2CPacket(Difficulty hungerDifficulty, boolean naturalRegenAllowedInPeaceful) {
    this.hungerDifficulty = hungerDifficulty;
    this.naturalRegenAllowedInPeaceful = naturalRegenAllowedInPeaceful;
  }

  public void write(FriendlyByteBuf data) {
    data.writeVarInt(this.hungerDifficulty.getId());
    data.writeBoolean(this.naturalRegenAllowedInPeaceful);
  }

  public Difficulty getHungerDifficulty() {
    return hungerDifficulty;
  }

  public boolean isNaturalRegenAllowedInPeaceful() {
    return naturalRegenAllowedInPeaceful;
  }

  public static ConfigSyncS2CPacket read(FriendlyByteBuf data) {
    return new ConfigSyncS2CPacket(Difficulty.byId(data.readVarInt()), data.readBoolean());
  }

  public static void createAndSend(Entity entity, Level level) {
    if (!(entity instanceof ServerPlayer serverPlayer) || !(level instanceof ServerLevel serverLevel)) return;
    Services.PLATFORM.sendToPlayer(serverPlayer, new ConfigSyncS2CPacket(PeacefulHungerConfig.getInstance().getHungerDifficulty(), PeacefulHungerConfig.getInstance().isNaturalRegenAllowedInPeaceful()));
  }

  public void handle() {
    ConfigSyncClientHandler.handle(this);
  }
}
