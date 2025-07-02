package dev.aminyo.aminyomclib.bukkit.utils.version;

import com.google.common.base.Preconditions;
import dev.aminyo.aminyomclib.core.enums.ServerEnvironment;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used to check the Minecraft version the server is running.
 * Useful when different Minecraft versions require different implementations.
 */
@SuppressWarnings("unused")
public class VersionUtils {

    private static final Pattern versionPattern = Pattern.compile("(?i)(?<major>\\d)\\.(?<minor>\\d+)\\.?(?<patch>\\d+)?");

    // region Versions
    public static final VersionUtils v1_8_0 = new VersionUtils(8, 0);
    public static final VersionUtils v1_8_1 = new VersionUtils(8, 1);
    public static final VersionUtils v1_8_2 = new VersionUtils(8, 2);
    public static final VersionUtils v1_8_3 = new VersionUtils(8, 3);
    public static final VersionUtils v1_8_4 = new VersionUtils(8, 4);
    public static final VersionUtils v1_8_5 = new VersionUtils(8, 5);
    public static final VersionUtils v1_8_6 = new VersionUtils(8, 6);
    public static final VersionUtils v1_8_7 = new VersionUtils(8, 7);
    public static final VersionUtils v1_8_8 = new VersionUtils(8, 8);
    public static final VersionUtils v1_9_0 = new VersionUtils(9, 0);
    public static final VersionUtils v1_9_1 = new VersionUtils(9, 1);
    public static final VersionUtils v1_9_2 = new VersionUtils(9, 2);
    public static final VersionUtils v1_9_3 = new VersionUtils(9, 3);
    public static final VersionUtils v1_9_4 = new VersionUtils(9, 4);
    public static final VersionUtils v1_10_0 = new VersionUtils(10, 0);
    public static final VersionUtils v1_10_1 = new VersionUtils(10, 1);
    public static final VersionUtils v1_10_2 = new VersionUtils(10, 2);
    public static final VersionUtils v1_11_0 = new VersionUtils(11, 0);
    public static final VersionUtils v1_11_1 = new VersionUtils(11, 1);
    public static final VersionUtils v1_11_2 = new VersionUtils(11, 2);
    public static final VersionUtils v1_12_0 = new VersionUtils(12, 0);
    public static final VersionUtils v1_12_1 = new VersionUtils(12, 1);
    public static final VersionUtils v1_12_2 = new VersionUtils(12, 2);
    public static final VersionUtils v1_13_0 = new VersionUtils(13, 0);
    public static final VersionUtils v1_13_1 = new VersionUtils(13, 1);
    public static final VersionUtils v1_13_2 = new VersionUtils(13, 2);
    public static final VersionUtils v1_14_0 = new VersionUtils(14, 0);
    public static final VersionUtils v1_14_1 = new VersionUtils(14, 1);
    public static final VersionUtils v1_14_2 = new VersionUtils(14, 2);
    public static final VersionUtils v1_14_3 = new VersionUtils(14, 3);
    public static final VersionUtils v1_14_4 = new VersionUtils(14, 4);
    public static final VersionUtils v1_15_0 = new VersionUtils(15, 0);
    public static final VersionUtils v1_15_1 = new VersionUtils(15, 1);
    public static final VersionUtils v1_15_2 = new VersionUtils(15, 2);
    public static final VersionUtils v1_16_0 = new VersionUtils(16, 0);
    public static final VersionUtils v1_16_1 = new VersionUtils(16, 1);
    public static final VersionUtils v1_16_2 = new VersionUtils(16, 2);
    public static final VersionUtils v1_16_3 = new VersionUtils(16, 3);
    public static final VersionUtils v1_16_4 = new VersionUtils(16, 4);
    public static final VersionUtils v1_16_5 = new VersionUtils(16, 5);
    public static final VersionUtils v1_17_0 = new VersionUtils(17, 0);
    public static final VersionUtils v1_17_1 = new VersionUtils(17, 1);
    public static final VersionUtils v1_18_0 = new VersionUtils(18, 0);
    public static final VersionUtils v1_18_1 = new VersionUtils(18, 1);
    public static final VersionUtils v1_18_2 = new VersionUtils(18, 2);
    public static final VersionUtils v1_19_0 = new VersionUtils(19, 0);
    public static final VersionUtils v1_19_1 = new VersionUtils(19, 1);
    public static final VersionUtils v1_19_2 = new VersionUtils(19, 2);
    public static final VersionUtils v1_19_3 = new VersionUtils(19, 3);
    public static final VersionUtils v1_19_4 = new VersionUtils(19, 4);
    public static final VersionUtils v1_20_0 = new VersionUtils(20, 0);
    public static final VersionUtils v1_20_1 = new VersionUtils(20, 1);
    public static final VersionUtils v1_20_2 = new VersionUtils(20, 2);
    public static final VersionUtils v1_20_3 = new VersionUtils(20, 3);
    public static final VersionUtils v1_20_4 = new VersionUtils(20, 4);
    public static final VersionUtils v1_20_5 = new VersionUtils(20, 5);
    public static final VersionUtils v1_20_6 = new VersionUtils(20, 6);
    public static final VersionUtils v1_21_0 = new VersionUtils(21, 0);
    public static final VersionUtils v1_21_1 = new VersionUtils(21, 1);
    public static final VersionUtils v1_21_2 = new VersionUtils(21, 2);
    public static final VersionUtils v1_21_3 = new VersionUtils(21, 3);
    public static final VersionUtils v1_21_4 = new VersionUtils(21, 4);
    public static final VersionUtils v1_22_0 = new VersionUtils(22, 0);
    // endregion

