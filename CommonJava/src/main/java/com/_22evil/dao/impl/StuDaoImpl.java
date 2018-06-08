package com._22evil.dao.impl;

import com._22evil.dao.StuDao;
import com._22evil.dao.entity.Stu;

import org.springframework.stereotype.Repository;

@Repository("stuDao")
public class StuDaoImpl extends GenericDaoImpl<Stu, String> implements StuDao {

} 