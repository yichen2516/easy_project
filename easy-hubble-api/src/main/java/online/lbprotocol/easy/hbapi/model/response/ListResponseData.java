package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ListResponseData<RECORD_TYPE> {
    private int total;
    private int current;
    private boolean hitCount;
    private int pages;
    private int size;
    private boolean optimizeCountSql;
    private List<RECORD_TYPE> records;
    private boolean searchCount;
    private List<Order> orders;
}