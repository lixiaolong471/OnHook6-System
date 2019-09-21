var app = angular.module('app',['service.system','ui.select','service.utils','toaster']);
app.controller('UserController',function($uibModal, $log, $document,$system,$scope,$utils,toaster) {
    $scope.utils = $utils;
    var $ctrl = this;
    $ctrl.isCheckAll = false;
    $ctrl.selectedData = [];
    $system.loadRoleList($ctrl);


    $ctrl.pageIndex = 1;
    $ctrl.pageSize = 20;
    $system.pageUserList($ctrl);
    $ctrl.toPage = function (pIndex, pSize) {
        $ctrl.pageIndex = pIndex;
        $ctrl.pageSize = pSize;
        $system.pageUserList($ctrl);
    }

    /*左边角色列表被选中的项*/
    $ctrl.selectedId;
    $ctrl.query = function (roleId) {
        $ctrl.selectedId = roleId;
        $system.pageUserList($ctrl,roleId);
    }
    //重新加载数据
    $ctrl.reload = function () {
        $system.pageUserList($ctrl,$ctrl.selectedId);
    }

    $ctrl.addRole = function (id) {
        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'tpl/system/role_add.html',
            controller: 'RoleDialogController',
            controllerAs: '$ctrl',
            size: 'lg',
            backdrop:'static',
            resolve: {
                id: function () {
                    return id;
                }
            }
        });
        modalInstance.result.then(function () {
            $system.loadRoleList($ctrl);
        });
    };

    $ctrl.delRole = function (id) {
        $utils.alertMsg({
            ok:function(){
                $system.deleteRole(id,function () {
                    $system.loadRoleList($ctrl);
                });
            }
        })

    }
    /*新增或者查看用户信息*/
    $ctrl.addUser = function (id) {
        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'tpl/system/user_add.html',
            controller: 'UserDialogController',
            controllerAs: '$ctrl',
            backdrop:'static',
            size: 'lg',
            resolve: {
                id: function () {
                    return id;
                },
                listPage : function () {
                    return $ctrl;
                }
                
            }
        });
    }


    $ctrl.export = function () {
        $system.exportUsers();
    }

       /*删除表格数据*/
    $ctrl.dataDelete = function(){
        var options         = {};
        // 配置模态框并获取数据id
        var resultID = $utils.operate({
            dataList: $ctrl.userPage,//源数组
            options: options,//modal 配置信息
            selectedList: $ctrl.selectedData,//选中的数组
            field: 'id',//需要返回的字段以字符串逗号分隔形式
            type: 'delete'//操作类型
        })
        if(resultID == ''){return;}
        // 确定的回调函数
        options.ok = function(){
            $system.deleteUser(resultID,function(data){
                $utils.renderTable($ctrl.selectedData,$ctrl.userPage,$ctrl)
                $system.pageUserList($ctrl);
                $scope.selectedData = [];
                $ctrl.isCheckAll = false;
            })
        }
    }

    /*禁用或启用用户*/
    $ctrl.isDisable = function (id) {
        $system.disableUser(id);
    }
});
app.controller('UserDialogController', function ($uibModalInstance,$system,id,listPage) {
    var $ctrl = this;
    $ctrl.initSelect = function() {
        if($ctrl.orgaList && $ctrl.roleList && $ctrl.user){
            //选择对应数据
            $ctrl.orgas = new Array();
            for(var i in $ctrl.orgaList){
                var orga = $ctrl.orgaList[i];
                for(var j in $ctrl.user.orgaIds){
                    var _orga_id = $ctrl.user.orgaIds[j];
                    if(orga.id === _orga_id){
                        $ctrl.orgas.push(orga);
                    }
                }
            }
            $ctrl.roles = new Array();
            for(var k in $ctrl.roleList){
                var role = $ctrl.roleList[k];
                for(var v in $ctrl.user.roleIds){
                    var _role_id = $ctrl.user.roleIds[v];
                    if(role.id === _role_id){
                        $ctrl.roles.push(role);
                    }
                }
            }
            if( $ctrl.user.available){
                $ctrl.user.available = "true";
            }else{
                $ctrl.user.available = "false";
            }
        }
    }

    //初始化列表数据
    $system.loadRoleList($ctrl,$ctrl.initSelect);
    $system.loadOrgaList($ctrl,$ctrl.initSelect);
    $system.getCodeList($ctrl,'sys_position',function (res) {
        $ctrl.positionList = res.data;
    });

    if(id){
        $ctrl.WindowsTitle = "编辑用户信息";
        $system.getUser($ctrl,id,$ctrl.initSelect);
    }else{
        $ctrl.WindowsTitle = "新增用户角色";
    }
    


    $ctrl.save = function () {
        console.log($ctrl.user);
        $ctrl.user.orgaIds = new Array();
        for(var i in $ctrl.orgas){
            $ctrl.user.orgaIds.push($ctrl.orgas[i].id);
        }
        $ctrl.user.roleIds = new Array();
        for(var i in $ctrl.roles){
            $ctrl.user.roleIds.push($ctrl.roles[i].id);
        }
        $system.saveUser($ctrl.user,function (response) {
            if(response.code == 1){
                listPage.reload();
                $uibModalInstance.close();
            }
        });
    };
    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
    //初始化密码
    $ctrl.initPwd = function (id) {
        $system.initPwd(id,function () {
            $uibModalInstance.close();
        });
    };


});
app.controller('RoleDialogController', function ($uibModalInstance,$system,$log,$utils,id) {
    var $ctrl = this;
    $ctrl.role = {
        authoritys: [],
    }
    $ctrl.initCheck = function () {
        if($ctrl.role && $ctrl.role.authoritys && $ctrl.authorityList){
            for(var key in $ctrl.authorityList){
                if($ctrl.authorityList[key].child && $ctrl.authorityList[key].child.length > 0){
                    for(var i in $ctrl.authorityList[key].child){
                        var selected = false;
                        var authority = $ctrl.authorityList[key].child[i];
                        for(var c in $ctrl.role.authoritys){
                            var id = $ctrl.role.authoritys[c];
                            if(id === authority.id){
                                selected = true;
                            }
                        }
                        authority.selected = selected;
                    }
                }
            }
        }
    }
    if(id){
        $system.getRole($ctrl,id,$ctrl.initCheck);
        $ctrl.WindowsTitle = "编辑用户角色";
    }else{
        $ctrl.WindowsTitle = "新增用户角色";
    }
    $system.loadAuthorityList($ctrl,$ctrl.initCheck);
    $ctrl.ok = function () {
        $system.saveRole($ctrl.role,function (response) {
            if(response.code == 1){
                $uibModalInstance.close();
            }
        });
    };

    $ctrl.check = function (childItem,item) {
        childItem.selected = !childItem.selected;
        if(childItem.selected){
            if(!$utils.contains($ctrl.role.authoritys,item.id)){
                $ctrl.role.authoritys.push(item.id);
            }
            $ctrl.role.authoritys.push(childItem.id);
        }else{
            console.log($utils.contains($ctrl.role.authoritys,item.id));
            $utils.arrayRemove($ctrl.role.authoritys,childItem.id);
            if($utils.contains($ctrl.role.authoritys,item.id)){
                var hasSelected = false;
                for(var i in $ctrl.role.authoritys){
                    for(var j in  item.child){
                        if($ctrl.role.authoritys[i] == item.child[j].id){
                            hasSelected = true;
                        }
                    }
                }
                console.log(hasSelected);
                if(!hasSelected){
                    $utils.arrayRemove($ctrl.role.authoritys,item.id);
                }
            }

        }
    };

    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
});