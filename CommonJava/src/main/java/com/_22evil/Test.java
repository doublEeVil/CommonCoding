package com._22evil;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        try {
            System.out.println(randomString(-229985452)+' '+randomString(-147909649));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String randomString(int seed) {
        Random rand = new Random(seed);
        StringBuilder sb = new StringBuilder();
        while(true) {
            int n = rand.nextInt(27);
            if (n == 0) break;
            sb.append((char) ('`' + n));
        }
        return sb.toString();
    }
}
