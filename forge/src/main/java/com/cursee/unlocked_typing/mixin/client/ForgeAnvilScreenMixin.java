package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.Constants;
import com.cursee.unlocked_typing.api.client.ScreenAccessor;
import com.cursee.unlocked_typing.impl.client.ClipboardHelper;
import com.cursee.unlocked_typing.platform.Services;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public class ForgeAnvilScreenMixin {

  @Inject(at = @At("TAIL"), method = "subInit")
  private void unlocked_typing$init(CallbackInfo ci) {

    Screen self = (Screen) (Object) this;
    ScreenAccessor screenAccessor = (ScreenAccessor) self;

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      System.out.println(Constants.PREFIX + "An instance of Screen was initialized... " + String.valueOf(self));
    }

    if (self instanceof AnvilScreen) {
      // screenAccessor.unlocked_typing$addRenderableWidget(ClientModConfig.configButton);
      screenAccessor.unlocked_typing$addRenderableWidget(new PlainTextButton(80, 0, 80, 16, Component.literal("COPY SYMBOL").withStyle(Style.EMPTY), button -> ClipboardHelper.copyToClipboard("§"), Minecraft.getInstance().font) {

        @Override
//        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
//          super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
//        }
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
          Minecraft minecraft = Minecraft.getInstance();
          guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
          RenderSystem.enableBlend();
          RenderSystem.enableDepthTest();
          guiGraphics.blitNineSliced(WIDGETS_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
          guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
          int i = this.active ? 16777215 : 10526880;
          this.renderString(guiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
        }

        private int getTextureY() {
          int i = 1;
          if (!this.active) {
            i = 0;
          } else if (this.isHoveredOrFocused()) {
            i = 2;
          }

          return 46 + i * 20;
        }
      });
    }
  }
}
