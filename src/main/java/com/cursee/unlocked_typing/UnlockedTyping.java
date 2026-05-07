package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.config.ModConfig;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*? if fabric { */
import net.fabricmc.api.ModInitializer;
/*? } */

public class UnlockedTyping /*? if fabric { */ implements ModInitializer /*? } */ {

  public static final Logger LOG = LoggerFactory.getLogger(Constants.MOD_NAME);
  public static ModConfig CONFIG;

  /*? if fabric { */
  @Override
  public void onInitialize() {
    init();
  }
  /*? } */

  public static void init() {
    CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
  }

  public static Identifier id(String path) {
    return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}