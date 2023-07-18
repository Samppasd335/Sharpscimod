package com.mycompany.datagrapher;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class ImageSettings {
    private ImageSettings() {
        throw new AssertionError("Utility class");
    }
    private static JSONObject settings;
    private static void loadSettings() throws IOException {
        String content = Files.readString(Path.of("settings.json"));
        settings = new JSONObject(content);
    }
    public static float getSetting(String settingName) throws AppException {
        if (settings == null) {
            try {
                loadSettings();
            } catch (IOException e) {
                throw new AppException("No settings found " + e.getMessage());
            }
        }
        return settings.getFloat(settingName);
    }
}