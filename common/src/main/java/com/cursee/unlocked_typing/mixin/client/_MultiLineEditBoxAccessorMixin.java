package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.MultiLineEditBoxAccessor;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.MultilineTextField;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MultiLineEditBox.class)
public class _MultiLineEditBoxAccessorMixin implements MultiLineEditBoxAccessor {

  @Shadow @Final private MultilineTextField textField;

  @Override
  public MultilineTextField unlocked_typing$getTextField() {
    return this.textField;
  }
}
