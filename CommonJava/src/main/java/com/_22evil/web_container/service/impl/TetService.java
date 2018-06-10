package com._22evil.web_container.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com._22evil.dao.StuDao;
import com._22evil.dao.entity.Stu;
import com._22evil.dao.impl.StuDaoImpl;
import com._22evil.web_container.service.ITetService;

@Service("tetService")
public class TetService implements ITetService {
    
    @Autowired
    private StuDao stuDao;

    public void test() {
        if (stuDao == null) {
            stuDao = new StuDaoImpl();
        }
        stuDao.save(new Stu());
        System.out.println("--" + stuDao);
    }
}