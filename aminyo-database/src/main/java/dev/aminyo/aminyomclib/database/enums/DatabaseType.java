package dev.aminyo.aminyomclib.database;

/**
 * Supported database types
 */
public enum DatabaseType {
    MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql://{host}:{port}/{database}"),
    MARIADB("org.mariadb.jdbc.Driver", "jdbc:mariadb://{host}:{port}/{database}"),
    POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql://{host}:{port}/{database}"),
    H2("org.h2.Driver", "jdbc:h2:{file}"),
    SQLITE("org.sqlite.JDBC", "jdbc:sqlite:{file}"),
    MONGODB("", "mongodb://{host}:{port}"),
    JSON("", "");

    private final String driverClass;
    private final String urlPattern;

    DatabaseType(String driverClass, String urlPattern) {
        this.driverClass = driverClass;
        this.urlPattern = urlPattern;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public boolean isSQL() {
        return this != MONGODB && this != JSON;
    }

    public boolean isNoSQL() {
        return this == MONGODB;
    }

    public boolean isFile() {
        return this == H2 || this == SQLITE || this == JSON;
    }
}
