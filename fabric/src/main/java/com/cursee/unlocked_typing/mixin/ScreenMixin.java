package com.cursee.unlocked_typing.mixin;

import com.cursee.unlocked_typing.impl.common.util.IBookPageAccessor;
import com.cursee.unlocked_typing.impl.common.util.IBookSigningAccessor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 140;

  @Unique
  private static final Map<String, Component> UNLOCKED_TYPING$EXAMPLE_MAP = new LinkedHashMap<>();

  /// After Renderable widget instances have been rendered
  /// lots of "magic numbers" that I'm too lazy to get rid of. sorry future me...
  @Inject(at = @At("TAIL"), method = "render")
  private void unlocked_typing$renderRenderablesInjection(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {

    Screen screen = (Screen) (Object) this;
    Font font = Minecraft.getInstance().font;

    // if (screen instanceof SignEditScreen || screen instanceof BookEditScreen || screen instanceof BookSignScreen) {
    if (screen instanceof SignEditScreen || screen instanceof BookEditScreen) {
      if (UNLOCKED_TYPING$EXAMPLE_MAP.isEmpty()) {
        unlocked_typing$fill(UNLOCKED_TYPING$EXAMPLE_MAP);
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

    // if (!(screen instanceof BookEditScreen || screen instanceof BookSignScreen)) {
    if (!(screen instanceof BookEditScreen)) {
      return;
    }

    String messages;
    int originalEditBoxX;

    if (screen instanceof BookEditScreen bookEditScreen) {

      IBookPageAccessor pageGetter = (IBookPageAccessor) bookEditScreen;
      // MultiLineEditBox editBox = pageGetter.unlocked_typing$getPage();

      messages = pageGetter.unlocked_typing$getPage();
      originalEditBoxX = 25; // editBox.getX();
    } else {

      messages = "";
      originalEditBoxX = 15;

//      if (!(screen instanceof BookSignScreen bookSignScreen)) {
//        return;
//      }
//
//      messages = ((IBookSigningAccessor) bookSignScreen).unlocked_typing$getTitleBox().getValue();
//      originalEditBoxX = ((IBookSigningAccessor) bookSignScreen).unlocked_typing$getTitleBox().getX();
    }

    int x = (originalEditBoxX + 4) + UNLOCKED_TYPING$OFFSET;
    int y = 21; //(9 * textField.getLineCount()) - 22;

    String helperRawText = Component.translatable("unlocked_typing.editableText").getString();

    // underlining draws over next text line
    // guiGraphics.drawString(font, FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), x, y - 36, 0xFFFFFFFF);

    // guiGraphics.drawString(font, FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), x, y - 38, 0xFFFFFFFF);

    guiGraphics.drawString(font, FormattedCharSequence.forward(helperRawText, Style.EMPTY.withUnderlined(true)), x, y, 0xFFFFFFFF);

//    if (screen instanceof BookSignScreen) {
//      guiGraphics.drawString(font, Component.literal(messages), x, y + 36, 0xFFFFFFFF);
//    }

    if (!messages.isEmpty()) {

//      if (!unlocked_typing$adjustedWidth) {
//        editBox.setWidth(editBox.getWidth() * 3);
//        unlocked_typing$adjustedWidth = true;
//      }

      // split string on LINE FEED ascii character
//      String[] splitMessages = messages.split(Character.toString((char) 10));

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
  }

  @Unique
  private void unlocked_typing$fill(Map<String, Component> map) {

    map.put("§ ", Component.literal("Formatting Codes").withStyle(Style.EMPTY.withBold(true)));

    map.put("§" + String.valueOf('k'), Component.literal("§" + String.valueOf('k') + "Obfuscated Text"));
    map.put("§" + String.valueOf('l'), Component.literal("§" + String.valueOf('l') + "Bold Text"));
    map.put("§" + String.valueOf('m'), Component.literal("§" + String.valueOf('m') + "Strikethrough Text"));
    map.put("§" + String.valueOf('n'), Component.literal("§" + String.valueOf('n') + "Underlined Text"));
    map.put("§" + String.valueOf('o'), Component.literal("§" + String.valueOf('o') + "Italic Text"));
    map.put("§" + String.valueOf('r'), Component.literal("§" + String.valueOf('r') + "Reset Text"));

    for (int i = 0; i <= 9; i++) {
      map.put("§" + String.valueOf(i), Component.literal("§" + String.valueOf(i) + "Colored Text"));
    }

    for (char character = 'a'; character <= 'f'; character++) {
      map.put("§" + String.valueOf(character), Component.literal("§" + String.valueOf(character) + "Colored Text"));
    }
  }
}
