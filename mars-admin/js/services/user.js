/**
 * Created by lixl on 2017/7/31.
 */
angular.module('service.user', ['service.utils']).service('$user',function ($log,$window,$utils) {

    /**
     * 分页查询用户基本信息
     * @param $scope
     * @param call
     */
    this.pageInfoList = function ($scope,call) {
        var url = '/sys/user/info/page.do';
        var defaultParam = {
            available:$scope.available,
            page:$scope.pageIndex,
            rows:$scope.pageSize,
            sort:"o.expirationTime asc",
            name:$scope.keyword,
        };


        $utils.query(url,defaultParam,$utils.callback(call,function(response){
            $scope.infoList = response.list;
            $scope.total = response.total;
        }));
    }


    /**
     * 生成序列号
     * @param info
     * @param call
     */
    this.createKey = function(id,call){
        var url = '/sys/user/info/createKey/'+id+'.do';
        $utils.exOperate(url,call);
    }

    this.available = function(id,call){
        var url = '/sys/user/info/available/'+id+'.do';
        $utils.save(url,call);
    }

    /**
     * 删除注册信息
     * @param info
     * @param call
     */
    this.delete = function(id,call){
        var url = '/sys/user/info/delete/'+id+'.do';
        $utils.delete(url,call);
    }

    /**
     * 获取公告信息
     * @param info
     * @param call
     */
    this.getNotice = function($scope,id,call){
        if(id){
            var url = '/sys/user/notice/get/'+id+'.g';
            $utils.findOne(url,$utils.callback(call,function (response) {
                $scope.notice = response.data;
            }));
        }
    }

    /**
     * 保存用户信息
     * @param user
     * @param call
     */
    this.saveNotice = function(notice,call){
        var url = '/sys/user/notice/update.do';
        $utils.save(url,notice,call);
    }


    /**
     * 保存用户信息
     * @param user
     * @param call
     */
    this.addTime = function($scope,call){
        var url = '/sys/user/info/addTime.do';
        $utils.save(url,{id:$scope.id,addDay:$scope.addDay},call);
    }



})