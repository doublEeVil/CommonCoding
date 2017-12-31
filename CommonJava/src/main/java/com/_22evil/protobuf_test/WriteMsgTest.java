package com._22evil.protobuf_test;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class WriteMsgTest {

    public static void main(String[] args) throws Exception{
        AddressBookProtos.Person.Builder person = AddressBookProtos.Person.newBuilder();
        person.setId(0123);
        person.setName("zhujinshan");
        person.setEmail("123@q.com");

        OutputStream out = new FileOutputStream("G:\\Public\\abc.data");
        person.build().writeTo(out);
        out.close();
    }
}
