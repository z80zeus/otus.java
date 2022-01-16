package ru.otus.jdbc.mapper;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {

    private final EntityClassMetaData<T> tMetaData;    // Метаданные типа T.

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        tMetaData = entityClassMetaData;
        selectAllSql = "select id," + getFieldsNamesWithoutId(",") +" from " + tMetaData.getName();
        selectByIdSql = "select id," + getFieldsNamesWithoutId(",") + " from " + tMetaData.getName() + " where id = ?";

        var qMarks = "?,".repeat(tMetaData.getFieldsWithoutId().size());
        insertSql = "insert into " + tMetaData.getName() + " (" + getFieldsNamesWithoutId(",") + ") values (" +
                qMarks.substring(0, qMarks.length() - 1) + ")";

        updateSql = "update " + tMetaData.getName() + " set " + getFieldsNamesWithoutId("=?,") + "=? where id=?";
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }

    private String getAllFieldsNames(@SuppressWarnings("SameParameterValue") String separator) {
        return tMetaData.getIdField().getName() + separator + getFieldsNamesWithoutId(separator);
    }

    private String getFieldsNamesWithoutId(String separator) {
        var fieldNames = new StringBuilder();
        for (var field: tMetaData.getFieldsWithoutId()) {
            fieldNames.append(field.getName()).append(separator);
        }
        return fieldNames.substring(0, fieldNames.length() - separator.length());
    }
}
