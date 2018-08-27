package com._22evil.test.spark;

import org.junit.Test;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * 各种报错，实在是不清楚spark需要哪个javax.servlet版本
 *
 */
public class TestHelloWorld {

    //@Test
    public static void main(String[] args) throws Exception{
        port(8888);
        get("/", (req, resp) -> {
            resp.body("dd");
            return "hello world...";
        });
        //Thread.sleep(11111);
    }
}
