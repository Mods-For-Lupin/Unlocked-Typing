package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.impl.common.config.PeacefulHungerConfig;
import net.minecraft.resources.ResourceLocation;

public class UnlockedTyping {

  public static void init() {
    new PeacefulHungerConfig().onLoad();
  }

  public static ResourceLocation identifier(String path) {
    return new ResourceLocation(Constants.MOD_ID, path);
  }
}