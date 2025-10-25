package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.impl.common.util.IAbstractSignEditScreenAccessor;
import com.cursee.unlocked_typing.impl.common.util.IBookEditScreenAccessor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class FabricScreenMixin {

  @Shadow public int width;
  @Shadow protected Font font;
  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 140;

  @Unique
  private static final Map<String, Component> UNLOCKED_TYPING$EXAMPLE_MAP = new LinkedHashMap<>();

  @Inject(at = @At("TAIL"), method = "render")
  private void unlocked_typing$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {

    Screen screen = (Screen) (Object) this;
    Font font = this.font;
    int textColor = 0xFFDDDDDD;

    // we are operating on either a sign screen or a book screen

//    if (screen instanceof SignEditScreen signEditScreen) {
//
//      IAbstractSignEditScreenAccessor accessor = (IAbstractSignEditScreenAccessor) signEditScreen;
//
//      int yOffset = 4 * 10 / 2;
//
//      boolean drewHelperText = false;
//      for (int messageIndex = 0; messageIndex < accessor.unlocked_typing$getMessages().length; messageIndex++) {
//        String message = accessor.unlocked_typing$getText().getMessage(messageIndex, false).getString();
//        int textX = -font.width(message) / 2;
//
//        if (!drewHelperText) {
//          guiGraphics.drawString(font, FormattedCharSequence.forward(Component.translatable("unlocked_typing.editableText").getString(), Style.EMPTY.withUnderlined(true)), textX + UNLOCKED_TYPING$OFFSET, (messageIndex * 10 - yOffset) - 11, textColor, true);
//          drewHelperText = true;
//        }
//
//        if (!message.isEmpty()) {
//          if (font.isBidirectional()) {
//            message = font.bidirectionalShaping(message);
//          }
//
//          System.out.println("drawing at x " + (textX + UNLOCKED_TYPING$OFFSET) + " y " + (messageIndex * 10 - yOffset));
//          guiGraphics.drawString(font, FormattedCharSequence.forward(message, Style.EMPTY), textX + UNLOCKED_TYPING$OFFSET, (messageIndex * 10 - yOffset) + 40, textColor, true); // todo get rid of the magic 40
//        }
//      }
//
//    } else
    if (screen instanceof BookEditScreen bookEditScreen) {
      IBookEditScreenAccessor accessor = (IBookEditScreenAccessor) bookEditScreen;

      if (!accessor.unlocked_typing$isSigning()) {
        String messages = accessor.unlocked_typing$currentPageText();

//      if (messages.isEmpty()) {
//        return;
//      }

        int i = (this.width - 192) / 2;
        int n = font.width(accessor.unlocked_typing$currentPageMessage());

        int originalEditBoxX = accessor.unlocked_typing$getDisplayCache().lines[0].x;

        int x = (originalEditBoxX + 4) + UNLOCKED_TYPING$OFFSET;
        int y = 21; //(9 * textField.getLineCount()) - 22;

        String helperRawText = Component.translatable("unlocked_typing.editableText").getString();
        if (!messages.isEmpty()) {

          guiGraphics.drawString(font, FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), x, y, 0xFFFFFFFF);

          String[] splitMessages = messages.split(Character.toString((char) 10));
          List<String> finalMessages = new ArrayList<>();
          for (String line : splitMessages) {
            while (line.length() > 20) {
              finalMessages.add(line.substring(0, 20));
              line = line.substring(20);
            }
            if (!line.isEmpty()) {
              finalMessages.add(line);
              finalMessages.add("");
            }
          }
          splitMessages = finalMessages.toArray(new String[0]);

          for (String message : splitMessages) {

            FormattedCharSequence unstyledCharSequence = FormattedCharSequence.forward(message, Style.EMPTY);

            guiGraphics.drawString(font, unstyledCharSequence, x, y + 11, 0xFFDDDDDD, false);
//        guiGraphics.drawString(font, unstyledCharSequence, x, y - 27, 0xFFDDDDDD, false);

            y += 9;
          }
        }
      } else {
        // is signing

        String titleText = accessor.unlocked_typing$getTitle();
        FormattedCharSequence formattedCharSequence = FormattedCharSequence.forward(titleText, Style.EMPTY);

        int y = 21;

        int i = (this.width - 192) / 2;

        int l = this.font.width(formattedCharSequence);

        int origCalcX = i + 36 + (114 - l) / 2;
        int origCalcY = 50;

        origCalcX += UNLOCKED_TYPING$OFFSET;

        guiGraphics.drawString(font, Component.literal(titleText), origCalcX, origCalcY + 36, 0xFFFFFFFF);

        String[] splitMessages = titleText.split(Character.toString((char) 10));
        List<String> finalMessages = new ArrayList<>();
        for (String line : splitMessages) {
          while (line.length() > 20) {
            finalMessages.add(line.substring(0, 20));
            line = line.substring(20);
          }
          if (!line.isEmpty()) {
            finalMessages.add(line);
            finalMessages.add("");
          }
        }
        splitMessages = finalMessages.toArray(new String[0]);

        for (String message : splitMessages) {

          FormattedCharSequence unstyledCharSequence = FormattedCharSequence.forward(message, Style.EMPTY);

          guiGraphics.drawString(font, unstyledCharSequence, origCalcX, origCalcY + 11, 0xFFDDDDDD, false);
//        guiGraphics.drawString(font, unstyledCharSequence, x, y - 27, 0xFFDDDDDD, false);

          y += 9;
        }
      }
    }

    if (!(screen instanceof BookEditScreen || screen instanceof SignEditScreen)) {
      return;
    }

    // draw formatting hint example text
    this.unlocked_typing$renderExampleText(guiGraphics, Minecraft.getInstance().font);
  }

  @Unique
  private void unlocked_typing$renderExampleText(GuiGraphics guiGraphics, Font font) {
    if (UNLOCKED_TYPING$EXAMPLE_MAP.isEmpty()) {
      unlocked_typing$fill();
    } else {

      int startY = 20;
      int startX = 20;

      for (String s : UNLOCKED_TYPING$EXAMPLE_MAP.keySet()) {

        Component mappedComponent = UNLOCKED_TYPING$EXAMPLE_MAP.get(s);

        guiGraphics.drawString(font, FormattedCharSequence.forward(s, Style.EMPTY), startX, startY, 0xFFFFFFFF);
        guiGraphics.drawString(font, mappedComponent, startX + 15, startY, 0xFFFFFFFF);

        startY += 9;
      }
    }
  }

  @Unique
  private void unlocked_typing$fill() {

    FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§ ", Component.literal("Formatting Codes").withStyle(Style.EMPTY.withBold(true)));

    FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + 'k', Component.literal("§" + 'k' + "Obfuscated Text"));
    FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + 'l', Component.literal("§" + 'l' + "Bold Text"));
    FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + 'm', Component.literal("§" + 'm' + "Strikethrough Text"));
    FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + 'n', Component.literal("§" + 'n' + "Underlined Text"));
    FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + 'o', Component.literal("§" + 'o' + "Italic Text"));
    FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + 'r', Component.literal("§" + 'r' + "Reset Text"));

    for (int i = 0; i <= 9; i++) {
      FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + i, Component.literal("§" + i + "Colored Text"));
    }

    for (char character = 'a'; character <= 'f'; character++) {
      FabricScreenMixin.UNLOCKED_TYPING$EXAMPLE_MAP.put("§" + character, Component.literal("§" + character + "Colored Text"));
    }
  }
}
