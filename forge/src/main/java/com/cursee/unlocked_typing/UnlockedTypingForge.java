package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.impl.client.network.ForgeConfigSyncClientHandler;
import com.cursee.unlocked_typing.impl.common.network.packet.ConfigSyncS2CPacket;
import java.util.function.Consumer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod(Constants.MOD_ID)
public class UnlockedTypingForge {

  public UnlockedTypingForge(FMLJavaModLoadingContext context) {
    UnlockedTyping.init();
  }

  public UnlockedTypingForge() {
    this(FMLJavaModLoadingContext.get());
  }
}