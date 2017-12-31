package com._22evil.netty_test.http_server;

/**
 * Created by shangguyi on 11/3/16.
 * config
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ImportResource({"classpath*:/spring.xml"})
public class AppConfig {
}
