/**
 * Created by lixl on 2017/7/24.
 */
angular.module('app',['service.system']).controller('MonitorController', function ($system) {
    var $ctrl = this;
    var captcha = '/sys/system/login/captcha.do';
    $system.loadSmsList($ctrl);
});