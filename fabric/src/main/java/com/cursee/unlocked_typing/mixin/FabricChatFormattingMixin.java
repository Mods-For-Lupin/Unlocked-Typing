package com.cursee.unlocked_typing.mixin;

import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatFormatting.class)
public class FabricChatFormattingMixin {

  /// Used in replacement of {@link ChatFormatting}'s STRIP_FORMATTING_PATTERN, replacing codepoint 167 / `§` with an unused value, `9999`.
  @Unique
  private static final Pattern UNLOCKED_TYPING$STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)" + Character.toString((char) 9999) + "[0-9A-FK-OR]");

  /// @reason Replaces Minecraft's internal regex for formatting strings with a custom regex pattern allowing codepoint 167 / `§`.
  @Inject(at = @At("TAIL"), method = "stripFormatting", cancellable = true)
  private static void unlocked_typing$stripFormatting(String text, CallbackInfoReturnable<String> cir) {
    if (text != null) {
      cir.setReturnValue(UNLOCKED_TYPING$STRIP_FORMATTING_PATTERN.matcher(text).replaceAll(""));
    }
  }
}
