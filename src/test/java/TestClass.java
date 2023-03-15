import com.myspring.beans.BeansException;
import com.myspring.context.ClassPathXmlApplicationContext;
import com.myspring.test.AService;

public class TestClass {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("mybean.xml");
        AService aservice = (AService) ctx.getBean("aservice");
        aservice.sayHello();
    }
}
