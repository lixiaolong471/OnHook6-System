/**
 * Created by lixl on 2017/7/23.
 */
angular.module('app',['service.system']).controller('JobController', function ($system) {
    var $ctrl = this;
    $system.loadJobList($ctrl);
});
