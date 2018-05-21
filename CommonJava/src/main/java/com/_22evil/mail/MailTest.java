package com._22evil.mail;

/**
 * 发送邮件测试
 */
public class MailTest {

    public static void main(String[] args) {
        System.out.println("====发送邮件测试====");
        MailConfig config = new MailConfig();
        config.host = "smtp.126.com";
        config.port = 25;
        config.user = "abc@126.com";
        config.pwd = "123456";

        config.senderName = "自定义发件人姓名";
        config.receiveAddr = "984370133@qq.com";
        config.subject = "自定义主题";
        config.text = "这个是测试内容";

        try {
            MailUtil.sendMail(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}