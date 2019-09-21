package com.mars.core.bean.runtime;

import com.mars.core.util.DateUtils;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;

import java.sql.Timestamp;

/**
 * Created by lixl on 2017/7/24.
 */
public class RuntimeInfo {
    Mem mem = new Mem();

    Cpu cpu = new Cpu();

    private Timestamp time = DateUtils.getDatetime();

    public Mem getMem() {
        return mem;
    }

    public void setMem(Mem mem) {
        this.mem = mem;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
