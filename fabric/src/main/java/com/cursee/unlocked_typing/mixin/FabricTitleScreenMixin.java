package com.cursee.unlocked_typing.mixin;

import com.cursee.unlocked_typing.UnlockedTyping;
import com.cursee.unlocked_typing.platform.Services;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class FabricTitleScreenMixin {

  @Inject(at = @At("HEAD"), method = "init()V")
  private void init(CallbackInfo info) {

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      UnlockedTyping.LOG.info("This is an example mixin from Fabric!");
    }
  }
}