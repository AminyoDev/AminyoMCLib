package dev.aminyo.aminyomclib.utils;

import java.util.*;

/**
 * Enhanced version utilities
 */
public final class VersionUtils {

    private VersionUtils() {}

    /**
     * Parse version string to comparable format
     * @param version version string (e.g., "1.21.8")
     * @return comparable version
     */
    public static ComparableVersion parseVersion(String version) {
        return new ComparableVersion(version);
    }

    /**
     * Check if version is in range
     * @param version version to check
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
     * Check if version is greater than or equal to minimum version
     * @param version version to check
     * @param minVersion minimum version
     * @return true if greater than or equal
     */
    public static boolean isVersionAtLeast(String version, String minVersion) {
        ComparableVersion v = parseVersion(version);
        ComparableVersion min = parseVersion(minVersion);
        return v.compareTo(min) >= 0;
    }

    /**
     * Check if version is less than maximum version
     * @param version version to check
     * @param maxVersion maximum version
     * @return true if less than
     */
    public static boolean isVersionBelow(String version, String maxVersion) {
        ComparableVersion v = parseVersion(version);
        ComparableVersion max = parseVersion(maxVersion);
        return v.compareTo(max) < 0;
    }

    /**
     * Get the latest version from a list
     * @param versions list of version strings
     * @return latest version or null if list is empty
     */
    public static String getLatestVersion(List<String> versions) {
        if (versions == null || versions.isEmpty()) {
            return null;
        }

        return versions.stream()
                .map(VersionUtils::parseVersion)
                .max(ComparableVersion::compareTo)
                .map(ComparableVersion::toString)
                .orElse(null);
    }

    /**
     * Get the oldest version from a list
     * @param versions list of version strings
     * @return oldest version or null if list is empty
     */
    public static String getOldestVersion(List<String> versions) {
        if (versions == null || versions.isEmpty()) {
            return null;
        }

        return versions.stream()
                .map(VersionUtils::parseVersion)
                .min(ComparableVersion::compareTo)
                .map(ComparableVersion::toString)
                .orElse(null);
    }

