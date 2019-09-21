/**
 * Created by lixl on 2017/7/28.
 */
angular.module('app',['ui.bootstrap','service.system','toaster']).controller("CodeController",function ($http,$system) {
    var $ctrl = this;
    $ctrl.type = 'base';
    $ctrl.pageIndex = 1;
    $ctrl.pageSize = 20;
    $system.pageCodeList($ctrl);
    $ctrl.toPage = function (pIndex, pSize){
        $ctrl.pageIndex = pIndex;
        $ctrl.pageSize = pSize;
        $system.pageCodeList($ctrl);
    }
    $ctrl.save = function (item){
        console.log(item);
        $system.saveCode(item);
    }
    $ctrl.query = function () {
        $system.pageCodeList($ctrl);
    }
    
});