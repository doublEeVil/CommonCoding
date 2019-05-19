package com._22evil.test.cache.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com._22evil.module_cache.ehcache.CacheConfig;
import com._22evil.module_cache.mysql.BaseConfigEntity;

@Table
@Entity
@CacheConfig
public class TestConfigEntityA extends BaseConfigEntity {
    private String addr;

    @Column(name = "addr")
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
