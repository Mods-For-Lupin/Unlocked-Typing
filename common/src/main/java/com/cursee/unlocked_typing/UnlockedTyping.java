package com.cursee.unlocked_typing;

import com.cursee.unlocked_typing.impl.SimpleConfig;
import com.cursee.unlocked_typing.platform.Services;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnlockedTyping {

  public static final Logger LOG = LoggerFactory.getLogger(Constants.MOD_NAME);

  public static void init() {
    createOrLoadConfiguration(ConfigType.COMMON);
    createOrLoadConfiguration(ConfigType.SERVER);
  }

  public static void createOrLoadConfiguration(ConfigType type) {

    Path configDirectoryPath = Services.PLATFORM.getConfigDirectory();
    File configDirectory = new File(configDirectoryPath.toUri());
    if (!configDirectory.isDirectory() && !configDirectory.mkdirs()) {
      return;
    }

    String configFilename = Constants.MOD_ID + type.getSuffix();
    String configFilepath = configDirectoryPath + File.separator + configFilename;
    File configFile = new File(configFilepath);

    if (!configFile.exists()) {

      try (InputStream inputStream = UnlockedTyping.class.getResourceAsStream("assets/" + configFilename)) {

        if (inputStream == null) {
          return;
        }

        try (BufferedInputStream bis = new BufferedInputStream(inputStream); BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(configFile))) {
          byte[] buffer = new byte[8192];

          int bytesRead;
          while((bytesRead = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
          }
        }

      } catch (IOException exception) {
        UnlockedTyping.LOG.info("Failed to unload internal resource {}", configFilename);
      }

    } else {
      try {

        SimpleConfig config = new SimpleConfig(configFilepath);

        if (type == ConfigType.CLIENT) {
          ConfiguredValues.ClientModConfig.displayFormattingExamples = config.getBoolean("display_formatting_examples", ConfiguredValues.ClientModConfig.displayFormattingExamples);
        }

      } catch (IOException exception) {
        UnlockedTyping.LOG.info("Failed to import config from {}, retaining default values.", configFilepath);
      }
    }
  }

  public enum ConfigType {
    COMMON("-common.toml"), CLIENT("-client.toml"), SERVER("-server.toml");

    private final String suffix;

    ConfigType(String suffix) {
      this.suffix = suffix;
    }

    public String getSuffix() {
      return suffix;
    }
  }
}