/**
 * Created by lixl on 2017/7/14.
 */
angular.module('app',['service.system']).controller('LoggerController', function ($system,$log) {
    var $ctrl = this;
    $ctrl.pageIndex = 1;
    $ctrl.pageSize = 20;
    $ctrl.toPage = function (pIndex, pSize){
        $ctrl.pageIndex = pIndex;
        $ctrl.pageSize = pSize;
        $system.pageOptlogList($ctrl);
    }

    $ctrl.query = function () {
        $ctrl.toPage(1,$ctrl.pageSize);
    }
    $ctrl.query();
});
