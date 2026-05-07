package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.Constants;
import com.cursee.unlocked_typing.platform.PlatformHelper;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

  @Inject(at = @At("TAIL"), method = "<init>")
  private void init(CallbackInfo info) {
    if (PlatformHelper.isDevelopmentEnvironment()) {
      System.out.println(Constants.PREFIX + "Minecraft initialized on " + PlatformHelper.getPlatformName());
    }
  }
}