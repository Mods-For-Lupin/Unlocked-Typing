package com.cursee.unlocked_typing.mixin;

import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BookEditScreen.class)
public class FabricRuntimeBookEditScreenMixin {

  @ModifyConstant(method = "method_27593", constant = @Constant(intValue = 16))
  private static int unlocked_typing$modifySynthetic(int constant) {
    return 30;
  }
}
