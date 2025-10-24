package com.cursee.unlocked_typing.mixin;

import com.cursee.unlocked_typing.impl.common.util.IBookPageAccessor;
import java.util.List;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin implements IBookPageAccessor {

  @Shadow @Final private List<String> pages;

  @Shadow private int currentPage;

  @Override
  public String unlocked_typing$getPage() {
    return this.pages.get(this.currentPage);
  }
}
