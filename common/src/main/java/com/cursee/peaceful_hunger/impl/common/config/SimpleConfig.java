package com.cursee.peaceful_hunger.impl.common.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleConfig {

  private final Map<String, String> entries = new HashMap<>();

  public SimpleConfig(String filePath) throws IOException {
    load(filePath);
  }

  private void load(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();

        // Skip empty lines and comments
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        // Split key=value
        String[] parts = line.split("=", 2);
        if (parts.length == 2) {
          String key = parts[0].trim();
          String value = parts[1].trim();
          entries.put(key, value);
        }
      }
    }
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    String value = entries.get(key);
    if (value == null) {
      return defaultValue;
    }
    return Boolean.parseBoolean(value);
  }

  public int getInteger(String key, int defaultValue) {
    String value = entries.get(key);
    if (value == null) {
      return defaultValue;
    }
    return Integer.parseInt(value);
  }

  public float getFloat(String key, float defaultValue) {
    String value = entries.get(key);
    if (value == null) {
      return defaultValue;
    }
    return Float.parseFloat(value);
  }
}
