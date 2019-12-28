package com._22evil.test.sock5;

public class Common {
    // 协议类型
    public static byte SOCK_VERSION = 0x05;

    // 命令类型
    public static byte CMD_CONNECT = 0x01;
    public static byte CMD_BIND = 0x02;
    public static byte CMD_UDP = 0x03;

    // 地址类型
    public static byte ADDR_TYPE_IPV4 = 0x01;
    public static byte ADDR_TYPE_DOMAINNAME = 0x03;
    public static byte ADDR_TYPE_IPV6 = 0x04;

    // 响应状态码
    public static byte RESP_SUCCESS = 0x00;
    public static byte RESP_NETWORK_UNREACH = 0x03;

    // 保留字
    public static byte RSV = 0x00;
}
