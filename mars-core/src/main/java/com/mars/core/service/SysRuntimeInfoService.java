package com.mars.core.service;

import com.mars.core.bean.runtime.RuntimeInfo;
import org.hyperic.sigar.Sigar;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by lixl on 2017/7/24.
 */
@Service
public class SysRuntimeInfoService {
	
	private static boolean isInit=false;

    private static final int DATA_LENGTH = 100;

    LinkedList<RuntimeInfo> dataList = new LinkedList<>();


    @PostConstruct
    public void init(){
    	if(isInit){
    		return;
    	}else{
    		isInit=true;
    	}
        final SysRuntimeInfoService superObject = this;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                superObject.run();
            }
        };
        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 60 * 1000;
        System.out.println("启动资源利用率计算程序" + new Date());
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }
    public  void run(){
        try{
//            System.out.println("刷新资源利用情况信息 - " + new Date());
            Sigar sigar = new Sigar();
            RuntimeInfo data = new RuntimeInfo();
            data.setCpu(sigar.getCpu());
            data.setMem(sigar.getMem());
            dataList.offer(data);
            if(dataList.size() > DATA_LENGTH){
                dataList.poll();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<RuntimeInfo> getDatalist(){
        return dataList;
    }

    public RuntimeInfo getData(){
        return dataList.peek();
    }

}
