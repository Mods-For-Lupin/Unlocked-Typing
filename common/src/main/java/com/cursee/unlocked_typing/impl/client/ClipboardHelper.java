package com.cursee.unlocked_typing.impl.client;

import net.minecraft.client.Minecraft;

public class ClipboardHelper {

  // this.minecraft.keyboardHandler.setClipboard(clickevent.getValue());

  public static void copyToClipboard(String value) {
    Minecraft.getInstance().keyboardHandler.setClipboard(value);
  }
}
