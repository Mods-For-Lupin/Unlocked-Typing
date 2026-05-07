package com.cursee.unlocked_typing.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.BookSignScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookSignScreen.class)
public abstract class BookSignScreenMixin {

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 140;

  @Inject(at = @At("TAIL"), method = "extractRenderState")
  private void unlocked_typing$extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
    this.unlocked_typing$renderBookSigningDisplay(graphics);
  }

  @Unique
  private void unlocked_typing$renderBookSigningDisplay(GuiGraphicsExtractor graphics) {
    ScreenAccessor screen = (ScreenAccessor) this;
    BookSignScreenAccessor signScreen = (BookSignScreenAccessor) this;
    Font font = screen.getFont();
    int i = (screen.getWidth() - 192) / 2;
    FormattedCharSequence formattedCharSequence = FormattedCharSequence.forward(signScreen.getTitleBox().getValue(), Style.EMPTY);

    String helperRawText = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
    graphics.text(font, FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET,
        50 - 11, DyeColor.LIGHT_GRAY.getTextColor(), false);

    graphics.text(font, formattedCharSequence, (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET, 50, DyeColor.LIGHT_GRAY.getTextColor(), false);

    Component renderedTextComponent = Component.literal(signScreen.getTitleBox().getValue());
    graphics.text(font, renderedTextComponent, (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET, 50 + 18, 0xFFFFFFFF, false);
  }
}