    @NotNull
    private static final VersionUtils currentVersion;

    @NotNull
    public static VersionUtils getCurrentVersion() {
        return currentVersion;
    }

    static {
        int major = 0;
        int patch = 0;

        String versionStr = Bukkit.getVersion();

        // Attempt to get version from
        if (ServerEnvironment.isPaper()) {
            try {
                versionStr = (String) Bukkit.getServer().getClass().getMethod("getVersionUtils").invoke(Bukkit.getServer());
            } catch (Exception ignored) {
            }
        }

        Matcher matcher = versionPattern.matcher(versionStr);
        if (matcher.find()) {
            if (matcher.groupCount() >= 2) {
                major = parseIntSafe(matcher.group("minor"));
            }
            if (matcher.groupCount() >= 3) {
                patch = parseIntSafe(matcher.group("patch"));
            }
        }

        // Fallback to attempt to get major version.
        if (major == 0 && getCraftBukkitVersion() != null) {
            String[] version = getCraftBukkitVersion().split("_");
            if (version.length >= 2) {
                major = parseIntSafe(version[1]);
            }
        }

        currentVersion = new VersionUtils(major, patch);
    }

    private final int major;
    private final int patch;

    public VersionUtils(int major, int patch) {
        this.major = major;
        this.patch = patch;
    }

    public int getMajor() {
        return major;
    }

    public int getPatch() {
        return patch;
    }

    /**
     * Gets the display name of a version. E.g. "1.8.8" or "1.18". Ignores pre-release versions.
     *
     * @return The display name of the version.
     */
    @NotNull
    public String getDisplayName() {
        return getDisplayName(false);
    }

    /**
     * Gets the display name of a version. E.g. "1.8.8", "1.18", or "1.18.0". Ignores pre-release versions.
     *
     * @param includeEmptyPatch Whether to include the patch version if it's 0. (e.g. "1.8.0" vs "1.8")
     * @return The display name of the version.
     */
    @NotNull
    public String getDisplayName(boolean includeEmptyPatch) {
        return "1." + major + ((patch == 0 && !includeEmptyPatch) ? "" : "." + patch);
    }

    /**
     * @return Whether the version is at least 1.13.
     */
    public static boolean isNew() {
        return currentVersion.getMajor() >= 13;
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version matches the provided version.
     */
    public static boolean is(@NotNull VersionUtils version) {
        checkNotNull(version);
        return currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() == version.getPatch();
    }

    /**
     * @param version The version to check.
     * @return Whether the current server major version matches the provided major version.
     */
    public static boolean isMajor(@NotNull VersionUtils version) {
        checkNotNull(version);
        return currentVersion.getMajor() == version.getMajor();
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version is newer than the provided version.
     */
    public static boolean isNewerThan(@NotNull VersionUtils version) {
        checkNotNull(version);
        return (currentVersion.getMajor() > version.getMajor()) || (currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() > version.getPatch());
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version is at least the provided version.
     */
    public static boolean isAtLeast(@NotNull VersionUtils version) {
        checkNotNull(version);
        return (currentVersion.getMajor() > version.getMajor()) || (currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() >= version.getPatch());
    }

    /**
     * @param version The version to check.
     * @return Whether the current server version is older than the provided version.
     */
    public static boolean isOlderThan(@NotNull VersionUtils version) {
        checkNotNull(version);
        return (currentVersion.getMajor() < version.getMajor()) || (currentVersion.getMajor() == version.getMajor() && currentVersion.getPatch() < version.getPatch());
    }

    /**
     * Attempts to get the CraftBukkit version from the server implementation's package name.
     *
     * @return The CraftBukkit version, or null if it could not be determined.
     * @deprecated Paper no longer includes the version in the CraftBukkit package name.
     * Using this on Paper 1.20.5 and newer will always return null.
     * Use {@link #getCurrentVersion()} and {@link #getDisplayName()} instead.
     */
    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public static @Nullable String getCraftBukkitVersion() {
        String[] pckg = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
        return (pckg.length >= 4) ? pckg[3] : null;
    }

    /**
     * Parses an integer from a String, returning 0 if it's not valid.
     *
     * @param str The String to parse.
     * @return The parsed String, or 0 if invalid.
     */
    private static int parseIntSafe(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ignored) {
            return 0;
        }
    }

    /**
     * Checks that the provided VersionUtils is not null, and throws a
     * NullPointerException with a custom message if it is.
     *
     * @param version The version to check
     */
    private static void checkNotNull(@Nullable VersionUtils version) {
        Preconditions.checkNotNull(version, "Version cannot be null");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VersionUtils)) return false;
        VersionUtils other = (VersionUtils) o;
        return major == other.major && patch == other.patch;
    }

    @Override
    public int hashCode() {
        int result = 31;
        result = 31 * result + major;
        result = 31 * result + patch;
        return result;
    }

    @Override
    public String toString() {
        return "VersionUtils(major=" + this.getMajor() + ", patch=" + this.getPatch() + ")";
    }
}

