package com.mars.web.controller.system;
import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.annotation.logger.OpLog;
import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.Request;
import com.mars.core.bean.param.Result;
import com.mars.core.util.CollectionUtils;
import com.mars.model.system.SysAuthorityEntity;
import com.mars.model.system.SysRoleEntity;
import com.mars.service.system.ISysAuthorityService;
import com.mars.service.system.ISysRoleService;
import com.mars.param.system.SysSaveRoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by lixl on 2017/6/27.
 */
@Controller
@RequestMapping("/system/role")
@Transactional
public class SysRolerController {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysAuthorityService authorityService;

    @RequestMapping("/list.do")
    @ResponseBody
    public Result list(@RequestBody Request request){
        DataResult result = new DataResult();
        List<SysRoleEntity> list = roleService.findByCondtion(request);
        try {
            result.setData(CollectionUtils.getList(false,list,"id","roleName","reserve"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/get/{id}.do")
    @ResponseBody
    @RoleSecurity( roleMark = "system.userlist")
    public Result get(@PathVariable Long id){
        DataResult result = new DataResult();
        try {
            SysRoleEntity entity = roleService.findOne(id);
            List<SysAuthorityEntity> lst = entity.getAuthorityList();
            Map<String,Object> data = CollectionUtils.getMapByObject(false,entity,"id","roleName","remark");
            data.put("authoritys",CollectionUtils.getProperty(new Long[]{},lst,"id"));
            result.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @OpLog(value = "删除用户角色", type = OpLog.Type.DELETE, module = "系统管理")
    @RequestMapping("/delete/{id}.do")
    @ResponseBody
    @RoleSecurity( roleMark = "system.userlist")
    public Result delete(@PathVariable Long id){
        SysRoleEntity entity = roleService.findOne(id);
        if(!entity.getUserList().isEmpty()){
            return new Result(0,"存在用户数据，不能删除！");
        }else{
            roleService.delete(entity);
        }
        return Result.getOkResult();
    }

    @OpLog(value = "修改用户角色", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/save.do")
    @ResponseBody
    @RoleSecurity( roleMark = "system.userlist")
    public Result save(@RequestBody SysSaveRoleRequest role){
        roleService.saveOrUpdate(role);
        return Result.getOkResult();
    }

}
