package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.ConfiguredValues.ClientModConfig;
import com.cursee.unlocked_typing.impl.client.FormattingExamplesHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ForgeScreenMixin {

  @Shadow
  protected Font font;

//  @Inject(at = @At("TAIL"), method = "render")
//  private void unlocked_typing$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {}

  @Inject(at = @At("TAIL"), method = "render")
  private void unlocked_typing$render$renderHelperText(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {

    Screen self = (Screen) (Object) this;

    if (ClientModConfig.displayFormattingExamples && (self instanceof BookEditScreen || self instanceof AbstractSignEditScreen || self instanceof AnvilScreen)) {
      FormattingExamplesHelper.renderFormattingExamples(guiGraphics, this.font);
    }
  }
}
