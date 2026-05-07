package com.cursee.unlocked_typing;

/*? if fabric { */
import net.fabricmc.api.ClientModInitializer;
/*? } */

public class UnlockedTypingClient /*? if fabric { */ implements ClientModInitializer /*? } */ {

  /*? if fabric { */
  @Override
  public void onInitializeClient() {
    init();
  }
  /*? } */

  public static void init() {
    // Client-specific initialization
  }
}
