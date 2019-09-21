package com.mars.model.system;

import com.mars.core.bean.annotation.tree.Node;
import com.mars.core.bean.annotation.tree.Parent;
import com.mars.core.bean.annotation.tree.Tree;
import com.mars.model.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixl on 2017/6/17.
 */
@Tree
@Entity
@Table(name = "sys_code")
public class SysCodeEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8416372223841571887L;

	/** 编码 */
    @Node
    private String code;

    /** 名称 */
    @Node
    private String name;

    /** 值*/
    @Node
    private Integer value;

    /** 顺序值*/
    @Node
    private Integer indexnum;

    @Node
    private String groupName;

    private String type;

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "indexnum")
    public Integer getIndexnum() {
        return indexnum;
    }

    public void setIndexnum(Integer indexnum) {
        this.indexnum = indexnum;
    }

    @Basic
    @Column(name = "value")
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Column
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SysCodeEntity that = (SysCodeEntity) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return indexnum != null ? indexnum.equals(that.indexnum) : that.indexnum == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (indexnum != null ? indexnum.hashCode() : 0);
        return result;
    }
}
