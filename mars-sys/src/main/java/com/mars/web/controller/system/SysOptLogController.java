package com.mars.web.controller.system;

import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.param.PageResult;
import com.mars.core.bean.param.Result;
import com.mars.core.util.CollectionUtils;
import com.mars.model.system.SysOptlogEntity;
import com.mars.service.system.ISysOptlogService;
import com.mars.param.system.SysPageOptlogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixl on 2017/7/22.
 */
@Controller
@RequestMapping("/system/optlog")
public class SysOptLogController {


    @Autowired
    ISysOptlogService optlogService;

    @RequestMapping("/page.do")
    @ResponseBody
    @RoleSecurity(roleMark = "system.logger")
    public Result page(@RequestBody SysPageOptlogRequest request){
        Page<SysOptlogEntity> page = optlogService.findByCondtion(request);
        PageResult result = new PageResult(page.getTotalElements(),
                CollectionUtils.getList(false,page.getContent(),
                        "id","browserVersion","module","optType","remark","ip","creator.name","createTime"));
        return result;
    }

}
