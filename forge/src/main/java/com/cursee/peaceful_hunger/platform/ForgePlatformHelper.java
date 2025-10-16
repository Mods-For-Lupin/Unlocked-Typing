package com.cursee.peaceful_hunger.platform;

import com.cursee.peaceful_hunger.PeacefulHungerForge;
import com.cursee.peaceful_hunger.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "Forge";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return ModList.get().isLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return !FMLLoader.isProduction();
  }

  @Override
  public Path getGameDirectory() {

    return FMLLoader.getGamePath();
  }

  @Override
  public String getGameDirectoryString() {

    return getGameDirectory().toString();
  }

  @Override
  public <T> void sendToPlayer(ServerPlayer serverPlayer, T packet) {
    PeacefulHungerForge.sendToPlayer(serverPlayer, packet);
  }
}