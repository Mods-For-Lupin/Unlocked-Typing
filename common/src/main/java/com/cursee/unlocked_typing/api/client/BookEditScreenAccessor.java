package com.cursee.unlocked_typing.api.client;

import net.minecraft.client.gui.screens.inventory.BookEditScreen.DisplayCache;
import net.minecraft.network.chat.Component;

public interface BookEditScreenAccessor {

  String unlocked_typing$currentPageText();

  String unlocked_typing$getTitle();

  Component unlocked_typing$currentPageMessage();

  DisplayCache unlocked_typing$getDisplayCache();

  boolean unlocked_typing$isSigning();
}
