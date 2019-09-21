package com.mars.model.system;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixl on 2017/6/17.
 */
@Entity
@Table(name = "sys_config")
public class SysConfigEntity implements Serializable{
    private long id;
    private String configkey;
    private String configname;
    private String configvalue;
    private int type;
    private String value;
    private String groupindex;
    private String module;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "configkey")
    public String getConfigkey() {
        return configkey;
    }

    public void setConfigkey(String configkey) {
        this.configkey = configkey;
    }

    @Basic
    @Column(name = "configname")
    public String getConfigname() {
        return configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname;
    }

    @Basic
    @Column(name = "configvalue")
    public String getConfigvalue() {
        return configvalue;
    }

    public void setConfigvalue(String configvalue) {
        this.configvalue = configvalue;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "groupindex")
    public String getGroupindex() {
        return groupindex;
    }

    public void setGroupindex(String groupindex) {
        this.groupindex = groupindex;
    }

    @Column(name = "module")
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysConfigEntity that = (SysConfigEntity) o;

        if (id != that.id) return false;
        if (type != that.type) return false;
        if (configkey != null ? !configkey.equals(that.configkey) : that.configkey != null) return false;
        if (configname != null ? !configname.equals(that.configname) : that.configname != null) return false;
        if (configvalue != null ? !configvalue.equals(that.configvalue) : that.configvalue != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (groupindex != null ? !groupindex.equals(that.groupindex) : that.groupindex != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (configkey != null ? configkey.hashCode() : 0);
        result = 31 * result + (configname != null ? configname.hashCode() : 0);
        result = 31 * result + (configvalue != null ? configvalue.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (groupindex != null ? groupindex.hashCode() : 0);
        return result;
    }
}
