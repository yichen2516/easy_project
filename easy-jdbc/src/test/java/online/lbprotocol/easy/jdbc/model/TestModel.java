package online.lbprotocol.easy.jdbc.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table("the_test_model")
@ToString
public class TestModel extends BaseModel{


    private String theString;
    private int theInteger;
    private float theFloat;
    private double theDouble;
    private BigDecimal theBigDecimal;

}
