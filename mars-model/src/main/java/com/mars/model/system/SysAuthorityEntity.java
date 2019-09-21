package com.mars.model.system;

import com.mars.core.bean.annotation.tree.Node;
import com.mars.core.bean.annotation.tree.Parent;
import com.mars.core.bean.annotation.tree.Tree;
import com.mars.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lixl on 2017/6/17.
 */
@Tree
@Entity
@Table(name = "sys_authority")
public class SysAuthorityEntity extends BaseEntity {

    @Node
    private String authorityMark;

    @Node
    private String authorityName;

    private boolean available;

    @Node
    private String icon;

    private Integer indexnum;

    @Parent
    private SysAuthorityEntity parent;

    private List<SysRoleEntity> roleList;


    @Basic
    @Column(name = "authority_mark")
    public String getAuthorityMark() {
        return authorityMark;
    }

    public void setAuthorityMark(String authorityMark) {
        this.authorityMark = authorityMark;
    }

    @Basic
    @Column(name = "authority_name")
    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Basic
    @Column(name = "available")
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "indexnum")
    public Integer getIndexnum() {
        return indexnum;
    }

    public void setIndexnum(Integer indexnum) {
        this.indexnum = indexnum;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public SysAuthorityEntity getParent() {
        return parent;
    }

    public void setParent(SysAuthorityEntity parent) {
        this.parent = parent;
    }

    @ManyToMany(mappedBy = "authorityList")
    public List<SysRoleEntity> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRoleEntity> roleList) {
        this.roleList = roleList;
    }


}
