import org.junit.Test;

public class BasicT {

    @Test
    public void test() throws Exception{
        String  s = "补发";
        System.out.println(new String(s.getBytes(), "gbk"));
    }

    @Test
    public void test2() {
        java.sql.Timestamp date = new java.sql.Timestamp(System.currentTimeMillis());
        System.out.println(date);
    }


}
