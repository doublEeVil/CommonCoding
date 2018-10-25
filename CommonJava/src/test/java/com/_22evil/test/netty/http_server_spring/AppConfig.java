package com._22evil.test.netty.http_server_spring;

/**
 * Created by shangguyi on 11/3/16.
 * config
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ImportResource({"classpath*:/template_spring.xml", "classpath*:/spring-hibernate.xml"})
public class AppConfig {
}
