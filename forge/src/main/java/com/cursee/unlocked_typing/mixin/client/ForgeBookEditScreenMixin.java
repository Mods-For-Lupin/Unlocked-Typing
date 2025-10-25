package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.impl.common.util.IBookEditScreenAccessor;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.DisplayCache;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BookEditScreen.class)
public abstract class ForgeBookEditScreenMixin implements IBookEditScreenAccessor {

  @Shadow
  protected abstract String getCurrentPageText();

  @Shadow private Component pageMsg;

  @Shadow protected abstract DisplayCache getDisplayCache();

  @Shadow private boolean isSigning;

  @Shadow private String title;

  @Override
  public String unlocked_typing$currentPageText() {
    return this.getCurrentPageText();
  }

  @Override
  public Component unlocked_typing$currentPageMessage() {
    return this.pageMsg;
  }

  @Override
  public DisplayCache unlocked_typing$getDisplayCache() {
    return this.getDisplayCache();
  }

  @Override
  public boolean unlocked_typing$isSigning() {
    return this.isSigning;
  }

  @Override
  public String unlocked_typing$getTitle() {
    return this.title;
  }

  // lambdas are converted to synthetic methods in bytecode, so we target this instead.
  @ModifyConstant(method = "lambda$new$3", constant = @Constant(intValue = 16))
  private static int unlocked_typing$init(int original) {
    return 30;
  }
}
