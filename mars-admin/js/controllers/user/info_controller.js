/**
 * Created by lixl on 2018/6/28 0028.
 */
var app = angular.module('app',['service.user']);
app.controller('UserTimeController', function ($uibModalInstance,$user,id,list) {
    var $ctrl = this;
    $ctrl.addDay = 30;
    $ctrl.id = id;
    $ctrl.disable = function () {
        $user.available(id,function () {
            list.query()
            $uibModalInstance.close();
        });
    }
    $ctrl.addTime = function () {
        $user.addTime($ctrl,function () {
            list.query()
            $uibModalInstance.close();
        });
    }
    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
})
app.controller('InfoController', function ($user,$uibModal) {
    var $ctrl = this;
    $ctrl.pageIndex = 1;
    $ctrl.pageSize = 20;
    $user.pageInfoList($ctrl);
    $ctrl.query = function(){
        $ctrl.toPage($ctrl.pageIndex,$ctrl.pageSize);
    }
    $ctrl.toPage = function (pIndex, pSize) {
        $ctrl.pageIndex = pIndex;
        $ctrl.pageSize = pSize;
        $user.pageInfoList($ctrl);
    }
    $ctrl.createKey = function (id) {
        $user.createKey(id,function () {
            $ctrl.query()
        });
    }
    $ctrl.delete = function (id) {
        $user.delete(id,function () {
            $ctrl.query()
        });
    }
    $ctrl.setAvailable = function (id) {
        $user.available(id,function () {
            $ctrl.query()
        });
    }

    $ctrl.setAvailable = function (id) {
        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'tpl/user/info_add_time.html',
            controller: 'UserTimeController',
            controllerAs: '$ctrl',
            size: 'md',
            backdrop:'static',
            resolve: {
                id: function () {
                    return id;
                },
                list:function () {
                    return $ctrl;
                }
            }
        });
    }
    
});