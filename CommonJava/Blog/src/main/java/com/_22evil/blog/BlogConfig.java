package com._22evil.blog;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
/**
 * 配置
 */
@Configuration
@ImportResource({"classpath*:application.xml"})
public class BlogConfig {
    private PropertiesConfiguration config;
    public BlogConfig() {

    }

    public void setConfig(PropertiesConfiguration config) {
        this.config = config;
    }

    public int port() {
        return config.getInt("port");
    }

    public String websiteName() {
        return config.getString("website_name");
    }

    public String websiteUrl() {
        return config.getString("website_url");
    }

    public String username() {
        return config.getString("username");
    }

    public String password() {
        return config.getString("password");
    }

    public String getUploadPicPath() {
        return config.getString("upload_pic_path");
    }

    public String mailHost() {
        return config.getString("mail_host");
    }

    public String mailUser() {
        return config.getString("mail_user");
    }

    public String mailPwd() {
        return config.getString("mail_pwd");
    }

    public String mailTo() {
        return config.getString("mail_to");
    }
}
