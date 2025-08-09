package dev.aminyo.aminyomclib.utils;

import java.util.Objects;

/**
 * Location utilities (platform-independent)
 */
public class LocationUtils {

    protected LocationUtils() {}

    /**
     * Simple location representation
     */
    public static class SimpleLocation {
        private final String world;
        private final double x, y, z;
        private final float yaw, pitch;

        public SimpleLocation(String world, double x, double y, double z, float yaw, float pitch) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public SimpleLocation(String world, double x, double y, double z) {
            this(world, x, y, z, 0f, 0f);
        }

        // Getters
        public String getWorld() { return world; }
        public double getX() { return x; }
        public double getY() { return y; }
        public double getZ() { return z; }
        public float getYaw() { return yaw; }
        public float getPitch() { return pitch; }

        @Override
        public String toString() {
            return String.format("%s:%.2f,%.2f,%.2f:%.2f,%.2f", world, x, y, z, yaw, pitch);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            SimpleLocation that = (SimpleLocation) obj;
            return Double.compare(that.x, x) == 0 &&
                    Double.compare(that.y, y) == 0 &&
                    Double.compare(that.z, z) == 0 &&
                    Float.compare(that.yaw, yaw) == 0 &&
                    Float.compare(that.pitch, pitch) == 0 &&
                    Objects.equals(world, that.world);
        }

        @Override
        public int hashCode() {
            return Objects.hash(world, x, y, z, yaw, pitch);
        }
    }

    /**
     * Calculate distance between two points (2D)
     * @param x1 first point X
     * @param z1 first point Z
     * @param x2 second point X
     * @param z2 second point Z
     * @return distance
     */
    public static double distance2D(double x1, double z1, double x2, double z2) {
        double dx = x2 - x1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dz * dz);
    }

    /**
     * Calculate distance between two points (3D)
     * @param x1 first point X
     * @param y1 first point Y
     * @param z1 first point Z
     * @param x2 second point X
     * @param y2 second point Y
     * @param z2 second point Z
     * @return distance
     */
    public static double distance3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Calculate distance between two locations
     * @param loc1 first location
     * @param loc2 second location
     * @return distance or -1 if different worlds
     */
    public static double distance(SimpleLocation loc1, SimpleLocation loc2) {
        if (!Objects.equals(loc1.getWorld(), loc2.getWorld())) {
            return -1;
        }

        return distance3D(loc1.getX(), loc1.getY(), loc1.getZ(),
                loc2.getX(), loc2.getY(), loc2.getZ());
    }

    /**
     * Parse location from string
     * @param locationString location string (world:x,y,z:yaw,pitch)
     * @return parsed location or null if invalid
     */
    public static SimpleLocation parseLocation(String locationString) {
        if (TextUtils.isEmpty(locationString)) {
            return null;
        }

        try {
            String[] parts = locationString.split(":");
            if (parts.length < 2) return null;

            String world = parts[0];
            String[] coords = parts[1].split(",");
            if (coords.length < 3) return null;

            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            double z = Double.parseDouble(coords[2]);

            float yaw = 0f, pitch = 0f;
            if (parts.length > 2) {
                String[] angles = parts[2].split(",");
                if (angles.length >= 2) {
                    yaw = Float.parseFloat(angles[0]);
                    pitch = Float.parseFloat(angles[1]);
                }
            }

            return new SimpleLocation(world, x, y, z, yaw, pitch);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Check if location is within a rectangular area
     * @param location location to check
     * @param corner1 first corner of area
     * @param corner2 second corner of area
     * @return true if within area
     */
    public static boolean isWithinArea(SimpleLocation location, SimpleLocation corner1, SimpleLocation corner2) {
        if (!Objects.equals(location.getWorld(), corner1.getWorld()) ||
                !Objects.equals(location.getWorld(), corner2.getWorld())) {
            return false;
        }

        double minX = Math.min(corner1.getX(), corner2.getX());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        return MathUtils.inRange(location.getX(), minX, maxX) &&
                MathUtils.inRange(location.getY(), minY, maxY) &&
                MathUtils.inRange(location.getZ(), minZ, maxZ);
    }
}
