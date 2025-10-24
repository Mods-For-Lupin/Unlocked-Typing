package com.cursee.unlocked_typing.mixin;

import com.cursee.unlocked_typing.impl.common.util.IMultiLineEditBoxFieldAccessor;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.components.MultilineTextField;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MultiLineEditBox.class)
public class MultiLineEditBoxMixin implements IMultiLineEditBoxFieldAccessor {

  @Shadow @Final private MultilineTextField textField;

  @Override
  public MultilineTextField unlocked_typing$getField() {
    return this.textField;
  }
}
