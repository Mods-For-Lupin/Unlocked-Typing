package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.UnlockedTyping;
import com.cursee.unlocked_typing.impl.client.FormattingExamplesHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookSignScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {

  @Inject(at = @At("TAIL"), method = "extractRenderState")
  private void unlocked_typing$extractRenderState$renderHelperText(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
    Screen self = (Screen) (Object) this;
    Font font = ((ScreenAccessor) this).getFont();

    if (UnlockedTyping.CONFIG.displayFormattingExamples.get() && (self instanceof BookEditScreen || self instanceof BookSignScreen || self instanceof AbstractSignEditScreen)) {
      FormattingExamplesHelper.renderFormattingExamples(graphics, font);
    }
  }
}
