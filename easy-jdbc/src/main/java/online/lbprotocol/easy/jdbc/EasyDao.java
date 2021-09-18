package online.lbprotocol.easy.jdbc;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import online.lbprotocol.easy.jdbc.builder.*;
import online.lbprotocol.easy.jdbc.model.Entity;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mapping.Association;
import org.springframework.data.relational.core.mapping.*;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.data.util.ParsingUtils;
import org.springframework.data.util.TypeInformation;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yichen for easy_project
 * @since 2021/8/1
 */
@Repository
public class EasyDao {

    public static final String DEFAULT_ID_COLUMN = "id";

    @Getter
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private RelationalMappingContext relationalMappingContext;
    @Resource
    private EntityDao entityDao;

    public <T> T get(Class<T> type, Object idValue) {
        return get(getTableName(type), new BeanPropertyRowMapper<>(type), getIdColumn(type), idValue);
    }

    public <T> T get(String tableName, RowMapper<T> rowMapper, Object idValue) {
        return get(tableName, rowMapper, DEFAULT_ID_COLUMN, idValue);
    }

    public <T> T get(String tableName, RowMapper<T> rowMapper, String idColumn, Object idValue) {
        List<T> result = select(tableName, rowMapper, b -> {
            b.where(idColumn, idValue);
            b.page(0, 1);
        });
        return result.stream().findFirst().orElse(null);
    }

    public int count(Class<?> type) {
        return count(type, b -> {
        });
    }

    public int count(Class<?> type, SelectCondition condition) {
        return count(getTableName(type), getIdColumn(type), condition);
    }

    public int count(String tableName) {
        return count(tableName, DEFAULT_ID_COLUMN);
    }

    public int count(String tableName, String idColumn) {
        return count(tableName, idColumn, b -> {
        });
    }

    public int count(String tableName, String idColumn, SelectCondition condition) {
        var selectBuilder = new DefaultSelectBuilder(tableName);
        condition.condition(selectBuilder);
        var build = selectBuilder.buildForCount(idColumn);
        var count = namedParameterJdbcTemplate.queryForObject(build.getLeft(), build.getRight(), Integer.class);
        return count == null ? 0 : count;
    }

    public boolean exists(Class<?> type, SelectCondition condition) {
        return count(type, condition) != 0;
    }

    public boolean exists(String tableName, String idColumn, SelectCondition condition) {
        return count(tableName, idColumn, condition) != 0;
    }

    public <T> T selectOne(Class<T> type, String columnName, String columnValue) {
        return select(type, columnName, columnValue, 0, 1).stream().findFirst().orElse(null);
    }

    public <T> T selectOne(Class<T> type, SelectCondition condition) {
        return selectOne(getTableName(type), new BeanPropertyRowMapper<>(type), condition);
    }

    public <T> T selectOne(String tableName, RowMapper<T> rowMapper, String columnName, String columnValue) {
        return select(tableName, rowMapper, columnName, columnValue, 0, 1).stream().findFirst().orElse(null);
    }

    public <T> T selectOne(String tableName, RowMapper<T> rowMapper, SelectCondition condition) {
        var selectBuilder = new DefaultSelectBuilder(tableName);
        condition.condition(selectBuilder);
        selectBuilder.page(0, 1);
        return select(tableName, rowMapper, condition).stream().findFirst().orElse(null);
    }

    public <T> List<T> select(Class<T> type) {
        return select(type, b -> {
        });
    }

    public <T> List<T> select(Class<T> type, String columnName, String columnValue, int pageNumber, int pageSize) {
        return select(type, b -> {
            b.where(columnName, columnValue).page(pageNumber, pageSize);
        });
    }

    public <T> List<T> select(Class<T> type, int pageNumber, int pageSize) {
        return select(type, b -> b.page(pageNumber, pageSize));
    }

    public <T> List<T> select(Class<T> type, SelectCondition condition, String... columns) {
        return select(getTableName(type), new BeanPropertyRowMapper<>(type), condition, columns);
    }

    public <T> List<T> select(String tableName, RowMapper<T> rowMapper) {
        return select(tableName, rowMapper, b -> {
        });
    }

