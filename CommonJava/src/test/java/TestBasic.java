import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TestBasic {
    @Test
    public void test1() {
        System.out.println("====");
        try {
            Thread.sleep(120);
            CA ca = new CB();
            ca.say();
        } catch (Exception e) {
            
        }
    }
}

class CA {
    public void say() {
        System.out.println("this is ca");
    }
}

class CB extends CA {
    public void say() {
        System.out.println("this is cb");
    }
}