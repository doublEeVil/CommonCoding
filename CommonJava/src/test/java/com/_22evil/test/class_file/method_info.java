package com._22evil.test.class_file;
/**
 * 方法表
 */
public class method_info {
    public short            ccess_flags;        //访问标识
    public short            name_index;         //名称索引
    public short            descriptor_index;   //描述符索引
    public short            attributes_count;   //属性个数
    public attribute_info[] attributes;         //属性表的具体内容
}
