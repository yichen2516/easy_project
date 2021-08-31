package online.lbprotocol.easy.jdbc.context;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
@Getter
public class OrderContext {

    private List<String> asc;
    private List<String> desc;

    public List<String> getAscLazy() {
        if (asc == null) {
            asc = new ArrayList<>();
        }
        return asc;
    }

    public List<String> getDescLazy() {
        if (desc == null) {
            desc = new ArrayList<>();
        }
        return desc;
    }
}
