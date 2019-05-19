package com._22evil.test.class_file;
/**
 * class file 的具体结构
 * u1 代表1个字节 byte
 * u2 代表2个字节 short
 * u3 代表3个字节 不存在
 * u4 代表4个字节 int
 */
public class ClassFile {
    public int              magic;                  //魔数，固定值0xCAFEBABE
    public short            minor_version;          //次版本号
    public short            major_version;          //主版本号
    public short            constant_pool_count;    //常量的个数, 如果为0，表示没有用常量池
    public cp_info[]        constant_pool;          //具体的常量池内容, 长度为常量的个数-1
    public short            access_flags;           //访问标识
    public short            this_class;             //当前类索引
    public short            super_class;            //父类索引
    public short            interfaces_count;       //接口的个数
    public short[]          interfaces;             //具体的接口内容
    public short            fields_count;           //字段的个数
    public field_info[]     fields;                 //具体的字段内容
    public short            methods_count;          //方法的个数
    public method_info[]    methods;                //具体的方法内容
    public short            attributes_count;       //属性的个数
    public attribute_info[] attributes;             //具体的属性内容
}

