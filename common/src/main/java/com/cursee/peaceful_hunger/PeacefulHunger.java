package com.cursee.peaceful_hunger;

import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import net.minecraft.resources.ResourceLocation;

public class PeacefulHunger {

  public static void init() {
    new PeacefulHungerConfig().onLoad();
  }

  public static ResourceLocation identifier(String path) {
    return new ResourceLocation(Constants.MOD_ID, path);
  }
}