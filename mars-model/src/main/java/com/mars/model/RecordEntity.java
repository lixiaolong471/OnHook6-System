package com.mars.model;

import com.mars.core.bean.annotation.tree.Node;
import com.mars.model.system.SysUserEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lixl on 2017/7/21.
 */
@MappedSuperclass
public class RecordEntity extends BaseEntity {

    /**
     * 代表了本数据记录是否有效，或者是否可用
     */
    @Node
    private Boolean available;

    /**
     * 数据的创建者
     */
    @Node(name = "creator.name")
    private SysUserEntity creator;

    /**
     * 数据创建时间
     */
    @Node
    private Timestamp createTime;

    /**
     * 数据的更新者
     */
    @Node(name = "updater.name")
    private SysUserEntity updater;

    /**
     * 数据的更新时间
     */
    @Node(name = "updateTime")
    private Timestamp updateTime;


    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }


    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "available")
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }


    @ManyToOne
    @JoinColumn(name = "creator")
    public SysUserEntity getCreator() {
        return creator;
    }

    public void setCreator(SysUserEntity creator) {
        this.creator = creator;
    }

    @ManyToOne
    @JoinColumn(name = "updater")
    public SysUserEntity getUpdater() {
        return updater;
    }

    public void setUpdater(SysUserEntity updater) {
        this.updater = updater;
    }
}
