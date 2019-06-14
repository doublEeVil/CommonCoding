package com._22evil.blog.service;

import com._22evil.blog.entity.EventLog;

public interface IEventLogService {

    /**
     * 添加记录
     * @param log
     */
    void add(EventLog log);
}
