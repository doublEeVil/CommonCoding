package com._22evil.mail;

public class MailConfig {
    public String host;                 // 格式 smtp.126.com
    public int port = 25;               // 一般都是25
    public String user;                 // 格式zhangsan@163.com
    public String pwd;                  // 密码
    public String charset = "utf-8";    // 字符集

    
    public String senderName;           // 自定义发送者姓名
    public String receiveAddr;          // 收件人地址 

    public String subject;            // 主题
    public String text;                 // 邮件正文
}