/**
 * Created by lixl on 2017/7/24.
 */
angular.module('app',['service.system','service.utils']).controller('LoginController', function ($system,$scope,$rootScope,$utils,$location,$state) {
    $scope.refresh = function () {
        var url = '/sys/system/login/captcha.do';
        $("#codImg").attr("src",url+"?v="+ new Date().getTime());
    }
    $scope.login = function(){
        if(!$scope.user||!$scope.user.userName){
            $scope.errorMessage = "用户名为空";
            $("#account").focus();
            return;
        }
        if(!$scope.user.passwd){
            $scope.errorMessage = "密码为空";
            $("#pwd").focus();
            return;
        }
        if(!$scope.user.authCode){
            $scope.errorMessage = "验证码为空";
            $("#imgCode").focus();
            return;

        }
        $system.login($scope.user,function (response) {
            if(response.code < 1){
                $scope.refresh();
                $scope.errorMessage = response.message;
            }else if(response.code == 1){
                $system.getLoginUser($rootScope,function (res) {
                    if(res.code == 1){
                        $system.initBaseData();
                        $system.loadMenus($rootScope);
                        $state.go("sys.dashboard",{},{reload:true});
                        if(!res.data.passwdUpdateTime){
                            $utils.alert("您的目前使用的系统预设登录密码安全强度较弱，请及时修改自己的登录密码！");
                            console.log("提示");
                        }
                    }
                })
            }
        });
    }
    $scope.enter = function (e) {
        var keycode = window.event?e.keyCode:e.which;
        if(keycode==13){
            $scope.login();
        }
    }
    $scope.refresh();
});