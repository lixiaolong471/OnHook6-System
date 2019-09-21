angular.module('app').controller('OrgController',function($utils,$uibModal, $log,$system,$scope, $document) {
    $scope.utils = $utils;
    var $ctrl = this;
    $ctrl.query = function(){
        $system.getOrganList($ctrl);
    }
    $ctrl.query();
    $ctrl.open = function (item,e) {
        e.stopPropagation();
        if(item.child && item.child.length >0){
            item.close = !item.close;
        }
    }
    $ctrl.addOrg = function (id,parentSelector) {
        var parentElem = parentSelector ?
            angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'tpl/system/org_add.html',
            controller: 'OrgDialogController',
            controllerAs: '$ctrl',
            backdrop:'static',
            size: 'lg',
            appendTo: parentElem,
            resolve: {
                id: function () {
                    return id;
                }
            }
        });

        modalInstance.result.then(function () {
            $system.getOrganList($ctrl);
        });
    };
    $ctrl.delOrg = function (id) {
        $utils.alertMsg({
            ok:function(){
                $system.deleteOrga(id,function () {
                    $system.getOrganList($ctrl);
                });
            }
        })
    }

    $ctrl.disable = function (id) {
        console.log(id);
        $system.disableOrga(id);
    }

    $system.loadUserTree($ctrl);
    $ctrl.userId = 292;

});
angular.module('app').controller('OrgDialogController', function ($system,$uibModalInstance,$utils,$scope,$timeout,id) {
    $scope.utils = $utils;
    var $ctrl = this;

    $ctrl.depart = '无';
    var parentObj = {parent:{id:0}}
    if(id){
        $ctrl.WindowsTitle = "编辑机构信息";
        $system.getOrganList($ctrl, function(data){
            $system.getOrga($ctrl,id,function(response){
                if(response.code == 1){
                    if(response.data['parent.id']){
                        parentObj.parent.id = response.data['parent.id'];
                        $ctrl.parentId =  response.data['parent.id'];
                        $ctrl.depart = $utils.getParentName(response.data['parent.id'],'child',data.data,'orgaName')
                    }
                }
            })
        })

    } else {
        $ctrl.WindowsTitle = "添加机构信息";
        $system.getOrganList($ctrl);
    }
    $ctrl.open = function (item,e) {
        e.stopPropagation();
        if(item.child && item.child.length >0){
            item.close = !item.close;
        }
    }

    $ctrl.openUl = function (e) {
        e.stopPropagation();
        $ctrl.isOpen = !$ctrl.isOpen;
    }

    $ctrl.selectItem = function(item,e){
        e.stopPropagation();
        $ctrl.depart = item.orgaName;
        parentObj.parent.id = item.id;
        $ctrl.isOpen = false
    }

    $ctrl.ok = function () {
        if(parentObj.parent.id != 0){
            $.extend($ctrl.orga,parentObj);
        }
        $ctrl.orga.available = true;
        $system.saveOrga($ctrl.orga, function(data){
            if(data.code = 1){
                $system.getOrganList($ctrl);
                $uibModalInstance.close();
            }
        })
};

    $ctrl.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
});
