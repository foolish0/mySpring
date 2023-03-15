import com.myspring.ClassPathXmlApplicationContext;
import com.myspring.test.AService;

public class TestClass {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("mybean.xml");
        AService aservice = (AService) ctx.getBean("aservice");
        aservice.sayHello();
    }
}
