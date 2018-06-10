package com._22evil.spring_hibernate.service.impl;


import com._22evil.spring_hibernate.dao.*;
import com._22evil.spring_hibernate.entity.*;
import com._22evil.spring_hibernate.service.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("playerService")
public class PlayerServiceImpl implements PlayerService {
    private static final Logger LOGGER = Logger
            .getLogger(PlayerServiceImpl.class);
    @Autowired
    private PlayerDao playerDao;

    public void addPlayer() {
        System.err.println("-----");
        Player player = new Player();
        player.setId("89000");
        player.setName("zhujisn");
        System.out.println("====");
        String p = playerDao.save(player);
        System.out.println(playerDao);
        //playerDao.flush();
        System.out.println("---++"+player.getId());
        System.err.println("======");
    }
}
