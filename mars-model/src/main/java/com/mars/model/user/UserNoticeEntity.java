package com.mars.model.user;

import com.mars.model.RecordEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lixl on 2018/7/2 0002.
 */
@Entity
@Table(name = "user_notice")
public class UserNoticeEntity extends RecordEntity{

    private String name;

    private String content;

    private Integer type;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
