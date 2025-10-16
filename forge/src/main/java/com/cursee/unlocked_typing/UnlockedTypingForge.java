package com.cursee.unlocked_typing;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class UnlockedTypingForge {

  public UnlockedTypingForge(FMLJavaModLoadingContext context) {
    UnlockedTyping.init();
  }

  public UnlockedTypingForge() {
    this(FMLJavaModLoadingContext.get());
  }
}