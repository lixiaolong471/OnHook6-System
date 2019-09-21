package com.mars.model.system;
import com.mars.core.bean.annotation.excel.Excel;
import com.mars.model.RecordEntity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixl on 2017/6/17.
 */
@Entity
@Table(name = "sys_user")
public class SysUserEntity extends RecordEntity {

    /** 用户登录名*/
    @Excel
    private String loginName;

    /** 姓名*/
    @Excel
    private String name;

    /** 地址*/
    @Excel(width = 60)
    private String address;

    /** 邮箱*/
    @Excel(width = 40)
    private String email;

    /** 证件号码*/
    @Excel
    private String zjh;

    /** 电话号码*/
    @Excel
    private String tel;

    /** 手机号码 */
    @Excel
    private String mobile;

    /** 登录密码*/
    @Excel
    private String password;

    /** 头像图片url地址 */
    private String photoUrl;

    /** 职位*/
    @Excel
    private Integer position;

    /** 密码更新时间*/
    private Timestamp passwdUpdateTime;

    /** 最后一次国更新时间 */
    private Timestamp lastLoginTime;

    /** 最后一次登录ip */
    private String ip;

    /** 工号*/
    private String jobNo;

    /**
     * 系统保留，系统保留的用户，不可删除
     */
    private Boolean reserve;

    /** 用户角色列表 */
    private List<SysRoleEntity> roles = new ArrayList<>();

    /** 用户部门列表 */
    private List<SysOrgaEntity> orgas = new ArrayList<>();

    /** 权重 （注册用户分配策略之一 默认是0）*/
    private Integer weight;

    @Basic
    @Column(name = "login_name")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "zjh")
    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
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
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Basic
    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Basic
    @Column(name = "passwd_update_time")
    public Timestamp getPasswdUpdateTime() {
        return passwdUpdateTime;
    }

    public void setPasswdUpdateTime(Timestamp passwdUpdateTime) {
        this.passwdUpdateTime = passwdUpdateTime;
    }

    @Basic
    @Column(name = "last_login_time")
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "job_no")
    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Column
    public Boolean getReserve() {
        return reserve;
    }

    public void setReserve(Boolean reserve) {
        this.reserve = reserve;
    }

    @ManyToMany
    @JoinTable(name="sys_user_role",
            joinColumns={
                    @JoinColumn(name="user_id",referencedColumnName="id")
            },
            inverseJoinColumns={
                    @JoinColumn(name="role_id",referencedColumnName="id")
            }
    )
    public List<SysRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleEntity> roles) {
        this.roles = roles;
    }

    @ManyToMany
    @JoinTable(name="sys_user_orga",
            joinColumns={
                    @JoinColumn(name="user_id",referencedColumnName="id")
            },
            inverseJoinColumns={
                    @JoinColumn(name="orga_id",referencedColumnName="id")
            }
    )
    public List<SysOrgaEntity> getOrgas() {
        return orgas;
    }

    public void setOrgas(List<SysOrgaEntity> orgas) {
        this.orgas = orgas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SysUserEntity that = (SysUserEntity) o;

        if (loginName != null ? !loginName.equals(that.loginName) : that.loginName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (zjh != null ? !zjh.equals(that.zjh) : that.zjh != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (photoUrl != null ? !photoUrl.equals(that.photoUrl) : that.photoUrl != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (passwdUpdateTime != null ? !passwdUpdateTime.equals(that.passwdUpdateTime) : that.passwdUpdateTime != null)
            return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(that.lastLoginTime) : that.lastLoginTime != null)
            return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        return jobNo != null ? jobNo.equals(that.jobNo) : that.jobNo == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (zjh != null ? zjh.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (passwdUpdateTime != null ? passwdUpdateTime.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (jobNo != null ? jobNo.hashCode() : 0);
        return result;
    }
}
