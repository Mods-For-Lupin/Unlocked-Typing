package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.DisplayCacheAccessor;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.DisplayCache;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.LineInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DisplayCache.class)
public abstract class _DisplayCacheAccessorMixin implements DisplayCacheAccessor {

  @Shadow
  @Final
  LineInfo[] lines;

  @Override
  public LineInfo[] unlocked_typing$getLines() {

    return lines;
  }
}
