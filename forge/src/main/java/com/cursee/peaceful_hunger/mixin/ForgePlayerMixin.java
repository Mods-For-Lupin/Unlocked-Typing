package com.cursee.peaceful_hunger.mixin;

import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Key;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public class ForgePlayerMixin {

  /// Check if the mod allows regeneration in peaceful. If the mod doesn't allow regeneration in peaceful, cancel tick regeneration being called on server player otherwise, continue as normal
  @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
  private boolean peaceful_hunger$tickInject(GameRules instance, Key<BooleanValue> key) {

    boolean allowedByGameRule = instance.getBoolean(key);
    boolean allowedByModConfig = PeacefulHungerConfig.getInstance().isNaturalRegenAllowedInPeaceful();

    return allowedByGameRule && allowedByModConfig;
  }
}