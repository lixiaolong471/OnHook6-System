package com.mars.model;

import com.mars.core.bean.annotation.tree.Node;
import com.mars.model.system.SysUserEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lixl on 2017/7/30.
 */
@MappedSuperclass
public class UpdateEntity extends BaseEntity{

    /**
     * 代表了本数据记录是否有效，或者是否可用
     */
    private Boolean available;

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
    @JoinColumn(name = "updater")
    public SysUserEntity getUpdater() {
        return updater;
    }

    public void setUpdater(SysUserEntity updater) {
        this.updater = updater;
    }

}
