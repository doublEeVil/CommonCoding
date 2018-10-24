package com._22evil.test.util;
import com._22evil.util.FileUtil;
import com._22evil.util.MarkdownUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
public class TestMarkdownUtil {

    @Test
    public void test() throws Exception{
        byte[] data = FileUtil.fileToByte(new File("C:\\Users\\Administrator\\Desktop\\temp\\pednson.md"));
        String input = new String(data);
        String output = MarkdownUtil.md2Html(input);
        System.out.println(output);
    }
}
