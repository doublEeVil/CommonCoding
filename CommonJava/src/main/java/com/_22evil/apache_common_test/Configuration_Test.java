package com._22evil.apache_common_test;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * apache.commons.configuration
 */
public class Configuration_Test {

    public static void test_Properties() {
        try {
            PropertiesConfiguration pg = new PropertiesConfiguration("F:\\CommonCoding\\CommonJava\\target\\classes\\database.properties");
            System.out.println(pg.getProperty("url"));
            pg.setProperty("name", "zjs");
            // 如果原格式包含/之类的特殊符号，save保存会存在问题
            pg.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test_Xml() {
        try {
            XMLConfiguration xc = new XMLConfiguration("F:\\CommonCoding\\CommonJava\\src\\main\\resources\\log4j2.xml");
            System.out.println(xc.getRoot().getName());
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception{
        // test_Properties();
        test_Xml();
    }
}
