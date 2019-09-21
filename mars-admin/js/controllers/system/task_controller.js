/**
 * Created by lixl on 2017/7/14.
 */
angular.module('app',['service.system']).controller('TaskController', function ($system) {
    var $ctrl = this;
    $system.loadJobList($ctrl);
});
