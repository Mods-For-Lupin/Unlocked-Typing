package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.BookEditScreenAccessor;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.DisplayCache;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BookEditScreen.class)
public abstract class _ForgeBookEditScreenAccessorMixin implements BookEditScreenAccessor {

  @Shadow
  private Component pageMsg;
  @Shadow
  private boolean isSigning;
  @Shadow
  private String title;

  @Shadow
  protected abstract String getCurrentPageText();

  @Shadow
  protected abstract DisplayCache getDisplayCache();

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
}
