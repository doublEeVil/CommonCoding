package com._22evil.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件工具类
 */
public class MailUtil {
    public static void sendMail(MailConfig conf) throws MessagingException, UnsupportedEncodingException {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(conf.host);
        sender.setPort(conf.port);
        sender.setUsername(conf.user);
        sender.setPassword(conf.pwd);
        sender.setDefaultEncoding(conf.charset);
        
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "false");
        sender.setJavaMailProperties(p);

        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper msgHelper = new MimeMessageHelper(msg, true, "UTF-8");
        msgHelper.setFrom(conf.user, conf.senderName);
        msgHelper.setSubject(conf.subject);
        msgHelper.setTo(conf.receiveAddr);
        msgHelper.setText(conf.text, true);
        sender.send(msg);
    }
}