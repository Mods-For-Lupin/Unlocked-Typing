package com.cursee.unlocked_typing.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.SignBlockEntity;
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
  /// This value will always be the same after initializing
  @Unique
  private static FormattedCharSequence unlocked_typing$preformattedTextTitle;
  /// This value will always be the same after initializing
  @Unique
  private static String unlocked_typing$preformattedTextTitleString;
  @Shadow
  @Final
  private SignBlockEntity sign;
  @Shadow
  @Final
  private String[] messages;
  /// Mimics {@link Screen}'s `font` field
  @Unique
  private Font unlocked_typing$font;

  @Shadow
  protected abstract Vector3f getSignTextScale();

  @Inject(at = @At("TAIL"), method = "renderSignText")
  private void unlocked_typing$renderSignText$renderPreformattedText(GuiGraphics guiGraphics, CallbackInfo ci) {

    if (this.unlocked_typing$font == null) {
      this.unlocked_typing$font = Minecraft.getInstance().font;
    }

    if (unlocked_typing$preformattedTextTitle == null && unlocked_typing$preformattedTextTitleString == null) {
      unlocked_typing$preformattedTextTitleString = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
      unlocked_typing$preformattedTextTitle = FormattedCharSequence.forward(unlocked_typing$preformattedTextTitleString, Style.EMPTY.withUnderlined(true));
    }

    // mimicking setup in original injection point
    guiGraphics.pose().translate(0f, 0f, 4f); // translates initial position for drawing text
    Vector3f scale = this.getSignTextScale(); // text scaling factor
    guiGraphics.pose().scale(scale.x, scale.y, scale.z); // scales the text
    int textColor = DyeColor.LIGHT_GRAY.getTextColor(); // color to draw our text as
    int yOffset = 4 * this.sign.getTextLineHeight() / 2;

    // draw title above preformatted text
    int inverseHalfTitleWidth = -this.unlocked_typing$font.width(unlocked_typing$preformattedTextTitleString) / 2;

    // draws below the text
    // guiGraphics.drawString(this.unlocked_typing$font, unlocked_typing$preformattedTextTitle, inverseHalfTitleWidth, yOffset, textColor, false);

    // draw above text correctly, before offset to the right
    // guiGraphics.drawString(this.unlocked_typing$font, unlocked_typing$preformattedTextTitle, inverseHalfTitleWidth, yOffset - 51, textColor, false);

    // draw with offset
    guiGraphics.drawString(this.unlocked_typing$font, unlocked_typing$preformattedTextTitle, inverseHalfTitleWidth + UNLOCKED_TYPING$OFFSET, yOffset - 51, textColor, false);

    // mimicking drawing of text
    for (int messageIndex = 0; messageIndex < this.messages.length; ++messageIndex) {
      String message = this.messages[messageIndex];
      if (message != null) {
        if (this.unlocked_typing$font.isBidirectional()) {
          message = this.unlocked_typing$font.bidirectionalShaping(message);
        }

        int inverseHalfMessageWidth = -this.unlocked_typing$font.width(message) / 2;
        // guiGraphics.drawString(this.unlocked_typing$font, message, inverseHalfMessageWidth, messageIndex * this.sign.getTextLineHeight() - yOffset, textColor, false);

        // replace "message" parameter with unformatted char sequence

        var charSequence = FormattedCharSequence.forward(message, Style.EMPTY);

        // draws over original text correctly, before offset to the right
        // guiGraphics.drawString(this.unlocked_typing$font, charSequence, inverseHalfMessageWidth, messageIndex * this.sign.getTextLineHeight() - yOffset, textColor, false);

        // daw with offset
        guiGraphics.drawString(this.unlocked_typing$font, charSequence, inverseHalfMessageWidth + UNLOCKED_TYPING$OFFSET, messageIndex * this.sign.getTextLineHeight() - yOffset, textColor, false);
      }
    }
  }
}
