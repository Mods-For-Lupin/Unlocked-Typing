package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.BookEditScreenAccessor;
import com.cursee.unlocked_typing.api.client.MultiLineEditBoxAccessor;
import com.cursee.unlocked_typing.api.client.ScreenAccessor;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.MultilineTextField;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.CommonComponents;
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
public class BookEditScreenMixin {

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 140;

  @Shadow private MultiLineEditBox page;

  @Inject(at = @At("TAIL"), method = "render")
  private void unlocked_typing$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
    this.unlocked_typing$renderBookWritingDisplay(guiGraphics);
  }

  @Unique
  private void unlocked_typing$renderBookWritingDisplay(GuiGraphics guiGraphics) {

    // TODO see if this fails?
    Font font = Minecraft.getInstance().font;

    BookEditScreen screen = (BookEditScreen) (Object) this;
    ScreenAccessor screenAccessor = (ScreenAccessor) screen;

    BookEditScreenAccessor bookEditScreenAccessor = (BookEditScreenAccessor) screen;

    MultiLineEditBox page = bookEditScreenAccessor.unlocked_typing$getPage();

    int oldScreenWidth = (screenAccessor.unlocked_typing$getWidth() - 192) / 2;
    int oldFontWidth = screenAccessor.unlocked_typing$getFont().width(page.getValue());

    String helperRawText = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (oldScreenWidth + 192 - 44) + 27, 18, DyeColor.LIGHT_GRAY.getTextColor(), false);

    guiGraphics.drawString(screenAccessor.unlocked_typing$getFont(), page.getValue(), oldScreenWidth - oldFontWidth + 192 - 44, 18, 0, false);

    MultiLineEditBoxAccessor editBoxAccessor = (MultiLineEditBoxAccessor) page;

    String s = editBoxAccessor.unlocked_typing$getTextField().value();
    if (s.isEmpty() && !page.isFocused()) {
      guiGraphics.drawWordWrap(font, CommonComponents.EMPTY, page.getX() + 4, page.getY() + 4, page.getWidth() - 8, -857677600);
      return;
    }

    int i = editBoxAccessor.unlocked_typing$getTextField().cursor();
    // boolean flag = page.isFocused() && (Util.getMillis() - page.focusedTime) / 300L % 2L == 0L;
    boolean flag = true; // always focused?
    boolean flag1 = i < s.length();
    int j = 0;
    int k = 0;
    int l = page.getY() + 4;
    boolean flag2 = false;

    for(MultilineTextField.StringView multilinetextfield$stringview : editBoxAccessor.unlocked_typing$getTextField().iterateLines()) {
      boolean flag3 = unlocked_typing$withinContentAreaTopBottom(page, l, l + 9);
      int i1 = page.getX() + 4;
      if (flag && flag1 && i >= multilinetextfield$stringview.beginIndex() && i <= multilinetextfield$stringview.endIndex()) {
        if (flag3) {
          String s2 = s.substring(multilinetextfield$stringview.beginIndex(), i);
          guiGraphics.drawString(font, FormattedCharSequence.forward(s2, Style.EMPTY), i1 + UNLOCKED_TYPING$OFFSET, l, DyeColor.LIGHT_GRAY.getTextColor(), false);
          j = i1 + font.width(s2);

          guiGraphics.drawString(font, FormattedCharSequence.forward(s.substring(i, multilinetextfield$stringview.endIndex()), Style.EMPTY), j + UNLOCKED_TYPING$OFFSET, l, DyeColor.LIGHT_GRAY.getTextColor(), false);
        }
      } else {
        if (flag3) {
          String s1 = s.substring(multilinetextfield$stringview.beginIndex(), multilinetextfield$stringview.endIndex());
          guiGraphics.drawString(font, FormattedCharSequence.forward(s1, Style.EMPTY), i1 + UNLOCKED_TYPING$OFFSET, l, DyeColor.LIGHT_GRAY.getTextColor(), false);
          j = i1 + font.width(s1) - 1;
        }

        k = l;
      }

      l += 9;
    }
  }

  @Unique
  private static boolean unlocked_typing$withinContentAreaTopBottom(MultiLineEditBox page, int top, int bottom) {
    return (double)bottom - page.scrollAmount() >= (double)page.getY() && (double)top - page.scrollAmount() <= (double)(page.getY() + page.getHeight());
  }
}
