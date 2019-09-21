package com.mars.web.controller.system;

import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.annotation.logger.OpLog;
import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.PageResult;
import com.mars.core.bean.param.Request;
import com.mars.core.bean.param.Result;
import com.mars.core.util.CollectionUtils;
import com.mars.core.util.EncryptionUtils;
import com.mars.core.util.ExcelUtils;
import com.mars.model.system.SysUserEntity;
import com.mars.param.system.SysPageUserRequest;
import com.mars.param.system.SysSaveUserRequest;
import com.mars.param.system.SysUserRequest;
import com.mars.service.SysConfigKey;
import com.mars.service.SysContent;
import com.mars.service.system.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by lixl on 2017/6/27.
 */
@Controller
@RequestMapping("/system/user/")
@Transactional
public class SysUserController {

    @Autowired
    ISysUserService userService;


    @RequestMapping("/page.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.userlist")
    public Result page(@RequestBody SysPageUserRequest request){
        Page<SysUserEntity> page = userService.findByCondtion(request);
        return new PageResult(page.getTotalElements(), CollectionUtils.getList(false,page.getContent(),"id","name","loginName","creator.name","position","passwdUpdateTime","lastLoginTime","createTime","available"));
    }

    @RequestMapping("/get/{id}.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.userlist")
    public Result get(@PathVariable Long id){
        SysUserEntity userEntity = userService.findOne(id);
        Map<String,Object> data = CollectionUtils.getMapByObject(false,
                userEntity,"id","name","loginName","tel","zjh","available","address","email","password","position","mobile");
        data.put("orgaIds",CollectionUtils.getProperty(new Long[]{},userEntity.getOrgas(),"id"));
        data.put("roleIds",CollectionUtils.getProperty(new Long[]{},userEntity.getRoles(),"id"));
        return new DataResult(data);
    }

    @OpLog(value = "保存用户信息", type = OpLog.Type.ADD, module = "系统管理")
    @RequestMapping("/save.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.userlist")
    public Result save(@RequestBody SysSaveUserRequest request){
    	SysUserEntity sysuser=userService.findUserByLoginName(request.getLoginName());
    	if(sysuser!=null){
    		if(request.getId()==null||request.getId().longValue()!=sysuser.getId().longValue()){
    			return Result.getPromptResult("抱歉,当前登录名称已经存在,请使用其他名称");
    		}
    	}
        userService.saveOrUpdate(request);
        return DataResult.getOkResult();
    }

    @OpLog(value = "初始化密码", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/initpwd/{id}.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.userlist")
    public Result initpwd(@PathVariable Long id){
        SysUserEntity user = userService.findOne(id);
        String passwd = EncryptionUtils.md5s(SysContent.getConfigValue(SysConfigKey.LOGIN.SYS_USER_LOGIN_INIT_PASSWORD));
        user.setPassword(passwd);
        userService.update(user);
        return DataResult.getOkResult();
    }


    @OpLog(value = "删除用户信息", type = OpLog.Type.DELETE, module = "系统管理")
    @RequestMapping("/delete/{ids}.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.userlist")
    public Result delete(@PathVariable String ids){
        userService.batchDelete(ids);
        return DataResult.getOkResult();
    }

    @OpLog(value = "禁用用户信息", type = OpLog.Type.DELETE, module = "系统管理")
    @RequestMapping("/disable/{id}.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.userlist")
    public Result disable(@PathVariable Long id){
        SysUserEntity entity = userService.findOne(id);
        if(entity != null){
            entity.setAvailable(!entity.getAvailable());
            userService.update(entity);
        }

        return DataResult.getOkResult();
    }

    @OpLog(value = "导出用户信息", type = OpLog.Type.QUERY, module = "系统管理")
    @RequestMapping("/export.do")
    @RoleSecurity(roleMark = "system.userlist")
    public void export(HttpServletRequest request, HttpServletResponse response){
        List<SysUserEntity> lst = userService.findByCondtion(new Request("createTime"));
        try {
            ExcelUtils.exportToResponse(lst,"用户列表",response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/loadManagerList.do")
    @ResponseBody
    public Result loadManagerList(@RequestBody SysPageUserRequest request){
        Page<SysUserEntity> page = userService.findByCondtion(request);
        return new DataResult(CollectionUtils.getList(false,page.getContent(),"id","name","position","jobNo"));
    }

    @RequestMapping("/tree.do")
    @ResponseBody
    public Result loadManagerTree(@RequestBody SysUserRequest request){
        return new DataResult(userService.getTree(request));
    }

}
