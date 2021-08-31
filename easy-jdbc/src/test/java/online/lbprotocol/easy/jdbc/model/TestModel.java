package online.lbprotocol.easy.jdbc.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
@Data
@Table("the_test_model")
@ToString
public class TestModel {

    @Id
    private long id;
    private String theString;
    private int theInteger;
    private float theFloat;
    private double theDouble;
    private BigDecimal theBigDecimal;

}
