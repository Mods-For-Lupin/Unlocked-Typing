package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.LineInfoAccessor;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.LineInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LineInfo.class)
public abstract class _LineInfoAccessorMixin implements LineInfoAccessor {

  @Shadow
  @Final
  int x;

  @Shadow
  @Final
  int y;

  @Shadow
  @Final
  Component asComponent;

  @Override
  public int unlocked_typing$getX() {

    return this.x;
  }

  @Override
  public int unlocked_typing$getY() {

    return this.y;
  }

  @Override
  public Component unlocked_typing$getAsComponent() {

    return this.asComponent;
  }
}
