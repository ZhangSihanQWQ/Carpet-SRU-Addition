package carpetsruaddition.tnt;

import carpetsruaddition.CarpetSettings;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Manages the limited yaw angles for TNT momentum.
 * Angles are stored as base angles (0-90°) and persisted to a file.
 * For each base angle, the range is: (angle ± 2°) + n * 90° where 0 <= n <= 3.
 */
public class LimitedYawManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("carpet-sru-addition");
    private static final double TOLERANCE = 2.0;
    private static final double ANGLE_STEP = 90.0;
    private static final int MAX_ROTATIONS = 4;

    private static final Set<Integer> limitedAngles = new HashSet<>();
    private static final String CONFIG_DIR = "config/carpet-sru-addition";
    private static final String CONFIG_FILE = "limited_tnt_angles.txt";
    private static Path configPath;

    static {
        initConfigPath();
    }

    private static void initConfigPath() {
        try {
            Path configDir = Paths.get(CONFIG_DIR);
            Files.createDirectories(configDir);
            configPath = configDir.resolve(CONFIG_FILE);
            loadFromFile();
        } catch (IOException e) {
            LOGGER.error("Failed to initialize config path", e);
        }
    }

    /**
     * Load limited angles from file
     */
    private static void loadFromFile() {
        if (configPath == null || !Files.exists(configPath)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(configPath, StandardCharsets.UTF_8);
            limitedAngles.clear();
            CarpetSettings.limitTntAngles = "";

            if (lines.isEmpty()) {
                return;
            }

            String line = lines.get(0).trim();
            if (line.isEmpty()) {
                return;
            }

            String[] parts = line.split(",");
            for (String part : parts) {
                try {
                    int angle = Integer.parseInt(part.trim());
                    if (angle >= 0 && angle <= 90) {
                        limitedAngles.add(angle);
                    }
                } catch (NumberFormatException e) {
                    // Ignore invalid angles
                }
            }

            updateCarpetSetting();
        } catch (IOException e) {
            LOGGER.warn("Failed to load limited angles from file", e);
        }
    }

    /**
     * Check if a yaw angle is within a limited range
     * @param yaw The yaw angle in degrees (0-360)
     * @return true if the yaw is in a limited range
     */
    public static boolean isLimited(float yaw) {
        if (limitedAngles.isEmpty()) {
            return false;
        }

        // Normalize yaw to 0-360
        float normalizedYaw = yaw % 360f;
        if (normalizedYaw < 0) {
            normalizedYaw += 360;
        }

        for (int baseAngle : limitedAngles) {
            for (int rotation = 0; rotation < MAX_ROTATIONS; rotation++) {
                double targetAngle = baseAngle + rotation * ANGLE_STEP;
                if (isInRange(normalizedYaw, targetAngle, TOLERANCE)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if a value is within a range of a target value
     */
    private static boolean isInRange(float value, double target, double tolerance) {
        double diff = Math.abs(value - target);
        // Handle wraparound at 0/360
        if (diff > 180) {
            diff = 360 - diff;
        }
        return diff <= tolerance;
    }

    /**
     * Get all currently limited base angles
     */
    public static Set<Integer> getLimitedAngles() {
        return new HashSet<>(limitedAngles);
    }

    /**
     * Add a base angle to the limit list and save to file
     */
    public static void addLimitedAngle(int angle) {
        if (angle >= 0 && angle <= 90) {
            limitedAngles.add(angle);
            saveToFile();
        }
    }

    /**
     * Remove a base angle from the limit list and save to file
     */
    public static void removeLimitedAngle(int angle) {
        limitedAngles.remove(angle);
        saveToFile();
    }

    /**
     * Clear all limited angles and save to file
     */
    public static void clearLimitedAngles() {
        limitedAngles.clear();
        saveToFile();
    }

    /**
     * Update the CarpetSettings field with current angles
     */
    private static void updateCarpetSetting() {
        List<Integer> sorted = new ArrayList<>(limitedAngles);
        Collections.sort(sorted);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sorted.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(sorted.get(i));
        }
        CarpetSettings.limitTntAngles = sb.toString();
    }

    /**
     * Save the current limited angles to file
     */
    private static void saveToFile() {
        updateCarpetSetting();

        if (configPath == null) {
            LOGGER.warn("Config path not initialized");
            return;
        }

        try {
            Files.createDirectories(configPath.getParent());
            Files.writeString(configPath, CarpetSettings.limitTntAngles, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Failed to save limited angles to file", e);
        }
    }
}



