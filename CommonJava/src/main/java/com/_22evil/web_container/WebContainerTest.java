package com._22evil.web_container;

import java.io.File;

public class WebContainerTest {

    public static void main(String[] args) throws Exception{
        System.out.println("----Web容器测试----");
        System.err.println(new File("template").exists());
        System.err.println("---" + new File("spring.xml").exists());

        new MyHttpServer(9000).launch();
    }
}