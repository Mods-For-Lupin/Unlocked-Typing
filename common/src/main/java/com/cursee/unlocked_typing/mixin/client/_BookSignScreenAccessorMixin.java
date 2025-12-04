package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.BookSignScreenAccessor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.BookSignScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BookSignScreen.class)
public class _BookSignScreenAccessorMixin implements BookSignScreenAccessor {

  @Shadow
  private EditBox titleBox;

  @ModifyConstant(method = "init", constant = @Constant(intValue = 15))
  private int unlocked_typing$init(int original) {
    return 30;
  }

  @Unique
  public EditBox unlocked_typing$getTitleBox() {
    return this.titleBox;
  }
}
