package com._22evil.test.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com._22evil.module_cache.ehcache.CacheConfig;
import com._22evil.module_cache.mysql.BaseEntity;

@Entity
@Table(name = "tab_test_entity_a")
@CacheConfig
public class TestEntityA extends BaseEntity{
    private int age;

    @Column(name = "age")
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
