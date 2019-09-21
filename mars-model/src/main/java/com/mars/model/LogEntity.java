package com.mars.model;
import com.mars.model.system.SysUserEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lixl on 2017/7/23.
 */
@MappedSuperclass
public class LogEntity extends BaseEntity {

    /**
     * 数据的创建者
     */
    private SysUserEntity creator;

    /**
     * 数据创建时间
     */
    private Timestamp createTime;

    @ManyToOne
    @JoinColumn(name = "creator")
    public SysUserEntity getCreator() {
        return creator;
    }

    public void setCreator(SysUserEntity creator) {
        this.creator = creator;
    }
    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
