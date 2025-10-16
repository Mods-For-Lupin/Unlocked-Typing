package com.cursee.peaceful_hunger.platform;

import com.cursee.peaceful_hunger.PeacefulHunger;
import com.cursee.peaceful_hunger.impl.common.network.packet.ConfigSyncS2CPacket;
import com.cursee.peaceful_hunger.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;

public class FabricPlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {
    return "Fabric";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return FabricLoader.getInstance().isModLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return FabricLoader.getInstance().isDevelopmentEnvironment();
  }

  @Override
  public Path getGameDirectory() {

    return FabricLoader.getInstance().getGameDir();
  }

  @Override
  public String getGameDirectoryString() {

    return getGameDirectory().toString();
  }

  @Override
  public <T> void sendToPlayer(ServerPlayer serverPlayer, T packet) {
    ServerPlayNetworking.send(serverPlayer, FabricCompatiblePacket.packetFromSuper(packet));
  }

  public static class FabricCompatiblePacket extends ConfigSyncS2CPacket implements FabricPacket {

    public static final PacketType<FabricCompatiblePacket> TYPE = PacketType.create(PeacefulHunger.identifier("config"), FabricCompatiblePacket::read);

    /// ugly, UGLLYYYY
    public static <T> FabricCompatiblePacket packetFromSuper(T packet) {
      return new FabricCompatiblePacket(((ConfigSyncS2CPacket) packet).getHungerDifficulty(), ((ConfigSyncS2CPacket) packet).isNaturalRegenAllowedInPeaceful());
    }

    public FabricCompatiblePacket(Difficulty hungerDifficulty, boolean naturalRegenAllowedInPeaceful) {
      super(hungerDifficulty, naturalRegenAllowedInPeaceful);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
      super.write(buf);
    }

    @Override
    public PacketType<?> getType() {
      return TYPE;
    }

    public static FabricCompatiblePacket read(FriendlyByteBuf data) {
      return new FabricCompatiblePacket(Difficulty.byId(data.readVarInt()), data.readBoolean());
    }
  }
}