    /**
     * Sort versions in ascending order
     * @param versions list of version strings
     * @return sorted list of versions
     */
    public static List<String> sortVersions(List<String> versions) {
        if (versions == null || versions.isEmpty()) {
            return new ArrayList<>();
        }

        return versions.stream()
                .map(VersionUtils::parseVersion)
                .sorted()
                .map(ComparableVersion::toString)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Sort versions in descending order
     * @param versions list of version strings
     * @return sorted list of versions (newest first)
     */
    public static List<String> sortVersionsDescending(List<String> versions) {
        if (versions == null || versions.isEmpty()) {
            return new ArrayList<>();
        }

        return versions.stream()
                .map(VersionUtils::parseVersion)
                .sorted(Collections.reverseOrder())
                .map(ComparableVersion::toString)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Check if current platform supports the required version
     * @param requiredVersion required version
     * @return true if supported
     */
    public static boolean isPlatformSupported(String requiredVersion) {
        // This would typically get the current platform version
        // For now, we assume all versions from 1.7.10 to 1.21.8 are supported
        return isVersionInRange(requiredVersion, "1.7.10", "1.21.8");
    }

    /**
     * Get Minecraft version compatibility information
     * @param version Minecraft version
     * @return compatibility info
     */
    public static MinecraftVersionInfo getVersionInfo(String version) {
        ComparableVersion v = parseVersion(version);
        ComparableVersion v1_8 = parseVersion("1.8");
        ComparableVersion v1_13 = parseVersion("1.13");
        ComparableVersion v1_17 = parseVersion("1.17");
        ComparableVersion v1_20 = parseVersion("1.20");

        MinecraftVersionInfo.Builder builder = new MinecraftVersionInfo.Builder(version);

        if (v.compareTo(v1_8) >= 0) {
            builder.supportsUUID(true);
        }

        if (v.compareTo(v1_13) >= 0) {
            builder.supportsFlattering(true);
            builder.supportsNamespacedKeys(true);
        }

        if (v.compareTo(v1_17) >= 0) {
            builder.supportsHexColors(true);
        }

        if (v.compareTo(v1_20) >= 0) {
            builder.supportsComponents(true);
        }

        return builder.build();
    }

    /**
     * Minecraft version information class
     */
    public static class MinecraftVersionInfo {
        private final String version;
        private final boolean supportsUUID;
        private final boolean supportsFlattering;
        private final boolean supportsNamespacedKeys;
        private final boolean supportsHexColors;
        private final boolean supportsComponents;

        private MinecraftVersionInfo(Builder builder) {
            this.version = builder.version;
            this.supportsUUID = builder.supportsUUID;
            this.supportsFlattering = builder.supportsFlattering;
            this.supportsNamespacedKeys = builder.supportsNamespacedKeys;
            this.supportsHexColors = builder.supportsHexColors;
            this.supportsComponents = builder.supportsComponents;
        }

        // Getters
        public String getVersion() { return version; }
        public boolean supportsUUID() { return supportsUUID; }
        public boolean supportsFlattering() { return supportsFlattering; }
        public boolean supportsNamespacedKeys() { return supportsNamespacedKeys; }
        public boolean supportsHexColors() { return supportsHexColors; }
        public boolean supportsComponents() { return supportsComponents; }

        public static class Builder {
            private final String version;
            private boolean supportsUUID = false;
            private boolean supportsFlattering = false;
            private boolean supportsNamespacedKeys = false;
            private boolean supportsHexColors = false;
            private boolean supportsComponents = false;

            public Builder(String version) {
                this.version = version;
            }

            public Builder supportsUUID(boolean supportsUUID) {
                this.supportsUUID = supportsUUID;
                return this;
            }

            public Builder supportsFlattering(boolean supportsFlattering) {
                this.supportsFlattering = supportsFlattering;
                return this;
            }

            public Builder supportsNamespacedKeys(boolean supportsNamespacedKeys) {
                this.supportsNamespacedKeys = supportsNamespacedKeys;
                return this;
            }

            public Builder supportsHexColors(boolean supportsHexColors) {
                this.supportsHexColors = supportsHexColors;
                return this;
            }

            public Builder supportsComponents(boolean supportsComponents) {
                this.supportsComponents = supportsComponents;
                return this;
            }

            public MinecraftVersionInfo build() {
                return new MinecraftVersionInfo(this);
            }
        }
    }

    /**
     * Enhanced comparable version implementation
     */
    public static class ComparableVersion implements Comparable<ComparableVersion> {
        private final String originalVersion;
        private final String cleanVersion;
        private final int[] parts;
        private final String suffix;

        public ComparableVersion(String version) {
            this.originalVersion = version != null ? version : "0.0.0";

            // Clean version and extract suffix
            String[] splitBySuffix = this.originalVersion.split("[-_]", 2);
            this.cleanVersion = splitBySuffix[0].replaceAll("[^0-9.]", "");
            this.suffix = splitBySuffix.length > 1 ? splitBySuffix[1] : "";

            // Parse version parts
            String[] split = this.cleanVersion.split("\\.");
            this.parts = new int[Math.max(split.length, 3)];

            for (int i = 0; i < split.length && i < parts.length; i++) {
                try {
                    parts[i] = Integer.parseInt(split[i]);
                } catch (NumberFormatException e) {
                    parts[i] = 0;
                }
            }
        }

        @Override
        public int compareTo(ComparableVersion other) {
            // Compare version parts
            for (int i = 0; i < Math.max(this.parts.length, other.parts.length); i++) {
                int thisPart = i < this.parts.length ? this.parts[i] : 0;
                int otherPart = i < other.parts.length ? other.parts[i] : 0;

                int result = Integer.compare(thisPart, otherPart);
                if (result != 0) {
                    return result;
                }
            }

            // If version parts are equal, compare suffixes
            return compareSuffixes(this.suffix, other.suffix);
        }

        private int compareSuffixes(String suffix1, String suffix2) {
            if (suffix1.isEmpty() && suffix2.isEmpty()) return 0;
            if (suffix1.isEmpty()) return 1; // No suffix > with suffix
            if (suffix2.isEmpty()) return -1; // With suffix < no suffix

            // Compare suffixes alphabetically
            return suffix1.compareToIgnoreCase(suffix2);
        }

        public int getMajor() { return parts.length > 0 ? parts[0] : 0; }
        public int getMinor() { return parts.length > 1 ? parts[1] : 0; }
        public int getPatch() { return parts.length > 2 ? parts[2] : 0; }
        public String getSuffix() { return suffix; }
        public String getCleanVersion() { return cleanVersion; }

        @Override
        public String toString() {
            return originalVersion;
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
            return Objects.hash(Arrays.hashCode(parts), suffix);
        }
    }
}
