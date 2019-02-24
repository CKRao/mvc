import com.clark.core.BeanContainer;
import com.clark.ioc.Ioc;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import com.clark.test.DoodleController;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/24 13:32
 * @Description:
 */
@Slf4j
public class IocTest {

    @Test
    public void doIoc() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.clark.test");
        new Ioc().doIoc();
        DoodleController controller = (DoodleController) beanContainer.getBean(DoodleController.class);
        controller.hello();
    }
}
