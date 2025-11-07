package com.cursee.unlocked_typing.mixin.client;

import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BookEditScreen.class)
public class InProdNeoForgeBookEditScreenMixin {

  @SuppressWarnings("all")
  @ModifyConstant(method = "m_98169_", constant = @Constant(intValue = 16))
  private static int unlocked_typing$method_27593$titleLengthVerification(int constant) {
    return 30;
  }
}
