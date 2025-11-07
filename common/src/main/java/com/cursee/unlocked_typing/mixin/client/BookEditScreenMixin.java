package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.BookEditScreenAccessor;
import com.cursee.unlocked_typing.api.client.DisplayCacheAccessor;
import com.cursee.unlocked_typing.api.client.LineInfoAccessor;
import com.cursee.unlocked_typing.api.client.ScreenAccessor;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.DisplayCache;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.LineInfo;
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

@Mixin(BookEditScreen.class)
public abstract class BookEditScreenMixin {

  @Shadow
  @Nullable
  private DisplayCache displayCache;

  @Shadow
  private boolean isSigning;

  @Shadow
  private Component pageMsg;
  @Shadow
  private int frameTick;
  @Shadow
  private String title;

  @Shadow
  protected abstract DisplayCache getDisplayCache();

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 140;

  @Inject(at = @At("TAIL"), method = "render")
  private void unlocked_typing$render$renderPreformattedText(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {

    if (this.isSigning) {
      this.unlocked_typing$renderBookSigningDisplay(guiGraphics);
    } else {
      this.unlocked_typing$renderBookWritingDisplay(guiGraphics);
    }
  }

  @Unique
  private void unlocked_typing$renderBookSigningDisplay(GuiGraphics guiGraphics) {

    BookEditScreen screen = (BookEditScreen) (Object) this;
    ScreenAccessor screenAccessor = (ScreenAccessor) screen;

    int i = (screenAccessor.unlocked_typing$getWidth() - 192) / 2;
    FormattedCharSequence formattedCharSequence = FormattedCharSequence.forward(this.title, Style.EMPTY);
    int l = screenAccessor.unlocked_typing$getFont().width(formattedCharSequence);

    String helperRawText = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
    // text seems to slide over during typing
    // guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (i + 36 + (114 - l) / 2) + UNLOCKED_TYPING$OFFSET, 50 - 11, DyeColor.LIGHT_GRAY.getTextColor(), false);
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET, 50 - 11, DyeColor.LIGHT_GRAY.getTextColor(), false);

    // correctly renders over original text, slides over during typing
    // guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), formattedCharSequence, i + 36 + (114 - l) / 2, 50, DyeColor.RED.getTextColor(), false);

    // correctly renders offset to the right from original text
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), formattedCharSequence, (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET, 50, DyeColor.LIGHT_GRAY.getTextColor(), false);

    Component renderedTextComponent = Component.literal(this.title);
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), renderedTextComponent, (i + 36 + (114 - 90) / 2) + UNLOCKED_TYPING$OFFSET, 50 + 18, 0xFFFFFFFF, false);
  }

  @Unique
  private void unlocked_typing$renderBookWritingDisplay(GuiGraphics guiGraphics) {

    BookEditScreen screen = (BookEditScreen) (Object) this;
    ScreenAccessor screenAccessor = (ScreenAccessor) screen;

    BookEditScreenAccessor bookEditScreenAccessor = (BookEditScreenAccessor) screen;
    DisplayCacheAccessor displayCacheAccessor = (DisplayCacheAccessor) bookEditScreenAccessor.unlocked_typing$getDisplayCache();

    int i = (screenAccessor.unlocked_typing$getWidth() - 192) / 2;
    int n = screenAccessor.unlocked_typing$getFont().width(this.pageMsg);

    String helperRawText = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (i + 192 - 44) + 27, 18, DyeColor.LIGHT_GRAY.getTextColor(), false);

    // TODO test removing the following drawString call
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), this.pageMsg, i - n + 192 - 44, 18, 0, false);

    for (LineInfo lineInfo : displayCacheAccessor.unlocked_typing$getLines()) {
      LineInfoAccessor lineInfoAccessor = (LineInfoAccessor) lineInfo;

      // correctly renders over original text
      // guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), lineInfoAccessor.unlocked_typing$getAsComponent(), lineInfoAccessor.unlocked_typing$getX(),
      //     lineInfoAccessor.unlocked_typing$getY(), DyeColor.RED.getTextColor(), false);

      // correctly renders offset to the right from original text
      Component originalComponent = lineInfoAccessor.unlocked_typing$getAsComponent();
      FormattedCharSequence formattedCharSequence = FormattedCharSequence.forward(originalComponent.getString(), Style.EMPTY);
      guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), formattedCharSequence, lineInfoAccessor.unlocked_typing$getX() + UNLOCKED_TYPING$OFFSET,
          lineInfoAccessor.unlocked_typing$getY(), DyeColor.LIGHT_GRAY.getTextColor(), false);
    }
  }
}
