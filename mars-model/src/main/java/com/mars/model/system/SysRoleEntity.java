package com.mars.model.system;

import com.mars.model.RecordEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lixl on 2017/6/17.
 */
@Entity
@Table(name = "sys_role")
public class SysRoleEntity extends RecordEntity {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 系统保留，系统保留的用户，不可删除
     */
    private Boolean reserve;
    /**
     * 角色权限列表
     */
    private List<SysAuthorityEntity> authorityList;

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column
    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    @ManyToMany
    @JoinTable(name="sys_role_authority",
            joinColumns={
                    @JoinColumn(name="role_id",referencedColumnName="id")
            },
            inverseJoinColumns={
                    @JoinColumn(name="authority_id",referencedColumnName="id")
            }
    )
    public List<SysAuthorityEntity> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<SysAuthorityEntity> authorityList) {
        this.authorityList = authorityList;
    }

    public List<SysUserEntity> userList;

    @ManyToMany(mappedBy = "roles")
    public List<SysUserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUserEntity> userList) {
        this.userList = userList;
    }
}
