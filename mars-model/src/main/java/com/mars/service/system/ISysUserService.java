package com.mars.service.system;

import com.mars.core.bean.param.Request;
import com.mars.core.bean.param.Result;
import com.mars.core.service.CommonService;
import com.mars.model.system.SysRoleEntity;
import com.mars.model.system.SysUserEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * Created by lixl on 2017/7/4.
 */
public interface ISysUserService extends CommonService<SysUserEntity>{

    /**
     * 系统默认管理员ID，系统超级用户。
     */
    Long ADMIN_USER_ID = 1L;

    SysUserEntity findUser(String userName,String passwd);

    /**
     * 通过登录名获取用户信息
     * @param loginName
     * @return
     */
	SysUserEntity findUserByLoginName(String loginName);

    /**
     * 获取用户组织架构树
     * @param request
     * @return
     */
    List<Map<String, Object>> getTree(@RequestBody Request request);

	/**
	 * 根据角色获取用户列表
	 * @param roleId
	 * @return
	 */
	List<SysUserEntity> findByRole(Long roleId);

	/**
	 * 根据角色查询手机号
	 * @param roleId
	 * @return
	 */
	List<String> findAllMobileByRole(Long roleId);
}
