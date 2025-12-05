package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.ScreenAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public abstract class _ScreenAccessorMixin implements ScreenAccessor {

  @Shadow
  public int width;
  @Shadow
  protected Font font;

  @Shadow protected abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget);

  @Override
  public int unlocked_typing$getWidth() {

    return width;
  }

  @Override
  public Font unlocked_typing$getFont() {

    return font;
  }

  public <T extends GuiEventListener & Renderable & NarratableEntry> T unlocked_typing$addRenderableWidget(T widget) {
    return this.addRenderableWidget(widget);
  }
}
