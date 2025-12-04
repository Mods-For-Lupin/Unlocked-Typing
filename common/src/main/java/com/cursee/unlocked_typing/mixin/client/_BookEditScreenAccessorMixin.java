package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.BookEditScreenAccessor;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BookEditScreen.class)
public class _BookEditScreenAccessorMixin implements BookEditScreenAccessor {

  @Shadow
  private MultiLineEditBox page;

  @Unique
  public MultiLineEditBox unlocked_typing$getPage() {
    return this.page;
  }
}
