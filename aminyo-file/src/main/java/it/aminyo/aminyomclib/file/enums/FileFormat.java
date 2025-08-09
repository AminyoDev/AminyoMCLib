package it.aminyo.aminyomclib.file.enums;

public enum FileFormat {
    YAML("yml", "yaml"),
    JSON("json"),
    TOML("toml"),
    PROPERTIES("properties");

    private final String[] extensions;

    FileFormat(String... extensions) {
        this.extensions = extensions;
    }

    public String[] getExtensions() {
        return extensions.clone();
    }

    public String getDefaultExtension() {
        return extensions[0];
    }

    public static FileFormat fromExtension(String extension) {
        if (extension == null) return null;

        String ext = extension.toLowerCase();
        if (ext.startsWith(".")) {
            ext = ext.substring(1);
        }

        for (FileFormat format : values()) {
            for (String formatExt : format.extensions) {
                if (formatExt.equals(ext)) {
                    return format;
                }
            }
        }
        return null;
    }
}
