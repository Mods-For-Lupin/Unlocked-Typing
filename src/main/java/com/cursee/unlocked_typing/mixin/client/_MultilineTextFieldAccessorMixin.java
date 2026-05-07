package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.api.client.MultilineTextFieldAccessor;
import net.minecraft.client.gui.components.MultilineTextField;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MultilineTextField.class)
public abstract class _MultilineTextFieldAccessorMixin implements MultilineTextFieldAccessor {
    // I will use this to expose methods if needed.
}
