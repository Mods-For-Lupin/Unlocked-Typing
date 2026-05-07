package com.cursee.unlocked_typing.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignEditScreen.class)
public abstract class AbstractSignEditScreenMixin {

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 100;
  @Unique
  private static FormattedCharSequence unlocked_typing$preformattedTextTitle;
  @Unique
  private static String unlocked_typing$preformattedTextTitleString;

  @Shadow @Final protected SignBlockEntity sign;
  @Shadow @Final private String[] messages;

  @Shadow protected abstract Vector3f getSignTextScale();

  @Inject(at = @At("TAIL"), method = "extractSignText")
  private void unlocked_typing$extractSignText$renderPreformattedText(GuiGraphicsExtractor graphics, Vector2f cursorPosOutput, CallbackInfo ci) {
    Font font = ((ScreenAccessor) this).getFont();
    if (unlocked_typing$preformattedTextTitle == null && unlocked_typing$preformattedTextTitleString == null) {
      unlocked_typing$preformattedTextTitleString = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
      unlocked_typing$preformattedTextTitle = FormattedCharSequence.forward(unlocked_typing$preformattedTextTitleString, Style.EMPTY.withUnderlined(true));
    }

    int textColor = DyeColor.LIGHT_GRAY.getTextColor();
    int signMidpoint = 4 * this.sign.getTextLineHeight() / 2;
    int yOffset = -51; // From original mixin

    int inverseHalfTitleWidth = -font.width(unlocked_typing$preformattedTextTitleString) / 2;
    graphics.text(font, unlocked_typing$preformattedTextTitle, inverseHalfTitleWidth + UNLOCKED_TYPING$OFFSET, yOffset, textColor, false);

    for (int i = 0; i < this.messages.length; ++i) {
      String message = this.messages[i];
      if (message != null) {
        if (font.isBidirectional()) {
          message = font.bidirectionalShaping(message);
        }

        int inverseHalfMessageWidth = -font.width(message) / 2;
        var charSequence = FormattedCharSequence.forward(message, Style.EMPTY);
        graphics.text(font, charSequence, inverseHalfMessageWidth + UNLOCKED_TYPING$OFFSET, i * this.sign.getTextLineHeight() - signMidpoint, textColor, false);
      }
    }
  }
}
