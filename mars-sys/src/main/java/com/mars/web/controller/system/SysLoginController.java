package com.mars.web.controller.system;

import com.mars.core.bean.annotation.logger.OpLog;
import com.mars.core.bean.param.DataResult;
import com.mars.core.bean.param.Result;
import com.mars.core.util.*;
import com.mars.model.system.SysUserEntity;
import com.mars.param.system.SysSaveUserRequest;
import com.mars.param.system.SysUserPwdRequest;
import com.mars.service.SysConfigKey;
import com.mars.service.SysContent;
import com.mars.service.system.ISysUserService;
import com.mars.param.system.SysUserLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by lixl on 2017/7/24.
 */
@Controller
@RequestMapping("/system/login")
public class SysLoginController {

    private static final String CAPTACHA = "user_login_captacha";

    private static final String pwd_error_count = "user_login_captacha";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisTemplate<String,Integer> redisTemplate;

    @OpLog(value = "用户登录", type = OpLog.Type.LOGIN, module = "系统管理")
    @RequestMapping("/login.do")
    @ResponseBody
    public Result login(HttpSession session,@RequestBody SysUserLoginRequest request){
        String captcha = (String)session.getAttribute(CAPTACHA);
        Result result = Result.getOkResult();
        if(StringUtils.isNotEmpty(captcha) &&
                StringUtils.isNotEmpty(request.getAuthCode()) && captcha.equalsIgnoreCase(request.getAuthCode())){
            SysUserEntity user = userService.findUser(request.getUserName(),request.getPasswd());
            if(user == null){
                Integer errorCount = redisTemplate.opsForValue().get(pwd_error_count+request.getUserName());
                if(errorCount == null){
                    errorCount = 0;
                }
                if(((errorCount +1 ) == SysContent.getConfigInteger(SysConfigKey.LOGIN.SYS_USER_PASSWORD_ERR_LOCK_TIME))){
                    user = userService.queryByExampleFirst(new String[]{"loginName"},new String[]{request.getUserName()});
                    if(user != null){
                        user.setAvailable(false);
                        userService.update(user);
                        return new Result(-3,"由于您密码输错错误次数过多，账户已被禁用");
                    }
                }else{
                    redisTemplate.opsForValue().set(pwd_error_count+request.getUserName(),errorCount + 1);
                }
                return new Result(-2,"用户名或密码不正确");
            }else if(!user.getAvailable()){
                return Result.getErrorResult("用户被禁用");
            }
            redisTemplate.delete(pwd_error_count+request.getUserName());
            user.setLastLoginTime(DateUtils.getDatetime());
            userService.update(user);
            SysContent.setLoginUser(user);
        }else{
            result = Result.getErrorResult("验证码错误");
        }
        session.setAttribute(CAPTACHA,null);
        return result;
    }

    /**
     * 返回验证码
     * @param session
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/captcha.do", method = RequestMethod.GET)
    public void captcha(HttpSession session, HttpServletResponse response)
            throws Exception {
        String code = ImageUtils.generatePhoneVerifyCode(4);
        System.err.println(code);
        session.setAttribute(CAPTACHA,code);
        response.setContentType("image/png");
        OutputStream stream = response.getOutputStream();
        try {
            ImageUtils.outputImage(72, 32, stream, code);
        }catch (Exception e) {
            e.printStackTrace();
        }  finally {
            stream.flush();
            stream.close();
        }
    }

    @OpLog(value = "退出登录", type = OpLog.Type.LOGIN, module = "系统管理",post = false)
    @RequestMapping("/logOut.do")
    @ResponseBody
    public Result logOut(HttpSession session, HttpServletResponse response){
        SysContent.setLoginUser(null);
        return Result.getOkResult();
    }

    @RequestMapping("/getInfo.do")
    @ResponseBody
    public Result getInfo(){
        Map<String,Object> user = CollectionUtils.getMapByObjectWithObjKey(false,SysContent.getLoginUser(),
                "id","name","address","tel","email","mobile","zjh","photoUrl","position","jobNo","passwdUpdateTime");
        return new DataResult(user);
    }


    @OpLog(value = "修改或完善个人信息", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/settings.do")
    @ResponseBody
    public Result settings(@RequestBody SysSaveUserRequest request){
        SysUserEntity userEntity = request.settings();
        userService.update(userEntity);
        SysContent.setLoginUser(userEntity);
        return Result.getOkResult();
    }

    @OpLog(value = "修改个人密码", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/updatePwd.do")
    @ResponseBody
    public Result updatePwd(@RequestBody SysUserPwdRequest request){
        SysUserEntity userEntity = SysContent.getLoginUser();
        if(userEntity.getPassword().equals(EncryptionUtils.md5s(request.getOldPasswd()))){
            userEntity.setPasswdUpdateTime(DateUtils.getDatetime());
            userEntity.setPassword(EncryptionUtils.md5s(request.getNewPasswd()));
            userService.update(userEntity);
            SysContent.setLoginUser(userEntity);
        }else{
            return Result.getErrorResult("原密码错误");
        }
        return Result.getOkResult();
    }


    @OpLog(value = "用户重置密码", type = OpLog.Type.MODIFY, module = "系统管理")
    @RequestMapping("/resetpwd.do")
    @ResponseBody
    public Result resetpwd(HttpSession session,@RequestBody SysUserPwdRequest request){
        String captcha = (String)session.getAttribute(CAPTACHA);
        Result result = Result.getOkResult();
        if(request.getNewPasswd().equals(request.getOldPasswd())){
            return Result.getErrorResult("新旧密码不能相同");
        }
        if(StringUtils.isNotEmpty(captcha) &&
                StringUtils.isNotEmpty(request.getAuthCode()) && captcha.equalsIgnoreCase(request.getAuthCode())){
            SysUserEntity user = userService.findUser(request.getLoginName(),request.getOldPasswd());
            if(user == null){
                Integer errorCount = redisTemplate.opsForValue().get(pwd_error_count+request.getLoginName());
                if(errorCount == null){
                    errorCount = 0;
                }
                if(((errorCount +1 ) == SysContent.getConfigInteger(SysConfigKey.LOGIN.SYS_USER_PASSWORD_ERR_LOCK_TIME))){
                    user = userService.queryByExampleFirst(new String[]{"loginName"},new String[]{request.getLoginName()});
                    if(user != null){
                        user.setAvailable(false);
                        userService.update(user);
                        return new Result(-3,"由于您密码输错错误次数过多，账户已被禁用");
                    }
                }else{
                    redisTemplate.opsForValue().set(pwd_error_count+request.getLoginName(),errorCount + 1);
                }
                return new Result(-2,"用户名或密码不正确");
            }else if(!user.getAvailable()){
                return Result.getErrorResult("用户被禁用");
            }
            redisTemplate.delete(pwd_error_count+request.getLoginName());
            user.setPasswdUpdateTime(DateUtils.getDatetime());
            user.setPassword(request.getNewPasswd());
            userService.update(user);
        }else{
            result = Result.getErrorResult("验证码错误");
        }
        session.setAttribute(CAPTACHA,null);
        return result;
    }



}
