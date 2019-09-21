/**
 * Created by lixl on 2018/7/2 0002.
 */
angular.module('app',['service.user']).controller('NoticeController', function ($user) {
    var $ctrl = this;
    $ctrl.noticeId = 1;
    $ctrl.query = function(){
        $user.getNotice($ctrl,$ctrl.noticeId);
    }

    $ctrl.save = function () {
        $user.saveNotice($ctrl.notice,function () {
            $ctrl.query()
        });
    }
    $ctrl.query()
});