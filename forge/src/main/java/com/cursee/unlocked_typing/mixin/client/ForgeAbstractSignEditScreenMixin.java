package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.impl.common.util.IAbstractSignEditScreenAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignEditScreen.class)
public abstract class ForgeAbstractSignEditScreenMixin implements IAbstractSignEditScreenAccessor {

  @Shadow
  @Final
  private SignBlockEntity sign;

  @Shadow
  @Final
  private String[] messages;

  @Shadow
  private SignText text;

  @Shadow
  protected abstract Vector3f getSignTextScale();

  @Override
  public SignBlockEntity unlocked_typing$getSignBlockEntity() {
    return this.sign;
  }

  @Override
  public String[] unlocked_typing$getMessages() {
    return this.messages;
  }

  @Override
  public SignText unlocked_typing$getText() {
    return this.text;
  }

  @Override
  public Vector3f unlocked_typing$getSignTextScale() {
    return this.getSignTextScale();
  }

  @Unique
  private static final int UNLOCKED_TYPING$OFFSET = 100;

  @Inject(at = @At("TAIL"), method = "renderSignText")
  private void unlocked_typing$renderSignText(GuiGraphics guiGraphics, CallbackInfo ci) {

    // Font font = ((Screen) (Object) this).getFont();
    Font font = Minecraft.getInstance().font;

    Vector3f vector3f = this.getSignTextScale();
    guiGraphics.pose().scale(vector3f.x(), vector3f.y(), 1.0f);
    int textColor = 0xFFDDDDDD; //this.text.hasGlowingText() ? this.text.getColor().getTextColor() : AbstractSignRenderer.getDarkColor(this.text);
    int yOffset = 4 * this.sign.getTextLineHeight() / 2;

    boolean drewHelperText = false;
    for (int messageIndex = 0; messageIndex < this.messages.length; ++messageIndex) {

      String message = this.text.getMessage(messageIndex, false).getString();
      int textX = -font.width(message) / 2;

      if (!drewHelperText) {

        guiGraphics.drawString(font, FormattedCharSequence.forward(Component.translatable("unlocked_typing.editableText").getString(), Style.EMPTY.withUnderlined(true)), textX + UNLOCKED_TYPING$OFFSET, (messageIndex * this.sign.getTextLineHeight() - yOffset) - 11, textColor, true);

        drewHelperText = true;
      }


      if (!message.isEmpty()) {
        if (font.isBidirectional()) {
          message = font.bidirectionalShaping(message);
        }
        guiGraphics.drawString(font, FormattedCharSequence.forward(message, Style.EMPTY), textX + UNLOCKED_TYPING$OFFSET, messageIndex * this.sign.getTextLineHeight() - yOffset, textColor, true);
      }
    }
  }
}
