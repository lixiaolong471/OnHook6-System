/**
 * Created by lixl on 2017/12/13 0013.
 */
var app = angular.module('app',['service.system','ui.select','service.utils','toaster']);
app.controller("WorkflowController",function ($scope,$http,$system,$uibModal) {
    var $ctrl = this;
    $ctrl.selectedId == null;
    $ctrl.query = function () {
        //获取流程数据
        $system.loadWorkflowList($ctrl,function (res) {
            if(res.code == 1 && $ctrl.workflowList.length > 0){
                $ctrl.selectedId = $ctrl.workflowList[0].id;
            }
            $ctrl.viewDetail($ctrl.selectedId);
        });
    }

    //更新流程状态
    $ctrl.updateStatus = function (id){
        $system.updateWorkflowStatus(id);
    }

    $ctrl.viewDetail = function (id) {
        $ctrl.selectedId = id;
        $system.getWorkflow(id,$ctrl);
    }

    $ctrl.delNode = function (id) {
        $system.delNode(id,function () {
            $ctrl.query();
        });
    }

    /*新增或者查看用户信息*/
    $ctrl.editNode = function (item) {
        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'tpl/system/workflow_edit.html',
            controller: 'WorkflowNodeController',
            controllerAs: '$ctrl',
            backdrop:'static',
            size: 'md',
            resolve: {
                item: function () {
                    return item;
                },
                selectedId:function () {
                    return  $ctrl.selectedId;
                },
                list:function () {
                    return $ctrl;
                }
            }
        });
    }

    //初始化数据
    $ctrl.query();
});


app.controller('WorkflowNodeController', function ($uibModalInstance,$system,item,selectedId,list) {
    var $ctrl = this;
    $ctrl.roleId = 1;
    $system.loadRoleList($ctrl);
    if(item){
        $ctrl.WindowsTitle = "编辑节点信息";
        $ctrl.node = item;
    }else{
        $ctrl.node = {};
        $ctrl.WindowsTitle = "新增节点信息";
    }
    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
    $ctrl.node.workflow = {id:selectedId};
    //保存流程信息
    $ctrl.saveNode = function (){
        $system.saveNode($ctrl.node,function (res) {
            if(res.code == 1){
                $uibModalInstance.close();
                $ctrl.viewDetail(selectedId);
            }
        });
    }

});