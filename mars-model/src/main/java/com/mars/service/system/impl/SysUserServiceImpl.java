package com.mars.service.system.impl;

import com.mars.core.bean.param.PageRequest;
import com.mars.core.bean.param.Request;
import com.mars.core.dao.CommonDao;
import com.mars.core.service.CommonServiceSupport;
import com.mars.core.util.CollectionUtils;
import com.mars.core.util.EncryptionUtils;
import com.mars.core.util.SpringUtils;
import com.mars.core.util.StringUtils;
import com.mars.model.system.SysOrgaEntity;
import com.mars.model.system.SysUserEntity;
import com.mars.param.system.SysPageUserRequest;
import com.mars.param.system.SysUserRequest;
import com.mars.service.system.ISysOrgaService;
import com.mars.service.system.ISysRoleService;
import com.mars.service.system.ISysUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixl on 2017/7/4.
 */
@Transactional
@Service
public class SysUserServiceImpl extends CommonServiceSupport<SysUserEntity> implements ISysUserService{
	
	private static Logger LOG = Logger.getLogger(SysUserServiceImpl.class);

    @Autowired
    CommonDao commonDao;

    @Autowired
    ISysOrgaService orgaService;

    @Autowired
    ISysRoleService sysRoleService;

    @Override
    public Page<SysUserEntity> findByCondtion(PageRequest request) {

        String hql = "";
        SysPageUserRequest userRequest = (SysPageUserRequest)request;
        if(userRequest.getRoleId() != null){
            hql = "select distinct o from SysUserEntity o left join o.roles r where 1=1 ";
        }else{
            hql = "select distinct o from SysUserEntity o where 1=1 ";
        }
        return commonDao.queryPage(hql,request);
    }

    @Override
    public SysUserEntity findUser(String userName, String passwd) {
        SysUserEntity example = new SysUserEntity();
        example.setLoginName(userName);
        example.setPassword(EncryptionUtils.md5s(passwd));
        SysUserEntity user = this.queryByExampleFirst(example);
        return user;
    }
    
    @Override
    public SysUserEntity findUserByLoginName(String loginName) {
        SysUserEntity user = this.queryByExampleFirst(new String[]{"loginName"},new Object[]{loginName});
        return user;
    }

    @Override
    public List<Map<String, Object>> getTree(Request request) {
        List<SysOrgaEntity> list = orgaService.findByCondtion(request);
        List<Map<String,Object>> tree = CollectionUtils.getTree(list);
        return getChildNode(tree,list);
    }

    private List<Map<String,Object>> getChildNode(List<Map<String,Object>> tree,List<SysOrgaEntity> list){
        List<Map<String,Object>> userNodes = new ArrayList<>();
        for(Map<String,Object> orga:tree){
            Map<String,Object> userNode = new HashMap<>();
            userNode.put("id",orga.get("id"));
            userNode.put("name",orga.get("orgaName"));
            userNode.put("leaf",false);
            List<SysUserEntity> userList = getUserNode((Long)orga.get("id"),list);
            List<Map<String,Object>> child = null;
            if(orga.get("child") == null){
                child = new ArrayList<>();
                orga.put("child",child);
            }else{
                child = (ArrayList<Map<String,Object>>)orga.get("child");
                child = getChildNode(child,list);
            }
            if(userList != null){
                for(SysUserEntity node:userList){
                    if(node != null && node.getAvailable()){
                        Map<String,Object> childNode = CollectionUtils.getMapByObjectWithObjKey(false,node,"id","name");
                        childNode.put("leaf",true);
                        child.add(childNode);
                    }
                }
            }
            userNode.put("child",child);
            userNodes.add(userNode);
        }
        return userNodes;
    }


    private List<SysUserEntity> getUserNode(Long id, List<SysOrgaEntity> list){
        for(SysOrgaEntity orga:list){
            if(orga.getId().equals(id)){
               return orga.getUserList();
            }
        }
        return null;
    }

    
    @Override
    @Transactional(readOnly=true)
    public List<SysUserEntity> findByRole(Long roleId){
    	StringBuilder hql = new StringBuilder("select distinct o from SysUserEntity o left join o.roles r where 1=1 ");
    	SysUserRequest request=new SysUserRequest();
    	if(roleId!=null){
        	request.setRoleId(roleId);
    	}
        List<SysUserEntity> list = commonDao.queryList(hql.toString(),request);
        return list;
    }
    
    @Override
    public List<String> findAllMobileByRole(Long roleId){
    	ISysUserService sysUserService=SpringUtils.getBean(ISysUserService.class);
    	List<SysUserEntity> list=sysUserService.findByRole(roleId);
    	List<String> mobileList=new ArrayList<String>();
        if(list!=null&&list.size()>0){
        	for (SysUserEntity sysUserEntity : list) {
        		if(StringUtils.isNotEmpty(sysUserEntity.getMobile())){
            		mobileList.add(sysUserEntity.getMobile());
        		}
			}
        }
        return mobileList;
    }

}
