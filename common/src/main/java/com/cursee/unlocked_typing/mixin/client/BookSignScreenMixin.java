package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.ScreenAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
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
public class BookSignScreenMixin {

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 140;

  @Shadow
  private String titleValue;

  @Shadow private EditBox titleBox;

  @Inject(at = @At("TAIL"), method = "render")
  private void unlocked_typing$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
    this.unlocked_typing$renderBookSigningDisplay(guiGraphics);
  }

  @Unique
  private void unlocked_typing$renderBookSigningDisplay(GuiGraphics guiGraphics) {

    BookSignScreen screen = (BookSignScreen) (Object) this;
    ScreenAccessor screenAccessor = (ScreenAccessor) screen;

    int i = (screenAccessor.unlocked_typing$getWidth() - 192) / 2;
    FormattedCharSequence formattedCharSequence = FormattedCharSequence.forward(this.titleBox.getValue(), Style.EMPTY);
    int l = screenAccessor.unlocked_typing$getFont().width(formattedCharSequence);

    String helperRawText = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
    // text seems to slide over during typing
    // guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (i + 36 + (114 - l) / 2) + UNLOCKED_TYPING$OFFSET, 50 - 11, DyeColor.LIGHT_GRAY.getTextColor(), false);
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET,
        50 - 11, DyeColor.LIGHT_GRAY.getTextColor(), false);

    // correctly renders over original text, slides over during typing
    // guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), formattedCharSequence, i + 36 + (114 - l) / 2, 50, DyeColor.RED.getTextColor(), false);

    // correctly renders offset to the right from original text
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), formattedCharSequence, (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET, 50, DyeColor.LIGHT_GRAY.getTextColor(), false);

    Component renderedTextComponent = Component.literal(this.titleBox.getValue());
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), renderedTextComponent, (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET, 50 + 18, 0xFFFFFFFF, false);
  }
}
