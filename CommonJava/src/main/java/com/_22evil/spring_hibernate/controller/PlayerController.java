package com._22evil.spring_hibernate.controller;

import com.alibaba.fastjson.JSONObject;
import com._22evil.spring_hibernate.dao.PlayerDao;
import com._22evil.spring_hibernate.entity.*;
import com._22evil.spring_hibernate.service.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;



@Controller
//@RequestMapping({"/test/player","/player"})
@RequestMapping({"/player"})
public class PlayerController extends CommonController {
    private static final Logger LOGGER = Logger
            .getLogger(PlayerController.class);

    @Autowired
    public PlayerService playerService;

    /**
     * 注册一个用户
     *
     * @return 用户playerID
     */
    @RequestMapping(value = "/newuser")
    public
    @ResponseBody
    JSONObject insertNewUser(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        playerService.addPlayer();
        return successReturn(jsonObject);

    }




}