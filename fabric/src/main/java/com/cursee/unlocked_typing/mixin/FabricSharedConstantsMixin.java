package com.cursee.unlocked_typing.mixin;

import com.cursee.unlocked_typing.platform.Services;
import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedConstants.class)
public class FabricSharedConstantsMixin {

  @Inject(at = @At("TAIL"), method = "isAllowedChatCharacter", cancellable = true)
  private static void unlocked_typing$isAllowedChatCharacter(char character, CallbackInfoReturnable<Boolean> cir) {

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      System.out.println("typed char " + Character.toString(character) + " keycode of " + ((int) character));

      if (character == 167) {
        System.out.println("Implicit character had keycode of 167 " + Character.toString(character));
        cir.setReturnValue(true);
      }
    }

    if (character == 167) {
      cir.setReturnValue(true);
    }
  }
}
