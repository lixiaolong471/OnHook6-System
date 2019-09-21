package com.mars.model.system;

import com.mars.core.bean.annotation.tree.Node;
import com.mars.core.bean.annotation.tree.Parent;
import com.mars.core.bean.annotation.tree.Tree;
import com.mars.model.RecordEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lixl on 2017/6/17.
 */
@Tree
@Entity
@Table(name = "sys_orga")
public class SysOrgaEntity extends RecordEntity {

    /** 机构名称 */
    @Node
    private String orgaName;

    /** 机构电话 */
    @Node
    private String tel;

    /** 机构地址 */
    @Node
    private String address;

    /** 父机构 */
    @Parent
    private SysOrgaEntity parent;

    /** 机构负责人 */
    @Node(name = "lead.name")
    private SysUserEntity lead;

    /**
     * 系统保留，系统保留的用户，不可删除
     */
    @Node
    private Boolean reserve;

    private List<SysUserEntity> userList;


    @Basic
    @Column(name = "orga_name")
    public String getOrgaName() {
        return orgaName;
    }

    public void setOrgaName(String orgaName) {
        this.orgaName = orgaName;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public SysOrgaEntity getParent() {
        return parent;
    }

    public void setParent(SysOrgaEntity parent) {
        this.parent = parent;
    }

    @ManyToOne
    @JoinColumn(name = "lead_id")
    public SysUserEntity getLead() {
        return lead;
    }

    public void setLead(SysUserEntity lead) {
        this.lead = lead;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column
    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    @ManyToMany(mappedBy = "orgas")
    public List<SysUserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUserEntity> userList) {
        this.userList = userList;
    }
}
