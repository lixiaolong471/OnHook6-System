package com.mars.web.controller.system;

import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.annotation.logger.OpLog;
import com.mars.core.bean.param.*;
import com.mars.model.system.SysCodeEntity;
import com.mars.param.system.SysListCodeRequest;
import com.mars.param.system.SysPageCodeRequest;
import com.mars.service.system.ISysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lixl on 2017/7/26.
 */
@Controller
@RequestMapping("/system/code")
public class SysCodeController {

    @Autowired
    private ISysCodeService codeService;

    @RequestMapping("/page.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.code")
    public Result page(@RequestBody SysPageCodeRequest request){
        Page<SysCodeEntity> page = codeService.findByCondtion(request);
        return new PageResult(page.getTotalElements(),page.getContent());
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Result list(@RequestBody SysListCodeRequest request){
        List<SysCodeEntity> list = codeService.findByCondtion(request);
        return new DataResult(list);
    }

    @OpLog(value = "更新系统编码信息", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/update.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.code")
    public Result update(@RequestBody SysCodeEntity entity){
        codeService.update(entity);
        return Result.getOkResult();
    }


    @RequestMapping("/list/{type}.do")
    @ResponseBody
    public Result select(@PathVariable String type){
        return new DataResult(codeService.findByCode(type));
    }

    @RequestMapping("/find.do")
    @ResponseBody
    public Result select(@RequestBody SysCodeEntity entity){
        return new DataResult(codeService.queryByExampleFirst(entity));
    }

}
