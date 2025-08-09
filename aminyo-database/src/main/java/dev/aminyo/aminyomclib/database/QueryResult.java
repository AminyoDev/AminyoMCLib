package dev.aminyo.aminyomclib.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Database query result wrapper
 */
public class QueryResult {
    private final List<Map<String, Object>> rows;
    private final int updateCount;
    private final boolean isUpdate;

    private QueryResult(List<Map<String, Object>> rows, int updateCount, boolean isUpdate) {
        this.rows = rows != null ? rows : new ArrayList<>();
        this.updateCount = updateCount;
        this.isUpdate = isUpdate;
    }

    public static QueryResult fromResultSet(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                row.put(columnName, value);
            }
            rows.add(row);
        }

        return new QueryResult(rows, -1, false);
    }

    public static QueryResult fromUpdateCount(int updateCount) {
        return new QueryResult(null, updateCount, true);
    }

    public static QueryResult empty() {
        return new QueryResult(new ArrayList<>(), 0, false);
    }

    public List<Map<String, Object>> getRows() {
        return new ArrayList<>(rows);
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }

    public int getRowCount() {
        return rows.size();
    }

    public Map<String, Object> getFirstRow() {
        return rows.isEmpty() ? new HashMap<>() : rows.get(0);
    }
}

