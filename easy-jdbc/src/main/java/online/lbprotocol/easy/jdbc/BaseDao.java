package online.lbprotocol.easy.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;

@Slf4j
public abstract class BaseDao {
    public static String ORDER_BY_ID = "id";
    public static String ORDER_OPTION_ASC = "ASC";
    public static String ORDER_OPTION_DESC = "DESC";

    protected static int PAGE_SIZE_LARGE = 40;
    protected static int PAGE_SIZE_MEDIUM = 20;
    protected static int PAGE_SIZE_SMALL = 10;

    @Resource
    protected JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected <T> T queryForNullable(String sql, Object[] args, RowMapper<T> rowMapper) {
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, args);

        } catch (Throwable t) {
            log.error("Failed to get an object!", t);
        }

        return null;
    }

}
