package com.cursee.peaceful_hunger.mixin;

import com.cursee.peaceful_hunger.impl.common.config.PeacefulHungerConfig;
import net.minecraft.world.Difficulty;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FoodData.class)
public class ForgeFoodDataMixin {

  /// when ticking/updating FoodData for players, use our configured hungerDifficulty value instead
  @ModifyVariable(method = "tick", at = @At("STORE"), ordinal = 0)
  private Difficulty peaceful_hunger$tick$getDifficulty(Difficulty originalHungerDifficulty) {

    return PeacefulHungerConfig.getInstance().getHungerDifficulty();
  }

//  /// Check if mods allows regeneration in peaceful. If the mod doesn't allow regeneration in peaceful, return `allowed by the game` AND `not in peaceful` otherwise, return the original value
//  @ModifyVariable(method = "tick", at = @At("STORE"), ordinal = 0)
//  private boolean peaceful_hunger$tick$getNaturalRegeneration(boolean allowedByGameRule) {
//
//    boolean allowedByModConfig = PeacefulHungerConfig.getInstance().isNaturalRegenAllowedInPeaceful();
//
//    return allowedByGameRule && allowedByModConfig;
//  }
}