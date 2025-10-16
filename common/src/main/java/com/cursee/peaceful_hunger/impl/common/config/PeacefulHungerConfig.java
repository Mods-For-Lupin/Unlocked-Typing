package com.cursee.peaceful_hunger.impl.common.config;

import com.cursee.peaceful_hunger.Constants;
import com.cursee.peaceful_hunger.platform.Services;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import net.minecraft.world.Difficulty;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class PeacefulHungerConfig {

  public static final String LOG_PREFIX = "[Peaceful Hunger] ";
  private static PeacefulHungerConfig instance = null;
  private Difficulty hungerDifficulty = Difficulty.HARD;
  private boolean naturalRegenAllowedInPeaceful = false;

  public static PeacefulHungerConfig getInstance() {
    return instance;
  }

  public Difficulty getHungerDifficulty() {
    return hungerDifficulty;
  }

  public void setHungerDifficulty(Difficulty hungerDifficulty) {
    this.hungerDifficulty = hungerDifficulty;
  }

  public boolean isNaturalRegenAllowedInPeaceful() {
    return naturalRegenAllowedInPeaceful;
  }

  public void setNaturalRegenAllowedInPeaceful(boolean naturalRegenAllowedInPeaceful) {
    this.naturalRegenAllowedInPeaceful = naturalRegenAllowedInPeaceful;
  }

  public void onLoad() {
    if (instance == null) {
      instance = this;
    }
    this.createOrLoadConfiguration();
  }

  public void createOrLoadConfiguration() {

    String configDirectoryPathString = Services.PLATFORM.getGameDirectory() + File.separator + "config";
    File configDirectory = new File(configDirectoryPathString);
    if (!configDirectory.isDirectory() && !configDirectory.mkdirs()) {
      System.out.println(LOG_PREFIX + "Failed to create config directory, Faster Happy Ghasts retaining default values.");
      return;
    }

    String configFileNameString = Constants.MOD_ID + "-common.toml";
    String configFilePathString = configDirectoryPathString + File.separator + configFileNameString;
    File configFile = new File(configFilePathString);

    if (!configFile.exists()) {
      try (InputStream inputStream = PeacefulHungerConfig.class.getClassLoader().getResourceAsStream("assets/" + configFileNameString)) {

        if (inputStream == null) {
          System.out.println(LOG_PREFIX + "Failed to initialize InputStream instance for assets/" + configFileNameString);
          return;
        }

        try (BufferedInputStream bis = new BufferedInputStream(inputStream); BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(configFile))) {
          byte[] buffer = new byte[8192];

          int bytesRead;
          while ((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
          }
        }

        System.out.println(LOG_PREFIX + "Created new configuration file: " + configFilePathString);
      } catch (IOException e) {
        System.out.println(LOG_PREFIX + "Failed to read internal resource: assets/" + configFileNameString);
        System.out.println(LOG_PREFIX + e.getMessage());
      }
    } else {

      // config file exists at expected location

      System.out.println(LOG_PREFIX + "Reading configuration file: " + configFilePathString);

      try {
        SimpleConfig config = new SimpleConfig(configFilePathString);

        // Deprecated
//        this.hungerDifficulty = Difficulty.byId(config.getInteger("hunger_difficulty", 3));

        int internalDifficulty = config.getInteger("hunger_difficulty", 3);

        switch (internalDifficulty) {
          case 0 -> this.hungerDifficulty = Difficulty.PEACEFUL;
          case 1 -> this.hungerDifficulty = Difficulty.EASY;
          case 2 -> this.hungerDifficulty = Difficulty.NORMAL;
          case 3 -> this.hungerDifficulty = Difficulty.HARD;
          default -> System.out.println(LOG_PREFIX + "Failed to read difficulty, using default value.");
        }

        this.naturalRegenAllowedInPeaceful = config.getBoolean("natural_regen_in_peaceful", false);
      } catch (IOException ignored) {
        System.out.println(LOG_PREFIX + "Failed to read " + configFilePathString + ", retaining default config values");
      }

      System.out.println(LOG_PREFIX + "Difficulty for hunger checks was set to " + this.hungerDifficulty.name());
    }
  }
}
