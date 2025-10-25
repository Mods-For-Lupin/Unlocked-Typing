package com.cursee.unlocked_typing.impl.common.util;

import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.joml.Vector3f;

public interface IAbstractSignEditScreenAccessor {

  SignBlockEntity unlocked_typing$getSignBlockEntity();

  String[] unlocked_typing$getMessages();

  SignText unlocked_typing$getText();

  Vector3f unlocked_typing$getSignTextScale();
}
