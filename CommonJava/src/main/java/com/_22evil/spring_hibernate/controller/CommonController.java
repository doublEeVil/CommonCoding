package com._22evil.spring_hibernate.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class CommonController {

    /**
     * @param data  内容
     * @return
     */
    private static final Logger LOGGER = Logger
            .getLogger(CommonController.class);

    // protected static Integer reqCount;

    public JSONObject successReturn(JSONObject data) {
        JSONObject object = new JSONObject();
        object.put("success", true);
        object.put("data", data);
        return object;
    }

    public JSONObject failReturn(Integer errorCode) {
        JSONObject object = new JSONObject();
        object.put("success", false);
        object.put("errorCode", errorCode);
        return object;
    }

    @ExceptionHandler
    public
    @ResponseBody
    JSONObject exp(HttpServletRequest request, Exception ex) {
        return null;
    }

    public boolean isNull(Object... objects) {
        for (Object o : objects) {
            if (o == null)
                return false;
        }
        return true;
    }

}