    public <T> List<T> select(String tableName, RowMapper<T> rowMapper, String columnName, String columnValue, int pageNumber, int pageSize) {
        return select(tableName, rowMapper, b -> {
            b.where(columnName, columnValue).page(pageNumber, pageSize);
        });
    }

    public <T> List<T> select(String tableName, RowMapper<T> rowMapper, int pageNumber, int pageSize) {
        return select(tableName, rowMapper, b -> b.page(pageNumber, pageSize));
    }

    public <T> List<T> select(String tableName, RowMapper<T> rowMapper, SelectCondition condition, String... columns) {
        var selectBuilder = new DefaultSelectBuilder(tableName, columns);
        condition.condition(selectBuilder);
        var build = selectBuilder.build();

        var sqlParameterSource = new SqlParameterSource() {
            @Override
            public boolean hasValue(String paramName) {
                return build.getRight().get(paramName) != null;
            }

            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                return build.getRight().get(paramName);
            }

            @Override
            public String[] getParameterNames() {
                return build.getRight().keySet().toArray(new String[0]);
            }
        };

        return namedParameterJdbcTemplate.query(build.getLeft(), sqlParameterSource, rowMapper);
    }

    public <T> Page<T> selectAndCount(Class<T> type, String columnName, Object columnValue, int pageNumber, int pageSize) {
        return selectAndCount(type, b -> {
            b.where(columnName, columnValue).page(pageNumber, pageSize);
        });
    }

    public <T> Page<T> selectAndCount(Class<T> type, int pageNumber, int pageSize) {
        return selectAndCount(type, b -> {
            b.page(pageNumber, pageSize);
        });
    }

    public <T> Page<T> selectAndCount(Class<T> type, SelectCondition condition) {
        return selectAndCount(getTableName(type), getIdColumn(type), new BeanPropertyRowMapper<>(type), condition);
    }

    public <T> Page<T> selectAndCount(String tableName, RowMapper<T> rowMapper, String columnName, Object columnValue, int pageNumber, int pageSize) {
        return selectAndCount(tableName, rowMapper, b -> {
            b.where(columnName, columnValue).page(pageNumber, pageSize);
        });
    }

    public <T> Page<T> selectAndCount(String tableName, RowMapper<T> rowMapper, int pageNumber, int pageSize) {
        return selectAndCount(tableName, rowMapper, b -> {
            b.page(pageNumber, pageSize);
        });
    }

    public <T> Page<T> selectAndCount(String tableName, RowMapper<T> rowMapper, SelectCondition condition) {
        return selectAndCount(tableName, DEFAULT_ID_COLUMN, rowMapper, condition);
    }

    public <T> Page<T> selectAndCount(String tableName, String idColumn, RowMapper<T> rowMapper, SelectCondition condition, String... columns) {
        var selectBuilder = new DefaultSelectBuilder(tableName, columns);
        condition.condition(selectBuilder);

        int count = count(tableName, idColumn, condition);
        if (count == 0) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(selectBuilder.getPageNumber(), selectBuilder.getPageSize()), count);
        }

        List<T> result = select(tableName, rowMapper, condition, columns);
        return new PageImpl<>(result, PageRequest.of(selectBuilder.getPageNumber(), selectBuilder.getPageSize()), count);
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> int update(T entity) {
        String idColumn = getIdColumn(entity.getClass());
        Object idValue = null;
        List<Field> propertyFields = getPropertyFields(entity);
        Map<String, Object> updateValues = new HashMap<>();
        for (Field pf : propertyFields) {
            if (pf.isAnnotationPresent(Transient.class)) continue;

            String name = pf.getName();
            String property = BeanUtils.getProperty(entity, name);
            if (name.equals(idColumn)) {
                idValue = property;
            } else {
                updateValues.put(renameColumnNameByNamingStrategy(getColumnName(pf)), property);
            }
        }
        if (idValue == null) {
            throw new NullPointerException();
        }
        Object finalIdValue = idValue;
        return update(entity.getClass(), b -> b.where(idColumn, finalIdValue).set(updateValues));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> int update(Class<T> type, UpdateCondition condition) {
        return update(getTableName(type), condition);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int update(String tableName, UpdateCondition condition) {
        var updateBuilder = new DefaultUpdateBuilder(tableName);
        condition.condition(updateBuilder);
        Pair<String, Map<String, Object>> build = updateBuilder.build();
        return namedParameterJdbcTemplate.update(build.getLeft(), build.getRight());
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T saveOrUpdate(T entity) {
        String idColumn = getIdColumn(entity.getClass());
        String idValue = BeanUtils.getProperty(entity, idColumn);
        if (StringUtils.isNotBlank(idValue) && !"0".equals(idValue)) {
            int update = update(entity);
            if (update == 1) {
                return entity;
            } else {
                throw new Error("No such entity.");
            }
        } else {
            return save(entity);
        }
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T save(T entity) {
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(getTableName(entity.getClass()));
        sb.append(" (");
        var parameterSource = new BeanPropertySqlParameterSource(entity);
        var idName = "id";
        var columns = new ArrayList<String>();
        var valuePlaceholders = new ArrayList<String>();
        for (var propertyName : parameterSource.getReadablePropertyNames()) {
            var name = propertyName;
            var isId = false;
            Field declaredField = null;
            try {
                declaredField = getDeclaredField(entity.getClass(), propertyName);
            } catch (NoSuchFieldException e) {
                continue;
            }
            if (declaredField.isAnnotationPresent(Transient.class)) continue;

            if (declaredField.getDeclaredAnnotation(Id.class) != null) {
                isId = true;
            }
            Column columnAnnotation = declaredField.getDeclaredAnnotation(Column.class);
            var customName = columnAnnotation == null ? null : columnAnnotation.value();
            if (StringUtils.isNotEmpty(customName)) {
                name = customName;
            }
            if (isId) {
                idName = renameColumnNameByNamingStrategy(name);
            } else {
                columns.add(renameColumnNameByNamingStrategy(name));
                valuePlaceholders.add(":" + propertyName);
            }
        }
        sb.append(String.join(", ", columns));
        sb.append(") VALUES (");
        sb.append(String.join(", ", valuePlaceholders));
        sb.append(")");
        var sql = sb.toString();
        var keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder);

        var clone = BeanUtils.cloneBean(entity);
        BeanUtils.setProperty(clone, idName, keyHolder.getKey());
        return (T) clone;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public long save(Entity entity) {
        return entityDao.saveAndReturn(entity).getId();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> int delete(Class<T> type, Object idValue) {
        return delete(getTableName(type), getIdColumn(type), idValue);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> int delete(Class<T> type, DeleteCondition condition) {
        DefaultDeleteBuilder builder = new DefaultDeleteBuilder(getTableName(type));
        condition.condition(builder);
        Pair<String, Map<String, Object>> build = builder.build();
        return namedParameterJdbcTemplate.update(build.getLeft(), build.getRight());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int delete(String tableName, String idColumn, Object idValue) {
        return delete(tableName, b -> b.where(idColumn, idValue));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public int delete(String tableName, DeleteCondition condition) {
        DefaultDeleteBuilder builder = new DefaultDeleteBuilder(tableName);
        condition.condition(builder);
        Pair<String, Map<String, Object>> build = builder.build();
        return namedParameterJdbcTemplate.update(build.getLeft(), build.getRight());
    }

    @SneakyThrows
    public <T> String getIdColumn(Class<T> tClass) {
        for (var declaredField : tClass.getDeclaredFields()) {
            if (!declaredField.isAnnotationPresent(Id.class)) continue;

            Column columnAnnotation = declaredField.getDeclaredAnnotation(Column.class);
            var customName = columnAnnotation == null ? null : columnAnnotation.value();
            String name;
            if (StringUtils.isNotEmpty(customName)) {
                name = customName;
            } else {
                name = declaredField.getName();
            }
            return renameColumnNameByNamingStrategy(name);
        }
        Class<? super T> superclass = tClass.getSuperclass();
        if (superclass != null) {
            return getIdColumn(superclass);
        }
        throw new NoSuchFieldException("No @Id Annotation found of class " + tClass.getSimpleName());
    }

    public <T> String getTableName(Class<T> tClass) {
        var table = tClass.getDeclaredAnnotation(Table.class);
        if (table == null || StringUtils.isBlank(table.value())) {
            return ParsingUtils.reconcatenateCamelCase(tClass.getSimpleName(), "_");
        }
        return table.value();
    }

    private String getColumnName(Field field) {
        Column declaredAnnotation = field.getDeclaredAnnotation(Column.class);
        if (declaredAnnotation == null) {
            return field.getName();
        }
        String value = declaredAnnotation.value();
        if (StringUtils.isBlank(value)) {
            return field.getName();
        }
        return value;
    }

    private <T> List<Field> getPropertyFields(T t) {
        List<Field> fields = new ArrayList<>();
        val ps = new BeanPropertySqlParameterSource(t);
        for (String rp : ps.getReadablePropertyNames()) {
            try {
                val f = getDeclaredField(t.getClass(), rp);
                fields.add(f);
            } catch (NoSuchFieldException ignored) {
            }
        }
        return fields;
    }

    private Field getDeclaredField(Class<?> t, String name) throws NoSuchFieldException {
        Class<?> _t = t;
        while (true) {
            if (_t == Object.class) throw new NoSuchFieldException(name);
            try {
                return _t.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                _t = _t.getSuperclass();
            }
        }
    }

    public String renameColumnNameByNamingStrategy(String columnName) {
        return relationalMappingContext.getNamingStrategy().getColumnName(new RelationalPersistentProperty() {
            @Override
            public boolean isReference() {
                return false;
            }

            @Override
            public SqlIdentifier getColumnName() {
                return null;
            }

            @Override
            public RelationalPersistentEntity<?> getOwner() {
                return null;
            }

            @Override
            public SqlIdentifier getReverseColumnName(PersistentPropertyPathExtension path) {
                return null;
            }

            @Override
            public SqlIdentifier getKeyColumn() {
                return null;
            }

            @Override
            public boolean isQualified() {
                return false;
            }

            @Override
            public Class<?> getQualifierColumnType() {
                return null;
            }

            @Override
            public boolean isOrdered() {
                return false;
            }

            @Override
            public boolean shouldCreateEmptyEmbedded() {
                return false;
            }

            @Override
            public String getName() {
                return columnName;
            }

            @Override
            public Class<?> getType() {
                return null;
            }

            @Override
            public TypeInformation<?> getTypeInformation() {
                return null;
            }

            @Override
            public Iterable<? extends TypeInformation<?>> getPersistentEntityTypes() {
                return null;
            }

            @Override
            public Method getGetter() {
                return null;
            }

            @Override
            public Method getSetter() {
                return null;
            }

            @Override
            public Method getWither() {
                return null;
            }

            @Override
            public Field getField() {
                return null;
            }

            @Override
            public String getSpelExpression() {
                return null;
            }

            @Override
            public Association<RelationalPersistentProperty> getAssociation() {
                return null;
            }

            @Override
            public boolean isEntity() {
                return false;
            }

            @Override
            public boolean isIdProperty() {
                return false;
            }

            @Override
            public boolean isVersionProperty() {
                return false;
            }

            @Override
            public boolean isCollectionLike() {
                return false;
            }

            @Override
            public boolean isMap() {
                return false;
            }

            @Override
            public boolean isArray() {
                return false;
            }

            @Override
            public boolean isTransient() {
                return false;
            }

            @Override
            public boolean isWritable() {
                return false;
            }

            @Override
            public boolean isImmutable() {
                return false;
            }

            @Override
            public boolean isAssociation() {
                return false;
            }

            @Override
            public Class<?> getComponentType() {
                return null;
            }

            @Override
            public Class<?> getRawType() {
                return null;
            }

            @Override
            public Class<?> getMapValueType() {
                return null;
            }

            @Override
            public Class<?> getActualType() {
                return null;
            }

            @Override
            public <A extends Annotation> A findAnnotation(Class<A> annotationType) {
                return null;
            }

            @Override
            public <A extends Annotation> A findPropertyOrOwnerAnnotation(Class<A> annotationType) {
                return null;
            }

            @Override
            public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
                return false;
            }

            @Override
            public boolean usePropertyAccess() {
                return false;
            }

            @Override
            public Class<?> getAssociationTargetType() {
                return null;
            }
        });
    }

    public interface SelectCondition {
        void condition(SelectBuilder builder);
    }

    public interface DeleteCondition {
        void condition(DeleteBuilder builder);
    }

    public interface UpdateCondition {
        void condition(UpdateBuilder builder);
    }
}
