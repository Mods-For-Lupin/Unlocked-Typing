package com.cursee.unlocked_typing.mixinOLD;

import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatFormatting.class)
public class FabricChatFormattingMixin {

  /// Use an irrelevant keycode for ignoring `9999` instead of `167`/`§`
  @Unique
  private static final Pattern UNLOCKED_TYPING$STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)" + Character.toString((char) 9999) + "[0-9A-FK-OR]");

  @Inject(at = @At("TAIL"), method = "stripFormatting", cancellable = true)
  private static void unlocked_typing$stripFormatting(String text, CallbackInfoReturnable<String> cir) {
//    if (text.contains("§")) {
//      cir.setReturnValue(UNLOCKED_TYPING$STRIP_FORMATTING_PATTERN.matcher(text).replaceAll(""));
//    }
    cir.setReturnValue(UNLOCKED_TYPING$STRIP_FORMATTING_PATTERN.matcher(text).replaceAll(""));
  }
}
