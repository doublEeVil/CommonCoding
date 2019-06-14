package com._22evil.blog.service.impl;

import com._22evil.blog.entity.EventLog;
import com._22evil.blog.service.IEventLogService;
import com._22evil.module_cache.mysql.service.GenericMySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventLogService implements IEventLogService {
    @Autowired
    private GenericMySqlService genericMySqlService;

    @Override
    public void add(EventLog log) {
        log.setCreateDate(System.currentTimeMillis());
        log.setUpdateDate(System.currentTimeMillis());
        genericMySqlService.save(log);
    }
}
