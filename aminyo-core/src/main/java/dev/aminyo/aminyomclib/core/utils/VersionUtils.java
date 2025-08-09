package dev.aminyo.aminyomclib.core.utils;

import dev.aminyo.aminyomclib.core.AminyoMCLibImpl;
import dev.aminyo.aminyomclib.core.interfaces.AminyoMCLib;

public final class VersionUtils {

    private VersionUtils() {
    }

    /**
     * Parse version string to comparable format
     *
     * @param version version string (e.g., "1.21.8")
     * @return comparable version
     */
    public static ComparableVersion parseVersion(String version) {
        return new ComparableVersion(version);
    }

    /**
     * Check if version is in range
     *
     * @param version    version to check
     * @param minVersion minimum version (inclusive)
     * @param maxVersion maximum version (inclusive)
     * @return true if in range
     */
    public static boolean isVersionInRange(String version, String minVersion, String maxVersion) {
        ComparableVersion v = parseVersion(version);
        ComparableVersion min = parseVersion(minVersion);
        ComparableVersion max = parseVersion(maxVersion);

        return v.compareTo(min) >= 0 && v.compareTo(max) <= 0;
    }

    /**
     * Check if current platform supports the required version
     *
     * @param requiredVersion required version
     * @return true if supported
     */
    public static boolean isPlatformSupported(String requiredVersion) {
        AminyoMCLib lib = AminyoMCLibImpl.getInstance();
        if (lib == null) return false;

        String currentVersion = lib.getPlatformAdapter().getMinecraftVersion();
        return isVersionInRange(currentVersion, "1.7.10", "1.21.8");
    }

    /**
     * Comparable version implementation
     */
    public static class ComparableVersion implements Comparable<ComparableVersion> {
        private final String version;
        private final int[] parts;

        public ComparableVersion(String version) {
            this.version = version.replaceAll("[^0-9.]", "");

            String[] split = this.version.split("\\.");
            this.parts = new int[Math.max(split.length, 3)];

            for (int i = 0; i < split.length; i++) {
                try {
                    parts[i] = Integer.parseInt(split[i]);
                } catch (NumberFormatException e) {
                    parts[i] = 0;
                }
            }
        }

        @Override
        public int compareTo(ComparableVersion other) {
            for (int i = 0; i < Math.max(this.parts.length, other.parts.length); i++) {
                int thisPart = i < this.parts.length ? this.parts[i] : 0;
                int otherPart = i < other.parts.length ? other.parts[i] : 0;

                int result = Integer.compare(thisPart, otherPart);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return version;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ComparableVersion that = (ComparableVersion) obj;
            return this.compareTo(that) == 0;
        }

        @Override
        public int hashCode() {
            return version.hashCode();
        }
    }
}
