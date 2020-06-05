package com._22evil.blog.util;

import com._22evil.blog.BlogApp;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

public enum  MailUtil {
    _MAIL;

    public MailUtil getInstance() {
        return _MAIL;
    }
    static JavaMailSenderImpl sender;
    static {
        sender = new JavaMailSenderImpl();
        sender.setHost(BlogApp.BLOG_CONFIG.mailHost());
        sender.setPort(25);
        sender.setUsername(BlogApp.BLOG_CONFIG.mailUser());
        sender.setPassword(BlogApp.BLOG_CONFIG.mailPwd());
        sender.setDefaultEncoding("utf-8");

        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);
    }

    public void sendMail(String msg) {
        try {
            MimeMessage mailMsg = sender.createMimeMessage();
            MimeMessageHelper msgHelper = new MimeMessageHelper(mailMsg, true, "UTF-8");
            msgHelper.setFrom(BlogApp.BLOG_CONFIG.mailUser(), "hanhna.com_反馈");
            msgHelper.setSubject("hanhna.com_反馈");
            msgHelper.setTo(BlogApp.BLOG_CONFIG.mailTo());
            msgHelper.setText(msg, true);
            sender.send(mailMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
