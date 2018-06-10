package com._22evil.spring_hibernate.dao.impl;

import com._22evil.spring_hibernate.dao.PlayerDao;
import com._22evil.spring_hibernate.entity.Player;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by wangye on 2016/6/1.
 */
@Repository("playerDao")
public class PlayerDaoImpl extends GenericDaoImpl<Player, String> implements PlayerDao {

}
