package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.MultiLineEditBoxAccessor;
import com.cursee.unlocked_typing.api.client.StringViewAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
public abstract class BookEditScreenMixin {

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 140;

  @Inject(at = @At("TAIL"), method = "extractRenderState")
  private void unlocked_typing$extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
    this.unlocked_typing$renderBookWritingDisplay(graphics);
  }

  @Unique
  private void unlocked_typing$renderBookWritingDisplay(GuiGraphicsExtractor graphics) {
    ScreenAccessor screen = (ScreenAccessor) this;
    BookEditScreenAccessor bookScreen = (BookEditScreenAccessor) this;
    Font font = screen.getFont();
    MultiLineEditBox page = bookScreen.getPage();
    int oldScreenWidth = (screen.getWidth() - 192) / 2;
    int oldFontWidth = font.width(page.getValue());

    String helperRawText = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
    graphics.text(font, FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), (oldScreenWidth + 192 - 44) + 27, 18, DyeColor.LIGHT_GRAY.getTextColor(), false);

    graphics.text(font, page.getValue(), oldScreenWidth - oldFontWidth + 192 - 44, 18, 0, false);

    MultiLineEditBoxAccessor editBoxAccessor = (MultiLineEditBoxAccessor) page;
    MultilineTextField textField = editBoxAccessor.unlocked_typing$getTextField();
    String s = textField.value();

    if (s.isEmpty() && !page.isFocused()) {
      graphics.textWithWordWrap(font, CommonComponents.EMPTY, page.getX() + 4, page.getY() + 4, page.getWidth() - 8, -857677600);
      return;
    }

    int cursor = textField.cursor();
    boolean flag1 = cursor < s.length();
    int l = page.getY() + 4;

    for(Object line : textField.iterateLines()) {
      StringViewAccessor stringView = (StringViewAccessor) line;
      boolean flag3 = unlocked_typing$withinContentAreaTopBottom(page, l, l + 9);
      int i1 = page.getX() + 4;
      if (flag1 && cursor >= stringView.unlocked_typing$beginIndex() && cursor <= stringView.unlocked_typing$endIndex()) {
        if (flag3) {
          String s2 = s.substring(stringView.unlocked_typing$beginIndex(), cursor);
          graphics.text(font, FormattedCharSequence.forward(s2, Style.EMPTY), i1 + UNLOCKED_TYPING$OFFSET, l, DyeColor.LIGHT_GRAY.getTextColor(), false);
          int j = i1 + font.width(s2);
          graphics.text(font, FormattedCharSequence.forward(s.substring(cursor, stringView.unlocked_typing$endIndex()), Style.EMPTY), j + UNLOCKED_TYPING$OFFSET, l, DyeColor.LIGHT_GRAY.getTextColor(), false);
        }
      } else {
        if (flag3) {
          String s1 = s.substring(stringView.unlocked_typing$beginIndex(), stringView.unlocked_typing$endIndex());
          graphics.text(font, FormattedCharSequence.forward(s1, Style.EMPTY), i1 + UNLOCKED_TYPING$OFFSET, l, DyeColor.LIGHT_GRAY.getTextColor(), false);
        }
      }
      l += 9;
    }
  }

  @Unique
  private static boolean unlocked_typing$withinContentAreaTopBottom(MultiLineEditBox page, int top, int bottom) {
    return (double)bottom - page.scrollAmount() >= (double)page.getY() && (double)top - page.scrollAmount() <= (double)(page.getY() + page.getHeight());
  }
}
