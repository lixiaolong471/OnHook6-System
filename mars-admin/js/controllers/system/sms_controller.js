/**
 * Created by lixl on 2017/7/23.
 */
angular.module('app',['service.system']).controller('SmsController', function ($system) {
    var $ctrl = this;
    $system.loadSmsList($ctrl);
    $ctrl.save = function () {
        $system.saveSmsList($ctrl.smsList);
    }
});
