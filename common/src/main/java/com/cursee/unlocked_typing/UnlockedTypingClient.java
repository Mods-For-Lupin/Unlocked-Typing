package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.UnlockedTyping.ConfigType;

public class UnlockedTypingClient {

  public static void init() {

    UnlockedTyping.createOrLoadConfiguration(ConfigType.CLIENT);
  }
}
