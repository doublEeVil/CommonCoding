package com._22evil.protobuf_test;

import java.io.FileInputStream;
import java.io.InputStream;

public class ReadMsgTest {
    public static void main(String[] args) throws Exception{
        InputStream in = new FileInputStream("G:\\Public\\abc.data");
        AddressBookProtos.Person person = AddressBookProtos.Person.parseFrom(in);
        System.out.println(person.getName());
    }
}
