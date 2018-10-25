package com._22evil.web_container;

/**
 * Created by shangguyi on 11/3/16.
 * config
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ImportResource({"classpath*:/template_spring.xml"})
public class AppConfig {
}
