package com.cursee.unlocked_typing.mixin;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SharedConstants.class)
public class ForgeSharedConstantsMixin {

  /// @reason Allows most text fields to contain the section sign (char `§` / codepoint 167).
  @Inject(at = @At("TAIL"), method = "isAllowedChatCharacter", cancellable = true)
  private static void unlocked_typing$isAllowedChatCharacter(char character, CallbackInfoReturnable<Boolean> cir) {
    if (character == ((char) 167)) {
      cir.setReturnValue(true);
    }
  }
}
