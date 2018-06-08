package com._22evil.web_container;

import java.io.File;
import com._22evil.dao.StuDao;
import com._22evil.dao.entity.Stu;

import org.springframework.beans.factory.annotation.Autowired;

public class WebContainerTest {
    @Autowired
    private StuDao stuDao;

    public static void main(String[] args) throws Exception{
        System.out.println("----Web容器测试----");
        System.err.println(new File("template").exists());
        System.err.println("---" + new File("spring.xml").exists());

        new WebContainerTest().test();

        new MyHttpServer(9000).launch();
        
    }

    public void test() {
        Stu stu = new Stu();
        stu.setName("js");
        stuDao.save(stu);
    }
}