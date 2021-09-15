package online.lbprotocol.easy.jdbc.mapper;

import online.lbprotocol.easy.jdbc.model.Entity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

public class EntityRowMapper implements RowMapper<Entity> {
    private static EntityRowMapper instance;

    public static EntityRowMapper getInstance() {
        if (instance == null) {
            instance = new EntityRowMapper();
        }

        return instance;
    }

    @Override
    public Entity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Entity entity = new Entity();

        // Read all column values.
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
            String columnName = metaData.getColumnName(i);
            int columnType = metaData.getColumnType(i);

            if (Types.CHAR == columnType || Types.VARCHAR == columnType || Types.LONGVARCHAR == columnType) {
                entity.put(columnName, rs.getString(i));

            } else if (Types.SMALLINT == columnType || Types.TINYINT == columnType) {
                entity.put(columnName, rs.getInt(i));

            } else if (Types.INTEGER == columnType || Types.BIGINT == columnType) {
                entity.put(columnName, rs.getLong(i));

            } else if (Types.FLOAT == columnType) {
                entity.put(columnName, rs.getFloat(i));

            } else if (Types.DECIMAL == columnType) {
                entity.put(columnName, rs.getBigDecimal(i));

            } else if (Types.TIMESTAMP == columnType) {
                entity.put(columnName, rs.getTimestamp(i).getTime());

            } else {
                throw new SQLException("Unsupported column type: " + columnType);
            }
        }

        return entity;
    }

}
