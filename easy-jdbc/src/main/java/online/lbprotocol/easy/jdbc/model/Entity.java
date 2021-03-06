package online.lbprotocol.easy.jdbc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author mixueqiang
 * @since Mar 1, 2014
 */
public class Entity extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 97627540484496507L;

    protected String modelName;
    protected List<Entity> children;

    public Entity() {
        super();
    }

    public Entity(String modelName) {
        super();
        this.modelName = modelName;
    }

    public BigDecimal getBigDecimal(String key) {
        Object value = get(key);
        if (value == null) {
            return new BigDecimal("0");
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof String) {
            return new BigDecimal((String) value);
        }
        if (value instanceof Number) {
            return new BigDecimal(((Number) value).doubleValue());
        }
        return new BigDecimal("0");
    }

    public boolean getBoolean(String key) {
        return Boolean.valueOf(getString(key));
    }

    public List<Entity> getChildren() {
        if (children == null) {
            children = new ArrayList<Entity>();
        }

        return children;
    }

    public void setChildren(List<Entity> children) {
        this.children = children;
    }

    public Float getFloat(String key) {
        Object value = get(key);
        if (value == null) {
            return 0.0F;
        }

        if (value instanceof String) {
            return Float.valueOf((String) value);
        }
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        return 0.0F;
    }

    public long getId() {
        return getLong("id");
    }

    public long getGuid() {
        return getLong("guid");
    }

    public int getInt(String key) {
        Object value = get(key);
        if (value == null) {
            return 0;
        }

        if (value instanceof String) {
            return Integer.valueOf((String) value);
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }

    public long getLong(String key) {
        Object value = get(key);
        if (value == null) {
            return 0L;
        }

        if (value instanceof String) {
            return Long.valueOf((String) value);
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        return 0L;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getString(String key) {
        Object value = get(key);
        if (value == null) {
            return null;
        }

        return value + "";
    }

    public Entity set(String key, Object value) {
        put(key, value);
        return this;
    }

}
