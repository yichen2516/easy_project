package online.lbprotocol.easy.jdbc;

import lombok.val;
import online.lbprotocol.easy.jdbc.model.TestModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
@SpringBootTest
class EasyDaoTest {

    @Resource
    EasyDao easyDao;

    @Test
    public void select() {
        List<TestModel> select = easyDao.select(TestModel.class, b -> b
                .orderBy("the_integer", Sort.Direction.DESC)
                .orderDesc("id"));
        System.out.println(select);
    }

    @Test
    public void get() {
        val model = easyDao.get(TestModel.class, 1L);
        System.out.println(model);
    }

    @Test
    public void save() {
        TestModel testModel = new TestModel();
        testModel.setTheString("TEST_DATA_1");
        testModel.setTheInteger(11);
        testModel.setTheFloat(11.11F);
        testModel.setTheDouble(22.2222222D);
        testModel.setTheBigDecimal(new BigDecimal("333333.3333333333"));
        testModel = easyDao.save(testModel);

        System.out.println(testModel);
    }
}