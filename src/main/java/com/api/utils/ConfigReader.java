package com.api.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader
 * 
 * Reusable utility to read configuration values from 'config.properties'.
 * Loads once in a static initializer for optimal performance and easy access.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.err.println("Warning: config.properties not found in classpath. Using defaults.");
            } else {
                properties.load(input);
            }
        } catch (Exception e) {
            System.err.println("Error loading config.properties: " + e.getMessage());
        }
    }

    /**
     * Retrieve any property value by key with a fallback default.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Retrieve property value by key.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Convenience method to get base.url.
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://reqres.in");
    }

    /**
     * Convenience method to get request timeout.
     */
    public static int getTimeoutSeconds() {
        return Integer.parseInt(getProperty("timeout.seconds", "10"));
    }
}
