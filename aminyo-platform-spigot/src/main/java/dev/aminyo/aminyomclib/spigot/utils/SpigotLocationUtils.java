package dev.aminyo.aminyomclib.spigot.utils;

import dev.aminyo.aminyomclib.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Spigot location utilities
 */
public final class SpigotLocationUtils extends LocationUtils {

    private SpigotLocationUtils() {
        super();
    }

    /**
     * Convert Bukkit Location to SimpleLocation
     */
    public static SimpleLocation fromBukkitLocation(Location location) {
        if (location == null) return null;

        return new SimpleLocation(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    /**
     * Convert SimpleLocation to Bukkit Location
     */
    public static Location toBukkitLocation(SimpleLocation location) {
        if (location == null) return null;

        World world = Bukkit.getWorld(location.getWorld());
        if (world == null) return null;

        return new Location(
                world,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

    /**
     * Check if location is safe for teleportation
     */
    public static boolean isSafeLocation(Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        // Check if blocks above are air
        if (!world.getBlockAt(x, y + 1, z).getType().name().contains("AIR") ||
                !world.getBlockAt(x, y + 2, z).getType().name().contains("AIR")) {
            return false;
        }

        // Check if block below is solid
        return world.getBlockAt(x, y - 1, z).getType().isSolid();
    }

    /**
     * Find safe location near the given location
     */
    public static Location findSafeLocation(Location location, int radius) {
        if (location == null) return null;

        for (int y = location.getBlockY(); y >= 1 && y <= 255; y++) {
            for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Location testLoc = new Location(location.getWorld(), x + 0.5, y, z + 0.5);
                    if (isSafeLocation(testLoc)) {
                        return testLoc;
                    }
                }
            }
        }

        return location; // Return original if no safe location found
    }

    /**
     * Get highest block at location
     */
    public static Location getHighestBlock(World world, int x, int z) {
        if (world == null) return null;

        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x + 0.5, y + 1, z + 0.5);
    }
}