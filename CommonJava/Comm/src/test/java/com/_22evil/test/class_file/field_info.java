package com._22evil.test.class_file;
/**
 * 字段表
 */
public class field_info {
    public short            access_flags;       //访问标识
    public short            name_index;         //名称索引
    public short            descriptor_index;   //描述符索引
    public short            attributes_count;   //属性个数
    public attribute_info[] attributes;         //属性表的具体内容
}
