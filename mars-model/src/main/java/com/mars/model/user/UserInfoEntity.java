package com.mars.model.user;

import com.mars.model.RecordEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by lixl on 2018/6/28 0028.
 */
@Entity
@Table(name="user_info")
public class UserInfoEntity extends RecordEntity {

    /**
     * 性名
     */
    private String name;

    /**
     * 机器码
     */
    private String machineCode;

    /**
     * 序列号
     */
    private String serialKey;

    /**
     * 过期时间
     */
    private Timestamp expirationTime;

    /**
     * 最后一次使用时间
     */
    private Timestamp lastUseTime;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "machine_code")
    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    @Column(name = "serial_key")
    public String getSerialKey() {
        return serialKey;
    }

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey;
    }

    @Column(name = "expiration_time")
    public Timestamp getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Timestamp expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Column(name = "last_use_time")
    public Timestamp getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Timestamp lastUseTime) {
        this.lastUseTime = lastUseTime;
    }
}
