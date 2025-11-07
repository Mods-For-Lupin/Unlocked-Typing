package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.Constants;
import com.cursee.unlocked_typing.platform.Services;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class NeoForgeMinecraftMixin {

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void unlocked_typing$clinit(CallbackInfo ci) {

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      System.out.println(Constants.PREFIX + "Minecraft initialized, Forge mixins applied");
    }
  }
}
