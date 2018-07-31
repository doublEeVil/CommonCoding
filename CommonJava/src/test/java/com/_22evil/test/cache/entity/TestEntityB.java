package com._22evil.test.cache.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com._22evil.module_cache.ehcache.CacheConfig;
import com._22evil.module_cache.mysql.BaseEntity;

@Entity
@Table(name = "tab_test_entity_b")
@CacheConfig
public class TestEntityB extends BaseEntity{
    private String name;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
