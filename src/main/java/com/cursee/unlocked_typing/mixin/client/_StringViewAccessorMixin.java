package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.StringViewAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(targets = "net.minecraft.client.gui.components.MultilineTextField$StringView")
public abstract class _StringViewAccessorMixin implements StringViewAccessor {

  @Shadow public abstract int beginIndex();
  @Shadow public abstract int endIndex();

  @Unique
  @Override
  public int unlocked_typing$beginIndex() {
    return beginIndex();
  }

  @Unique
  @Override
  public int unlocked_typing$endIndex() {
    return endIndex();
  }
}
