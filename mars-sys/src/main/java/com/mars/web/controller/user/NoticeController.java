package com.mars.web.controller.user;

import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.Result;
import com.mars.core.util.CollectionUtils;
import com.mars.core.util.DateUtils;
import com.mars.model.user.UserNoticeEntity;
import com.mars.param.user.UserSaveNoticeRequest;
import com.mars.service.SysContent;
import com.mars.service.user.IUserNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixl on 2018/7/2 0002.
 */
@Controller
@RequestMapping("/user/notice")
public class NoticeController {

    @Autowired
    IUserNoticeService userNoticeService;

    @RequestMapping("/get/{id}.g")
    @ResponseBody
    public Result get(@PathVariable Long id){
        UserNoticeEntity entity = userNoticeService.findOne(id);
        DataResult result = new DataResult();
        result.setData(CollectionUtils.getMapByObjectWithObjKey(false,entity,"id","name","content","updater.name","updateTime"));
        return result;
    }


    @RequestMapping("/update.do")
    @ResponseBody
    @RoleSecurity(roleMark = "info.notice")
    public Result update(@RequestBody UserSaveNoticeRequest request){
        UserNoticeEntity entity = userNoticeService.findOne(request.getId());
        entity.setContent(request.getContent());
        entity.setUpdateTime(DateUtils.getDatetime());
        entity.setUpdater(SysContent.getLoginUser());
        userNoticeService.update(entity);
        return Result.getOkResult();
    }



}
