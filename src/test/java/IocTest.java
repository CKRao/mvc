import com.clark.aop.Aop;
import com.clark.aop.advice.Advice;
import com.clark.core.BeanContainer;
import com.clark.ioc.Ioc;
import com.clark.test.DoodleAspect;
import com.clark.test.DoodleServiceImpl;
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

    @Test
    public void doAop() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.clark");
        new Aop().doAop();
        new Ioc().doIoc();
        DoodleController controller = (DoodleController) beanContainer.getBean(DoodleController.class);
        controller.hello();
        controller.helloForAspect();
        controller.testForAspect();
    }

    @Test
    public void doTest() {
        System.out.println("Advice >>isAssignableFrom>> DoodleController: "+Advice.class.isAssignableFrom(DoodleController.class));
        System.out.println("Advice >>isAssignableFrom>> DoodleAspect: "+Advice.class.isAssignableFrom(DoodleAspect.class));
        System.out.println("Advice >>isAssignableFrom>> DoodleServiceImpl: "+Advice.class.isAssignableFrom(DoodleServiceImpl.class));

    }
}
