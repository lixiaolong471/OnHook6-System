package com.mars.web.controller.system;

import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.annotation.logger.OpLog;
import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.Result;
import com.mars.model.system.SysConfigEntity;
import com.mars.service.system.ISysConfigService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lixl on 2017/7/15.
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController {

    private static final String DATA_KEY = "group";
    private static final String DATA_NODE = "child";

    @Autowired
    ISysConfigService sysConfigService;

    @RequestMapping("/list.do")
    @ResponseBody
    @RoleSecurity(roleMark = {"system.config","web.config","wap.config"})
    public Result list(@RequestBody SysConfigEntity condition){
        List<SysConfigEntity> list = sysConfigService.queryByExample(condition);
        return new DataResult(toTree(list));
    }

    private List<Map<String,Object>> toTree(List<SysConfigEntity>  list){
        List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
        for(SysConfigEntity entity:list){
            Map<String,Object> group = getNode(data,entity.getGroupindex());
            if(group != null){
                ((List)group.get(DATA_NODE)).add(entity);
            }else{
                group = new HashedMap();
                List<SysConfigEntity> child = new ArrayList<>();
                group.put(DATA_KEY,entity.getGroupindex());
                child.add(entity);
                group.put(DATA_NODE,child);
                data.add(group);
            }
        }
        return data;
    }

    private Map getNode(List<Map<String,Object>> data,String key){
        for(Map<String,Object> _dd:data){
            if(_dd.get(DATA_KEY).equals(key)){
                return _dd;
            }
        }
        return null;
    }

    @OpLog(value = "修改系统配置", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/update.do")
    @RoleSecurity(roleMark = {"system.config","wap.config","web.config"})
    @ResponseBody
    public Result update(@RequestBody SysConfigEntity entity){
        sysConfigService.update(entity);
        return Result.getOkResult();
    }

}
