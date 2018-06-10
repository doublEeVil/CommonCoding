package com._22evil.web_container;

import org.springframework.beans.factory.annotation.Autowired;

public class WebContainerTest {
    
    public static void main(String[] args) throws Exception{
        System.out.println("----Web容器测试----");
        new MyHttpServer(9000).launch();
        
    }
}