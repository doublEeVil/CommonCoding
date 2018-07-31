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
        } catch (Exception e) {
            
        }
    }
}