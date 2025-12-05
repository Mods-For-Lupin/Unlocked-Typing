package com.cursee.unlocked_typing;

import java.util.Locale;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.CycleButton.Builder;
import net.minecraft.network.chat.Component;

public class ConfiguredValues {

  public static class CommonModConfig {

  }

  public static class ClientModConfig {

    public static boolean displayFormattingExamples = true;

    public static CycleButton<Boolean> configButton = new Builder<Boolean>((bool) -> Component.literal(String.valueOf(bool).toUpperCase(Locale.ROOT))).withInitialValue(displayFormattingExamples)
        .withValues(displayFormattingExamples, false).create(0, 0, 80, 16, Component.literal("Display"), (cycleButton, aBoolean) -> {
          displayFormattingExamples = aBoolean;
        });
  }

  public static class ServerModConfig {

  }
}
