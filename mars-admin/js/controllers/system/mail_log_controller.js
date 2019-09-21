/**
 * Created by lixl on 2017/7/24.
 */
angular.module('app',['service.system']).controller('MailLogController', function ($system) {
    var $ctrl = this;
    $ctrl.pageIndex = 1;
    $ctrl.pageSize = 20;
    $system.loadMailLogList($ctrl);
    $ctrl.query = function(){
        $ctrl.toPage($ctrl.pageIndex,$ctrl.pageSize);
    }
    $ctrl.toPage = function (pIndex, pSize) {
        $ctrl.pageIndex = pIndex;
        $ctrl.pageSize = pSize;
        $system.loadMailLogList($ctrl);
    }
    $ctrl.open =function(id){
        var iWidth=750;                          //弹出窗口的宽度;
        var iHeight=500;                         //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
        //获得窗口的水平位置
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
        window.open("/sys/system/mail/"+id+".do",
                        "_blank",
                        "height="+iHeight+",width="+iWidth+",top="+iTop+",left="+iLeft+",toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no"
                    )
    }
});