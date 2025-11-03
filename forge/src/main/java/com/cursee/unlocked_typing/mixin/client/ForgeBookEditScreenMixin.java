package com.cursee.unlocked_typing.mixin.client;

import com.cursee.unlocked_typing.Constants;
import com.cursee.unlocked_typing.impl.common.util.IBookEditScreenAccessor;
import java.lang.reflect.Method;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen.DisplayCache;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookEditScreen.class)
public abstract class ForgeBookEditScreenMixin implements IBookEditScreenAccessor {

  @Shadow
  protected abstract String getCurrentPageText();

  @Shadow private Component pageMsg;

  @Shadow protected abstract DisplayCache getDisplayCache();

  @Shadow private boolean isSigning;

  @Shadow private String title;

  @Override
  public String unlocked_typing$currentPageText() {
    return this.getCurrentPageText();
  }

  @Override
  public Component unlocked_typing$currentPageMessage() {
    return this.pageMsg;
  }

  @Override
  public DisplayCache unlocked_typing$getDisplayCache() {
    return this.getDisplayCache();
  }

  @Override
  public boolean unlocked_typing$isSigning() {
    return this.isSigning;
  }

  @Override
  public String unlocked_typing$getTitle() {
    return this.title;
  }

  // Yes, this is causing your development environment to fail.
  // SEARGE m_98169_ <- official lambda$new$3
  // In the source code we have available, we see the official mapping (for some reason), but we actually want to target the mapping used at runtime
  @SuppressWarnings("all")
  @ModifyConstant(method = "m_98169_", constant = @Constant(intValue = 16))
  private static int unlocked_typing$init(int original) {
    return 30;
  }

//  // accessible method net/minecraft/client/gui/screens/inventory/BookEditScreen <init> (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)V
//  /// After an instance has been initialized, print declared methods and parameter types of those methods.
//  @Inject(at = @At("TAIL"), method = "init()V")
//  private void unlocked_typing$classInitTail(CallbackInfo ci) {
//
//    Constants.LOG.info("new book edit screen!!!!");
//    Constants.LOG.info("new book edit screen!!!!");
//    Constants.LOG.info("new book edit screen!!!!");
//    Constants.LOG.info("new book edit screen!!!! printed four times on purpose lol");
//
//    // TODO delete this block entirely
//    if (true) {
//      Class<BookEditScreen> screenClass = BookEditScreen.class;
//
//      Method[] methods = screenClass.getDeclaredMethods();
//
//      Constants.LOG.info("BookEditScreen.class Declared Methods:");
//      for (Method method : methods) {
//
//        Constants.LOG.info("-method name    {}", method.getName());
//
//        for (var param : method.getParameterTypes()) {
//          Constants.LOG.info("---parameter name    {}", param.getName());
//        }
//      }
//    }
//  }
}
