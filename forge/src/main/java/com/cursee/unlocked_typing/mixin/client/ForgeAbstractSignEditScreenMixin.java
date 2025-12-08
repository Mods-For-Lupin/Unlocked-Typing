package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.ConfiguredValues.ClientModConfig;
import com.cursee.unlocked_typing.Constants;
import com.cursee.unlocked_typing.api.client.ScreenAccessor;
import com.cursee.unlocked_typing.impl.client.ClipboardHelper;
import com.cursee.unlocked_typing.platform.Services;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignEditScreen.class)
public abstract class ForgeAbstractSignEditScreenMixin {

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 100;
  /// This value will always be the same after initializing
  @Unique
  private static FormattedCharSequence unlocked_typing$preformattedTextTitle;
  /// This value will always be the same after initializing
  @Unique
  private static String unlocked_typing$preformattedTextTitleString;
  @Shadow
  @Final
  private SignBlockEntity sign;
  @Shadow
  @Final
  private String[] messages;
  /// Mimics {@link Screen}'s `font` field
  @Unique
  private Font unlocked_typing$font;

  @Shadow
  protected abstract Vector3f getSignTextScale();

  @Inject(at = @At("TAIL"), method = "renderSignText")
  private void unlocked_typing$renderSignText$renderPreformattedText(GuiGraphics guiGraphics, CallbackInfo ci) {

    if (!ClientModConfig.displayFormattingExamples) {
      return;
    }

    if (this.unlocked_typing$font == null) {
      this.unlocked_typing$font = Minecraft.getInstance().font;
    }

    if (unlocked_typing$preformattedTextTitle == null && unlocked_typing$preformattedTextTitleString == null) {
      unlocked_typing$preformattedTextTitleString = Component.translatable("unlocked_typing.preformattedTextTitle").getString();
      unlocked_typing$preformattedTextTitle = FormattedCharSequence.forward(unlocked_typing$preformattedTextTitleString, Style.EMPTY.withUnderlined(true));
    }

    // mimicking setup in original injection point
    guiGraphics.pose().translate(0f, 0f, 4f); // translates initial position for drawing text
    Vector3f scale = this.getSignTextScale(); // text scaling factor
    guiGraphics.pose().scale(scale.x, scale.y, scale.z); // scales the text
    int textColor = DyeColor.LIGHT_GRAY.getTextColor(); // color to draw our text as
    int yOffset = 4 * this.sign.getTextLineHeight() / 2;

    // draw title above preformatted text
    int inverseHalfTitleWidth = -this.unlocked_typing$font.width(unlocked_typing$preformattedTextTitleString) / 2;

    // draws below the text
    // guiGraphics.drawString(this.unlocked_typing$font, unlocked_typing$preformattedTextTitle, inverseHalfTitleWidth, yOffset, textColor, false);

    // draw above text correctly, before offset to the right
    // guiGraphics.drawString(this.unlocked_typing$font, unlocked_typing$preformattedTextTitle, inverseHalfTitleWidth, yOffset - 51, textColor, false);

    // draw with offset
    guiGraphics.drawString(this.unlocked_typing$font, unlocked_typing$preformattedTextTitle, inverseHalfTitleWidth + UNLOCKED_TYPING$OFFSET, yOffset - 51, textColor, false);

    // mimicking drawing of text
    for (int messageIndex = 0; messageIndex < this.messages.length; ++messageIndex) {
      String message = this.messages[messageIndex];
      if (message != null) {
        if (this.unlocked_typing$font.isBidirectional()) {
          message = this.unlocked_typing$font.bidirectionalShaping(message);
        }

        int inverseHalfMessageWidth = -this.unlocked_typing$font.width(message) / 2;
        // guiGraphics.drawString(this.unlocked_typing$font, message, inverseHalfMessageWidth, messageIndex * this.sign.getTextLineHeight() - yOffset, textColor, false);

        // replace "message" parameter with unformatted char sequence

        var charSequence = FormattedCharSequence.forward(message, Style.EMPTY);

        // draws over original text correctly, before offset to the right
        // guiGraphics.drawString(this.unlocked_typing$font, charSequence, inverseHalfMessageWidth, messageIndex * this.sign.getTextLineHeight() - yOffset, textColor, false);

        // daw with offset
        guiGraphics.drawString(this.unlocked_typing$font, charSequence, inverseHalfMessageWidth + UNLOCKED_TYPING$OFFSET, messageIndex * this.sign.getTextLineHeight() - yOffset, textColor, false);
      }
    }
  }

  @Inject(at = @At("TAIL"), method = "init()V")
  private void unlocked_typing$init(CallbackInfo ci) {

    Screen self = (Screen) (Object) this;
    ScreenAccessor screenAccessor = (ScreenAccessor) self;

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      System.out.println(Constants.PREFIX + "An instance of Screen was initialized... " + String.valueOf(self));
    }

    if (self instanceof BookEditScreen || self instanceof AbstractSignEditScreen) {
      screenAccessor.unlocked_typing$addRenderableWidget(ClientModConfig.configButton);
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
