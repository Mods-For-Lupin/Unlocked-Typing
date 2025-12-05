package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.ConfiguredValues.ClientModConfig;
import com.cursee.unlocked_typing.impl.client.FormattingExamplesHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {

  @Shadow
  protected Font font;

//  @Inject(at = @At("TAIL"), method = "render")
//  private void unlocked_typing$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {}

  @Inject(at = @At("TAIL"), method = "render")
  private void unlocked_typing$render$renderHelperText(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {

    Screen self = (Screen) (Object) this;

    if (ClientModConfig.displayFormattingExamples && (self instanceof BookEditScreen || self instanceof AbstractSignEditScreen)) {
      FormattingExamplesHelper.renderFormattingExamples(guiGraphics, this.font);
    }
  }
}
