package online.lbprotocol.easy.jdbc.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author yichen for easy_project
 * @since 2021/9/14
 */
@Data
public class BaseModel {

    @Id
    private long id;
}
