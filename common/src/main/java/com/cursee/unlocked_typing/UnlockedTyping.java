package com.cursee.unlocked_typing;

import net.minecraft.resources.ResourceLocation;

public class UnlockedTyping {

  public static void init() {
  }

  public static ResourceLocation identifier(String path) {
    return new ResourceLocation(Constants.MOD_ID, path);
  }
}