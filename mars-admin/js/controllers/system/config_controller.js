angular.module('app',['ui.bootstrap','service.system','toaster']).controller("ConfigController",function ($scope,$http,$system) {
    var $ctrl = this;
    $ctrl.module = $scope.module;
    console.log("loading module:" + $ctrl.module);

    $system.loadRoleList($ctrl);

    $system.loadConfigList($ctrl,function (res) {
        for(var i in $ctrl.configList){
            var item = res.data[i];
            for(var j in item.child){
                var child = item.child[j];
                if(child.type == 2 && child.value){
                    child.list = angular.fromJson(child.value.trim());
                }
            }

        }
    });

    $ctrl.save = function (config){
        $system.saveConfig({
            configkey:config.configkey,
            configname:config.configname,
            configvalue:config.configvalue,
            groupindex:config.groupindex,
            id:config.id,
            module:config.module,
            value:config.value,
            type:config.type,
        },function () {
            config.list = angular.fromJson(config.value);
        });
    }
});
