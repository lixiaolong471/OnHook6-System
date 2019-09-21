package com.mars.web.controller.user;

import com.mars.core.bean.annotation.authority.RoleSecurity;
import com.mars.core.bean.annotation.logger.OpLog;
import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.PageResult;
import com.mars.core.bean.param.Result;
import com.mars.core.util.CollectionUtils;
import com.mars.core.util.DateUtils;
import com.mars.core.util.EncryptionUtils;
import com.mars.core.util.StringUtils;
import com.mars.model.user.UserInfoEntity;
import com.mars.param.user.UserPageInfoRequest;
import com.mars.param.user.UserSaveInfoRequest;
import com.mars.service.SysConfigKey;
import com.mars.service.SysContent;
import com.mars.service.user.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by lixl on 2018/6/28 0028.
 */
@Controller
@RequestMapping("/user/info/")
public class UserController {

    @Autowired
    IUserInfoService userInfoService;


    @RequestMapping("/page.do")
    @ResponseBody
    @RoleSecurity(roleMark = "info.list")
    public Result page(@RequestBody UserPageInfoRequest request){
        Page<UserInfoEntity> page = userInfoService.findByCondtion(request);
        return new PageResult(page.getTotalElements(), CollectionUtils.getList(false,page.getContent(),"id","name","machineCode","creator.name","serialKey","expirationTime","lastUseTime","createTime","available"));
    }

    @RequestMapping("/delete/{id}.do")
    @ResponseBody
    @RoleSecurity(roleMark = "info.list")
    public Result delete(@PathVariable Long id){
        userInfoService.deleteById(id);
        return Result.getOkResult();
    }

    @RequestMapping("/available/{id}.do")
    @ResponseBody
    @RoleSecurity(roleMark = "info.list")
    @OpLog(value = "账户启用", type = OpLog.Type.MODIFY, module = "系统管理")
    public Result available(@PathVariable Long id){
        UserInfoEntity user = userInfoService.findOne(id);
        if(user.getAvailable()){
            user.setAvailable(false);
        }
        userInfoService.update(user);
        return Result.getOkResult();
    }

    @RequestMapping("/addTime.do")
    @ResponseBody
    @RoleSecurity(roleMark = "info.list")
    @OpLog(value = "账户启用", type = OpLog.Type.MODIFY, module = "系统管理")
    public Result addTime(@RequestBody UserSaveInfoRequest saveRequest){
        UserInfoEntity user = userInfoService.findOne(saveRequest.getId());
        user = saveRequest.getAddDateUpdate(user);
        userInfoService.update(user);
        return Result.getOkResult();
    }


    /**
     * 激活第一步：注册激活
     * @return
     */
    @RequestMapping("/registerVerify.g")
    @ResponseBody
    public Result registerVerify(HttpServletRequest request){
        String name = request.getParameter("name");
        String machineCode = request.getParameter("machineCode");
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(machineCode)){
            return new Result(-1,"参错错误");
        }
        UserSaveInfoRequest saveRequest = new UserSaveInfoRequest();
        saveRequest.setName(name);
        saveRequest.setMachineCode(machineCode);
        UserInfoEntity entity = userInfoService.findByMachineCode(machineCode,name);
        if( entity!= null){
            entity = saveRequest.getUpdate(entity);
            userInfoService.update(entity);
        }else{
            entity = saveRequest.getSave();
            userInfoService.save(entity);
        }
        return DataResult.getOkResult();
    }



    @OpLog(value = "生成序列号", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/createKey/{id}.do")
    @ResponseBody
    @RoleSecurity(roleMark = "info.list")
    public Result createKey(@PathVariable Long id){
        UserInfoEntity user = userInfoService.findOne(id);
        String machineCode = user.getMachineCode();
        String encryptPassword = SysContent.getConfigValue(SysConfigKey.USER_REGISTER_CONFIG.PASSWORD);
        int monthCount = SysContent.getConfigInteger(SysConfigKey.USER_REGISTER_CONFIG.RENEWAL_COUNT);
        String key = EncryptionUtils.encryptByDES(machineCode,encryptPassword);
        String md5Key = EncryptionUtils.md5s(key);
        String formatKey = md5Key.substring(0,8);
        for(int i = 1;i<4;i++){
            formatKey += ("-" + md5Key.substring(i*8,(i+1)*8));
        }
        user.setExpirationTime(new Timestamp(DateUtils.addMonth(new Date(),monthCount).getTime()));
        user.setSerialKey(formatKey);
        user.setAvailable(true);
        userInfoService.update(user);
        return Result.getOkResult();
    }



    @RequestMapping("/loginValidate.g")
    @ResponseBody
    public Result loginValidate(String serialKey,String machineCode){
        if(StringUtils.isEmpty(serialKey) || StringUtils.isEmpty(machineCode)){
            return new Result(-1,"参错错误");
        }
        UserInfoEntity userInfo = userInfoService.findBySerialKey(serialKey);
        if(userInfo == null){
            return new Result(-2,"未注册");
        }
        String encryptPassword = SysContent.getConfigValue(SysConfigKey.USER_REGISTER_CONFIG.PASSWORD);
        String key = EncryptionUtils.encryptByDES(machineCode,encryptPassword);
        String md5Key = EncryptionUtils.md5s(key);
        if(serialKey.replaceAll("-","").equals(md5Key)){
            if(userInfo.getAvailable() && userInfo.getExpirationTime().compareTo(DateUtils.getDatetime()) >= 0){
                userInfo.setLastUseTime(DateUtils.getDatetime());
                userInfoService.update(userInfo);
                DataResult result = new DataResult();
                result.setData(userInfo.getExpirationTime().getTime());
                return result;
            }else{
                userInfo.setAvailable(false);
                userInfoService.update(userInfo);
                return new Result(-3,"序列号已过期");
            }
        }else{
            return new Result(-4,"序列号无效");
        }
    }

}
