package com._22evil.test.spark;

import org.junit.Test;

import static spark.Spark.*;
/**
 * 各种报错，实在是不清楚spark需要哪个javax.servlet版本
 *
 */
public class TestHelloWorld {

    //@Test
    public static void main(String[] args) throws Exception{
        port(8888);
        get("/abc", (req, resp) -> {
            resp.header("Access-Control-Allow-Origin", "*");
            //resp.redirect("/abc");
            //resp.redirect("/abc");
            return "{status: 12}";
        });
        //Thread.sleep(11111);
    }
}
