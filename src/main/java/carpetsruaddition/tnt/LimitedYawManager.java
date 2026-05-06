package carpetsruaddition.tnt;

import carpetsruaddition.CarpetSettings;
import java.util.*;

/**
 * Manages the limited yaw angles for TNT momentum.
 * Angles are stored as base angles (0-90°).
 * For each base angle, the range is: (angle ± 2°) + n * 90° where 0 <= n <= 3.
 */
public class LimitedYawManager {
    private static final double TOLERANCE = 2.0;
    private static final double ANGLE_STEP = 90.0;
    private static final int MAX_ROTATIONS = 4;

    private static final Set<Integer> limitedAngles = new HashSet<>();

    /**
     * Update the limited angles from the setting
     */
    public static void updateFromSetting() {
        limitedAngles.clear();
        String setting = CarpetSettings.limitTntRandomMomentum.trim();
        if (setting.isEmpty()) {
            return;
        }

        String[] parts = setting.split(",");
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
    }

    /**
     * Check if a yaw angle is within a limited range
     * @param yaw The yaw angle in degrees (0-360)
     * @return true if the yaw is in a limited range
     */
    public static boolean isLimited(float yaw) {
        updateFromSetting();

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
        updateFromSetting();
        return new HashSet<>(limitedAngles);
    }

    /**
     * Add a base angle to the limit list
     */
    public static void addLimitedAngle(int angle) {
        if (angle >= 0 && angle <= 90) {
            updateFromSetting();
            limitedAngles.add(angle);
            saveToSetting();
        }
    }

    /**
     * Remove a base angle from the limit list
     */
    public static void removeLimitedAngle(int angle) {
        updateFromSetting();
        limitedAngles.remove(angle);
        saveToSetting();
    }

    /**
     * Clear all limited angles
     */
    public static void clearLimitedAngles() {
        limitedAngles.clear();
        CarpetSettings.limitTntRandomMomentum = "";
    }

    /**
     * Save the current limited angles to the setting
     */
    private static void saveToSetting() {
        List<Integer> sorted = new ArrayList<>(limitedAngles);
        Collections.sort(sorted);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sorted.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(sorted.get(i));
        }
        CarpetSettings.limitTntRandomMomentum = sb.toString();
    }
}

