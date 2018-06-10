package com._22evil.spring_hibernate.entity;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangye on 2016/6/1.
 */
@Entity
//@Table(name = "player", catalog = "threeKingdoms")
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = -3682937297560966149L;
    private String id;
    private String name;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
