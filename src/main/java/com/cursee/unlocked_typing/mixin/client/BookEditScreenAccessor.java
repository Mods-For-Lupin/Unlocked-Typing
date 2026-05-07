package com.cursee.unlocked_typing.mixin.client;
/*? if fabric { */
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BookEditScreen.class)
public interface BookEditScreenAccessor {
  @Accessor
  MultiLineEditBox getPage();
}
/*? } */
