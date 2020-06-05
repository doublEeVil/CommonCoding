package com._22evil.blog.controller.api;

import com._22evil.blog.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class ApiFeekbackRouter implements Route {

    private static final Logger logger = LoggerFactory.getLogger("MailLog");

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.queryParams("name");
        String email = request.queryParams("email");
        String msg = request.queryParams("msg");
        String mailMsg = "user: " + name + " email:" + email + " msg:" + msg;
        logger.info(mailMsg);
        MailUtil._MAIL.sendMail(mailMsg);
        return null;
    }
}
