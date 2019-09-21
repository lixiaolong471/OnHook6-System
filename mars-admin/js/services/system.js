/**
 * Created by lixl on 2017/7/16.
 */
'use strict';
angular.module('service.system', ['service.utils']).service('$system',function ($http,$log,$window,$utils,$localStorage) {
    
    this.login = function (user,call) {
        var url = '/sys/system/login/login.do';
        $utils.query(url,user,call);
    }

    this.resetPwd = function (param,call) {
        var url = '/sys/system/login/resetpwd.do';
        $utils.post(url,{success:"重置成功",error:"操作失败"},
            param,$utils.callback(call,function (response) {

            }))
    }

    this.logOut = function (call) {
        var url = '/sys/system/login/logOut.do';
        $utils.get(url,'',call);
    }

    /**
     * 获单个用户信息
     * @param $ctrl
     * @param id
     */
    this.getUser = function ($scope,id,call) {
        if(id){
            var url = '/sys/system/user/get/'+id+'.do';
            $utils.findOne(url,$utils.callback(call,function (response) {
                $scope.user = response.data;
            }));
        }
    }

    /**
     * 获单个角色信息
     * @param $ctrl
     * @param id
     */
    this.getRole = function ($scope,id,call) {
        if(id){
            var url = '/sys/system/role/get/'+id+'.do';
            $utils.findOne(url,$utils.callback(call,function (response) {
                $scope.role = response.data;
            }));
        }
    }

    /**
     * 保存用户信息
     * @param user
     * @param call
     */
    this.saveUser = function(user,call){
        var url = '/sys/system/user/save.do';
        $utils.save(url,user,call);
    }

    /**
     * 保存用户角色信息
     * @param role
     * @param call
     */
    this.saveRole = function(role,call){
        var url = '/sys/system/role/save.do';
        $utils.save(url,role,call);
    }

    /**
     * 获取当前登录用户信息
     * @param $scope
     * @param call
     */
    this.getLoginUser = function($scope,call){
        var url = '/sys/system/login/getInfo.do';
        $utils.get(url,'',$utils.callback(call,function (response) {
            $scope.loginUser = response.data;
        }));
    }



    /**
     * 修改当前登录用户信息
     * @param user
     * @param call
     */
    this.userSetting = function (user,call) {
        var url = '/sys/system/login/settings.do';
        $utils.update(url,user,call);
    }

    /**
     * 修改当前登录用户密码信息
     * @param user
     * @param call
     */
    this.userPwd = function (user,call) {
        var url = '/sys/system/login/updatePwd.do';
        $utils.update(url,user,call);
    }

    /**
     * 初始化密码
     * @param id
     */
    this.initPwd = function (id,call) {
        if(id){
            var url = '/sys/system/user/initpwd/'+id+'.do';
            $utils.disable(url,call);
        }
    }

    /**
     * 删除用户信息
     * @param id
     * @param call
     */
    this.deleteUser = function(id,call){
        if(id){
            var url = '/sys/system/user/delete/'+id+'.do';
            $utils.delete(url,call);
        }
    }

    /**
     * 禁用掉系统用户
     * @param id
     * @param call
     */
    this.disableUser = function(id,call){
        if(id){
            var url = '/sys/system/user/disable/'+id+'.do';
            $utils.disable(url,call);
        }
    }



    /**
     * 导出用户信息
     * @param call
     */
    this.exportUsers = function (call) {
        var url = '/sys/system/user/export.do';
        $utils.export(url);
        if(call){
            call();
        }
    }

    /**
     * 删除角色信息
     * @param id
     * @param call
     */
    this.deleteRole = function(id,call){
        if(id){
            var url = '/sys/system/role/delete/'+id+'.do';
            $utils.delete(url,call);
        }
    }

    /**
     *获取所有单位机构（树状）
     * */
    this.getOrganList = function ($scope,call) {
        var url = '/sys/system/org/tree.do';
        var defaultParams ={
            sort: 'orgaName',
            orgName: $scope.orgName,
        }
        $utils.query(url,defaultParams,$utils.callback(call,function (response) {
            $scope.organList = response.data;
        }));
    }

    /**
     * 获取单个单位信息
     * @param $scope
     * @param id
     * @param call
     */
    this.getOrga = function ($scope,id,call) {
        if(id){
            var url = '/sys/system/org/get/'+id+'.do';
            $utils.findOne(url,$utils.callback(call,function (response) {
                $scope.orga = response.data;
            }));
        }
    }

    /**
     * 删除单个单位信息
     * @param id
     * @param call
     */
    this.deleteOrga = function (id,call) {
        if(id){
            var url = '/sys/system/org/delete/'+id+'.do';
            $utils.delete(url,call);
        }
    }


    /**
     * 禁用掉组织架构信息
     * @param id
     * @param call
     */
    this.disableOrga = function (id,call) {
        if(id){
            var url = '/sys/system/org/disable/'+id+'.do';
            $utils.disable(url,call);
        }
    }
    /**
     * 保存组织机构信息
     * @param orga
     * @param call
     */
    this.saveOrga = function(orga,call){
        var url = '/sys/system/org/save.do';
        $utils.save(url,orga,call);
    }


    /**
     * 加载配置列表
     * @param $scope
     * @param call
     */
    this.loadConfigList = function ($scope,call) {
       var url = '/sys/system/config/list.do';
       $utils.query(url,{sort:"indexnum",module:$scope.module},$utils.callback(call,function (response) {
           $scope.configList = response.data;
       }));
    }

    /**
     * 保存配置列表
     * @param configList
     * @param call
     */
    this.saveConfig = function (config,call) {
        var url = '/sys/system/config/update.do';
        $utils.query(url,config,call);
    }

    /**
     * 加载用户角色列表
     * @param $scope
     * @param call
     */
    this.loadRoleList = function ($scope,call) {
        var url = '/sys/system/role/list.do';
        $utils.query(url,{sort:"createTime"},$utils.callback(call,function (response) {
            $scope.roleList = response.data;
        }));
    }

    /**
     * 加载权限信息列表
     * @param $scope
     * @param call
     */
    this.loadAuthorityList = function ($scope,call) {
        var url = '/sys/system/authority/list.do';
        $utils.query(url,{sort:"indexnum"},$utils.callback(call,function (response) {
            if($scope){
                $scope.authorityList = response.data;
            }
        }));
    }

    /**
     * 加载权限信息列表
     * @param $scope
     * @param call
     */
    this.loadMenus = function ($scope,call) {
        var url = '/sys/system/authority/menu.do';
        $utils.query(url,{sort:"indexnum"},$utils.callback(call,function (response) {
            if($scope){
                $scope.menus = response.data;
            }
        }));
    }

    /**
     * 加载权限信息列表
     * @param $scope
     * @param call
     */

    this.pageUserList = function ($scope,roleId,call) {
        var url = '/sys/system/user/page.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"o.createTime",
            name:$scope.name
        }
        if(roleId){
            defaultParam["roleId"] = roleId;
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.userPage = response.list;
            /*$scope.userPage = $scope.userPage.slice(($scope.pageIndex-1) * $scope.pageSize,$scope.pageIndex *  $scope.pageSize);*/
            $scope.total = response.total;
        }));
    }

    /**
     * 加载日志列表
     * @param $scope
     * @param call
     */
    this.pageOptlogList = function ($scope,call) {
        var url = '/sys/system/optlog/page.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"createTime desc",
            remark:$scope.content
        }

        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.optlogList = response.list;
            // console.log(222,response.total);
            $scope.total = response.total;
        }));
    }

    /**
     * 加载所有的任务列表
     * @param $scope
     * @param call
     */
    this.loadJobList = function ($scope,call) {
        var url = '/sys/system/job/list.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"jobName"
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.jobList = response.data;
        }));
    }

    /**
     * 加载所有的短信模板列表
     * @param $scope
     * @param call
     */
    this.loadSmsList = function ($scope,call) {
        var url = '/sys/system/sms/list.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"code"
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.smsList = response.data;
        }));
    }

    this.saveSmsList = function (smsList,call) {
        var url = '/sys/system/sms/update.do';
        $utils.save(url,smsList,call);
    }

    /**
     * 加载所有的短信模板列表
     * @param $scope
     * @param call
     */
    this.loadSmsLogList = function ($scope,call) {
        var url = '/sys/system/sms/log.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"createTime desc",
            tel:$scope.tel,
            selfMotion:$scope.selfMotion
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.logList = response.list;
            $scope.total = response.total;
        }));
    }
    
    /**
     * 发送短信
     * @param $scope
     * @param param
     * @param call
     */
    this.sendSms = function (param,call) {
        var url = '/sys/system/sms/send.do';
        $utils.post(url,{success:"提交成功，请耐心等待处理",error:"操作失败"},
        		param,$utils.callback(call,function (response) {
            
        }))
    }
    
    /**
     * 导入发送短信
     */
    this.importToSend=function(data,call){
        var url = '/sys/system/sms/importToSend.do';
    	$utils.upload(url,{success:"导入成功，请耐心等待处理",error:"导入失败"},data,call);
    }
    
    /**
     * 下载导入发送短信模板
     */
    this.downLoadSmsImportTemplate = function () {
        var url = '/sys/public/model/SYS_SMS_SEND_LIST.xlsx';
        $utils.downLoad(url);
    }

    /**
     * 导出短信记录
     */
    this.exportSmslog = function (call) {
        var url = '/sys/system/sms/export.do';
        $utils.export(url);
        if(call){
            call();
        }
    }

    /**
     * 加载所有的短信模板列表
     * @param $scope
     * @param call
     */
    this.loadMailList = function ($scope,call) {
        var url = '/sys/system/mail/list.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"code"
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.mailList = response.data;
        }));
    }

    /**
     * 加载所有的短信模板列表
     * @param $scope
     * @param call
     */
    this.loadMailLogList = function ($scope,call) {
        var url = '/sys/system/mail/log.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"createTime desc",
            tel:$scope.mail
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.mailList = response.list;
            $scope.total = response.total;
        }));
    }

    /**
     * 加载单位列表数据
     * @param $scope
     * @param call
     */
    this.loadOrgaList = function ($scope,call) {
        var url = '/sys/system/org/list.do';
        var defaultParam = {
            sort:"createTime desc",
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.orgaList = response.data;
        }));
    }


    /**
     * 加载系统编码列表数据
     * @param $scope
     * @param call
     */
    this.pageCodeList = function ($scope,call) {
        var url = '/sys/system/code/page.do';
        var defaultParam = {
            groupName:$scope.groupName,
            sort:"code,indexnum",
            type:$scope.type,
            page:$scope.pageIndex,
            rows:$scope.pageSize,
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.codeList = response.list;
            $scope.total = response.total;
        }));
    }

    /**
     * 加载所有的系统编码
     * @param $scope
     * @param call
     */
    this.loadCodeList = function ($scope,call) {
        var url = '/sys/system/code/list.do';
        var defaultParam = {
            groupName:$scope.groupName,
            sort:"code,indexnum",
            type:"base",
            page:$scope.pageIndex,
            rows:$scope.pageSize,
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.codeList = response.data;
        }));
    }



    /**
     * 加载系统编码列表数据
     * @param $scope
     * @param call
     */
    this.findNameByCode = function (key,value,call) {
        if($localStorage[key] ==null){
            var deferred = $q.defer();
            this.getCodeList(this,key,function () {
                $localStorage[key] = this.codeList;
                console.log($localStorage[key]);
                deferred.resolve(this.getCodeName(this.codeList,value));
            });
        }else{
            return this.getCodeName(this.codeList,value);
        }
    }

    this.getCodeName = function (codeList,value) {
        for(var i in codeList){
            var item = codeList[i];
            if(item.value == value){
                return item.name;
            }
        }
        return '--';
    }

    /**
     * 保存系统系统编码表
     * @param codeList
     * @param call
     */
    this.saveCode = function (code,call) {
        var url = '/sys/system/code/update.do';
        $utils.query(url,code,call);
    }

    /**
     * 加载系统编码列表数据
     * @param $scope
     * @param call
     */
    this.getCodeList = function ($scope,code,call) {
        var url = '/sys/system/code/list/'+code+'.do';
        var defaultParam = {
            sort:"indexnum desc",
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.codeList = response.data;
        }));
    }

    /**
     * 加载付息方式列表数据
     * @param $scope
     * @param call
     */
    this.getTypeList = function ($scope,code,call) {
        var url = '/sys/system/code/list/'+code+'.do';
        var defaultParam = {
            sort:"indexnum desc",
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            console.log(444, response.data);
            $scope.typeList = response.data;
        }));
    }

    this.getUploadedImg = function($scope,call){
        var url = 'sys/system/attachment/page.do';
        var defaultParam = {
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            resourceType:'IMG'
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.imgList = response.list;
            $scope.total = response.total;
        }));
    }

    /**
     * 加载编码数据
     */
    this.initCode = function () {
        this.loadCodeList({},function (res) {
            if(res.code == 1){
                var codeList = res.data;
                for(var i in codeList){
                    var data = codeList[i];
                    var dataList = $localStorage[data.code]
                    if(!dataList){
                        dataList = new Array();
                    }
                    dataList.push(data);
                    $localStorage[data.code] = dataList;
                }
            }
        })
    }

    /**
     * 加载基础数据
     */
    this.initBaseData = function () {
        this.initCode();
    }

    /**
     * 加载用户树
     * @param $scope
     * @param call
     */
    this.loadUserTree = function($scope,call){
        var url = '/sys/system/user/tree.do';
        var defaultParam = {
            sort:"createTime desc",
        }
        $utils.query(url,defaultParam,$utils.callback(call,function(response){
            $scope.userTree = response.data;
        }));
    }

    /**
     * 加载流程信息数据列表
     * @param $scope
     * @param call
     */
    this.loadWorkflowList = function ($scope,call) {
        var url = '/sys/system/workflow/list.do';
        var defaultParam = {
            sort:"updateTime desc",
        }
        $utils.query(url,defaultParam,$utils.callback(call,function(response){
            $scope.workflowList = response.data;
        }));
    }

    /**
     * 保存流程节点信息数据
     * @param code
     * @param call
     */
    this.saveNode = function (node,call) {
        var url = '/sys/system/workflow/saveNode.do';
        $utils.save(url,node,call);
    }

    /**
     * 保存流程节点信息数据
     * @param code
     * @param call
     */
    this.delNode = function (id,call) {
        var url = '/sys/system/workflow/delNode/'+id+'.do';
        if(id){
            $utils.delete(url,call);
        }
    }

    /**
     * 更新流程信息状态
     * @param status
     * @param call
     */
    this.updateWorkflowStatus = function (status,call) {
        var url = '/sys/system/workflow/status/'+id+'.do';
        $utils.exOperate(url,call);
    }

    /**
     * 获取流程详细信息
     * @param id
     * @param ctrl
     * @param $scope
     */
    this.getWorkflow = function (id,$scope,call) {
        if(id){
            var url = '/sys/system/workflow/get/'+id+'.do';
            $utils.findOne(url,$utils.callback(call,function (response) {
                $scope.workflow = response.data;
            }));
        }
    }
    
    this.getAuthority = function ($rootScope) {
    	var hasAuthority=false;
        if($rootScope.loginUser.position){
        	if($rootScope.loginUser.position==3){
        		hasAuthority=true;
        	}
        }
        return hasAuthority;
    };

});