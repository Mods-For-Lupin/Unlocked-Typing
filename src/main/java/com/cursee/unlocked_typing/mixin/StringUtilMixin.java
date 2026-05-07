package com.cursee.unlocked_typing.mixin;

import net.minecraft.util.StringUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StringUtil.class)
public class StringUtilMixin {

  /// @reason Allows most text fields to contain the section sign (char `§` / codepoint 167).
  @Inject(at = @At("TAIL"), method = "isAllowedChatCharacter", cancellable = true)
  private static void unlocked_typing$isAllowedChatCharacter(int character, CallbackInfoReturnable<Boolean> cir) {
    if (character == 167) {
      cir.setReturnValue(true);
    }
  }
}
