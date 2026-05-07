package com.cursee.unlocked_typing.mixin.client;
/*? if fabric { */
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public interface ScreenAccessor {
  @Accessor
  Font getFont();

  @Accessor
  int getWidth();

  @Accessor
  int getHeight();
}
/*? } */
