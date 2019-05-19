package com._22evil.test.util;

import com._22evil.util.PrintUtil;
import org.junit.Test;

public class TestPrintUtil {

    @Test
    public void testHex2Bytes() {
        String s = "82";
        PrintUtil.printHex2Bytes(s);
    }
}
