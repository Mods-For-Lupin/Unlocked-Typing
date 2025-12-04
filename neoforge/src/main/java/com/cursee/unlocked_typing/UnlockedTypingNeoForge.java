package com.cursee.unlocked_typing;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class UnlockedTypingNeoForge {

  public UnlockedTypingNeoForge(IEventBus eventBus) {
    UnlockedTyping.init();
  }
}