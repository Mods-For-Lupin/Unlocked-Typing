package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.ScreenAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public abstract class _ScreenAccessorMixin implements ScreenAccessor {

  @Shadow
  public int width;
  @Shadow
  protected Font font;

  @Override
  public int unlocked_typing$getWidth() {

    return width;
  }

  @Override
  public Font unlocked_typing$getFont() {

    return font;
  }
}
