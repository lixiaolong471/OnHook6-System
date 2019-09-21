package com.mars.web.controller.system;
import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.Request;
import com.mars.core.bean.param.Result;
import com.mars.core.util.CollectionUtils;
import com.mars.model.system.SysAuthorityEntity;
import com.mars.param.system.SysListAuthorityRequest;
import com.mars.service.SysContent;
import com.mars.service.system.ISysAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

/**
 * Created by lixl on 2017/7/17.
 */
@Controller
@RequestMapping("/system/authority")
@Transactional
public class SysAuthorityController {

    @Autowired
    private ISysAuthorityService authorityService;

    @RequestMapping("/menu.do")
    @ResponseBody
    public Result menu(@RequestBody SysListAuthorityRequest request){
        DataResult result = new DataResult();
        try {
            Long[] ids = CollectionUtils.getProperty(new Long[]{},SysContent.getLoginUser().getRoles(),"id");
            request.setRoles(ids);
            request.setAvailable(true);
            List<SysAuthorityEntity> list = authorityService.findByCondtion(request);
            List<Map<String,Object>> treeMap = CollectionUtils.getTree(list);
            result.setData(treeMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Result list(){
        DataResult result = new DataResult();
        try {
            SysAuthorityEntity example = new SysAuthorityEntity();
            example.setAvailable(true);
            List<SysAuthorityEntity> list = authorityService.queryByExample(example);
            List<Map<String,Object>> treeMap = CollectionUtils.getTree(list);
            result.setData(treeMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
