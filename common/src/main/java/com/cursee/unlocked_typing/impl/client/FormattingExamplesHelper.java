package com.cursee.unlocked_typing.impl.client;

import com.cursee.unlocked_typing.mixin.client.ScreenMixin;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

public class FormattingExamplesHelper {

  /// Used in {@link ScreenMixin}` to display examples of section sign (§) usage
  public static final Map<String, Component> FORMATTING_EXAMPLES = new LinkedHashMap<>();

  // fill the map on class initialization / first access of this class
  static {
    FORMATTING_EXAMPLES.put("§ ", Component.translatable("unlocked_typing.formatCodesTitle").withStyle(Style.EMPTY.withBold(true)));

    FORMATTING_EXAMPLES.put("§" + 'k', Component.literal("§" + 'k' + "Obfuscated Text"));
    FORMATTING_EXAMPLES.put("§" + 'l', Component.literal("§" + 'l' + "Bold Text"));
    FORMATTING_EXAMPLES.put("§" + 'm', Component.literal("§" + 'm' + "Strikethrough Text"));
    FORMATTING_EXAMPLES.put("§" + 'n', Component.literal("§" + 'n' + "Underlined Text"));
    FORMATTING_EXAMPLES.put("§" + 'o', Component.literal("§" + 'o' + "Italic Text"));
    FORMATTING_EXAMPLES.put("§" + 'r', Component.literal("§" + 'r' + "Reset Text"));

    for (int i = 0; i <= 9; i++) {
      FORMATTING_EXAMPLES.put("§" + i, Component.literal("§" + i + "Colored Text"));
    }

    for (char character = 'a'; character <= 'f'; character++) {
      FORMATTING_EXAMPLES.put("§" + character, Component.literal("§" + character + "Colored Text"));
    }
  }

  public static void renderFormattingExamples(GuiGraphics guiGraphics, Font font) {
    int startY = 20;
    int startX = 20;

    for (String s : FORMATTING_EXAMPLES.keySet()) {

      Component mappedComponent = FORMATTING_EXAMPLES.get(s);

      guiGraphics.drawString(font, FormattedCharSequence.forward(s, Style.EMPTY), startX, startY, 0xFFFFFFFF);
      guiGraphics.drawString(font, mappedComponent, startX + 15, startY, 0xFFFFFFFF);

      startY += 9;
    }
  }
}
