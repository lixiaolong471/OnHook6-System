/**
 * Created by lixl on 2017/7/24.
 */
angular.module('app',['service.system']).controller('MailController', function ($system) {
    var $ctrl = this;
    $system.loadMailList($ctrl);
});