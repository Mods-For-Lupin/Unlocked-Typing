package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.platform.Services;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class UnlockedTypingNeoForgeMixinConfig implements IMixinConfigPlugin {

  @Override
  public void onLoad(String mixinPackage) {

  }

  @Override
  public String getRefMapperConfig() {
    return "";
  }

  @Override @SuppressWarnings("all")
  public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

    System.out.println(Constants.PREFIX + "Should apply? " + mixinClassName);

    // disable runtime mixin in dev env, or dev mixin in production env
    if (mixinClassName.contains("InProd") && Services.PLATFORM.isDevelopmentEnvironment()) {
      return false;
    } else if (mixinClassName.contains("InDev") && !Services.PLATFORM.isDevelopmentEnvironment()) {
      return false;
    }

    return true;
  }

  @Override
  public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

  }

  @Override
  public List<String> getMixins() {
    return List.of();
  }

  @Override
  public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

  }

  @Override
  public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

  }
}
