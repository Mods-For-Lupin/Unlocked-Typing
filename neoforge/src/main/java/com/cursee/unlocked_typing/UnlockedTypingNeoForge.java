package com.cursee.unlocked_typing;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class UnlockedTypingNeoForge {

  public UnlockedTypingNeoForge(IEventBus eventBus) {
    UnlockedTyping.init();

    if (FMLLoader.getDist() == Dist.CLIENT) {
      new UnlockedTypingClientNeoForge();
    }
  }
}