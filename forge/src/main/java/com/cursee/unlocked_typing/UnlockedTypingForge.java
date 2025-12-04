package com.cursee.unlocked_typing;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class UnlockedTypingForge {

  public UnlockedTypingForge(FMLJavaModLoadingContext context) {
    UnlockedTyping.init();

    if (FMLLoader.getDist() == Dist.CLIENT) {
      new UnlockedTypingClientForge();
    }
  }

  @SuppressWarnings("all")
  public UnlockedTypingForge() {
    this(FMLJavaModLoadingContext.get());
  }
}