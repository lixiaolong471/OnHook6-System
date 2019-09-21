package com.mars.service;

import com.mars.core.util.SpringUtils;
import com.mars.model.system.SysOptlogEntity;
import com.mars.service.system.ISysOptlogService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixl on 2017/7/4.
 */
public class ThreadPoolManager {

    ExecutorService threadPool = Executors.newFixedThreadPool(20);

    private static ThreadPoolManager install = new ThreadPoolManager();

    public static ThreadPoolManager getInstall() {
        return install;
    }

    public void addOpLog(final SysOptlogEntity optlogEntity) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                ISysOptlogService sysOptlogService = SpringUtils.getBean(ISysOptlogService.class);
                sysOptlogService.save(optlogEntity);
            }
        });
    }
}
