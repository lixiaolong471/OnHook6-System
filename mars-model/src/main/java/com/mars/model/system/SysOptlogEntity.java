package com.mars.model.system;

import com.mars.model.LogEntity;
import com.mars.model.RecordEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lixl on 2017/6/17.
 */
@Entity
@Table(name = "sys_optlog")
public class SysOptlogEntity extends LogEntity {

    private String browserVersion;

    private String ip;

    private String module;

    private String optType;

    @Basic
    @Column(name = "browser_version")
    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }


    @Basic
    @Column(name = "module")
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Basic
    @Column(name = "opt_type")
    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SysOptlogEntity that = (SysOptlogEntity) o;

        if (browserVersion != null ? !browserVersion.equals(that.browserVersion) : that.browserVersion != null)
            return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (module != null ? !module.equals(that.module) : that.module != null) return false;
        return optType != null ? optType.equals(that.optType) : that.optType == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (browserVersion != null ? browserVersion.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (module != null ? module.hashCode() : 0);
        result = 31 * result + (optType != null ? optType.hashCode() : 0);
        return result;
    }
}
