package com.cursee.unlocked_typing.api.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public interface ScreenAccessor {

  int unlocked_typing$getWidth();

  Font unlocked_typing$getFont();

  <T extends GuiEventListener & Renderable & NarratableEntry> T unlocked_typing$addRenderableWidget(T widget);
}
