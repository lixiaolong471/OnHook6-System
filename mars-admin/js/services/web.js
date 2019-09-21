/**
 * Created by lixl on 2017/7/29.
 */
angular.module('service.web', ['service.utils']).service('$web',function ($http,$log,$window,$utils) {
    /**
     * 加载广告位数据列表
     * @param $scope
     * @param call
     */
    this.loadAdvertisementList = function ($scope,call) {
        var url = '/sys/web/advertisement/list.do';
        console.log($scope.clientType);
        var param = {
            available:$scope.available,
            section:$scope.section,
            clientType:$scope.clientType,
            sort:"order_sort"
        }
        $utils.query(url,param,$utils.callback(call,function (response) {
            $scope.advertisementList = response.data;
            $scope.total = response.total;
        }));
    }

    /**
     * 获取广告位详情信息
     * @param $scope
     * @param id
     * @param call
     */
    this.getAdvertisement = function ($scope,id,call) {
        var url = '/sys/web/advertisement/get/'+id+'.do';
        $utils.findOne(url,$utils.callback(call,function (response) {
            $scope.advertisement = response.data;
        }));
    }


    /**
     * 禁用广告位信息
     * @param id
     * @param call
     */
    this.disableAdvertisement = function (id,call) {
        var url = '/sys/web/advertisement/disable/'+id+'.do';
        $utils.disable(url,call);
    }

    /**
     * 置顶到首页
     * @param id
     * @param call
     */
    this.topRecommend = function (id,call) {
        var url = '/sys/web/recommend/top/'+id+'.do';
        $utils.disable(url,call);
    }
    
    /**
     * 向上移动广告位
     * @param id
     * @param call
     */
    this.upAdvertisement = function (id,list,call) {
        var url = '/sys/web/advertisement/displace.do';
        $utils.up(url,id,list,call);
    }

    /**
     * 向下移动广告位
     * @param id
     * @param call
     */
    this.downAdvertisement = function (id,list,call) {
        var url = '/sys/web/advertisement/displace.do';
        $utils.down(url,id,list,call);
    }


    /**
     * 删除广告位详情
     * @param $scope
     * @param ids
     * @param call
     */
    this.deleteAdvertisement = function (ids,call) {
        var url = '/sys/web/advertisement/delete/'+ids+'.do';
        $utils.delete(url,call);
    }

    /**
     * 保存广告位数据
     * @param advertisement
     * @param call
     */
    this.saveAdvertisement = function (advertisement,call) {
        var url = '/sys/web/advertisement/save.do';
        $utils.save(url,advertisement,call);
    }

    /**
     * 获取分页数据
     * @param $scope
     * @param call
     */
    this.pageNoticeList = function ($scope,call) {
        var url= '/sys/web/notice/page.do';
        var defaultParam = {
            sort:"top desc,createTime desc",
            section:$scope.section,
            available:$scope.available,
            clientType:$scope.clientType,
            page:$scope.pageIndex,
            rows:$scope.pageSize,
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.noticeList = response.list;
            $scope.total = response.total;
        }));

    }

    /**
     * 获取公告数据 根据id
     * @param $scope
     * @param id
     * @param call
     */
    this.getNotice = function ($scope,id,call) {
        var url = '/sys/web/notice/get/'+id+'.do';
        $utils.findOne(url,$utils.callback(call,function (response) {
            $scope.notice = response.data;
        }));
    }

    /**
     * 删除公告数据
     * @param ids
     * @param call
     */
    this.deleteNotice = function (ids,call) {
        var url = '/sys/web/notice/delete/'+ids+'.do';
        $utils.delete(url,call);
    }

    /**
     * 禁用网站公告信息
     * @param id
     */
    this.disableNotice = function (id,call) {
        console.log(1234567);
        var url = '/sys/web/notice/disable/'+id+'.do';
        $utils.disable(url,call);
    }

    this.topNotice = function (id,call) {
        var url = '/sys/web/notice/top/'+id+'.do';
        $utils.disable(url,call);
    }



    /**
     * 保存公告数据
     * @param notice
     * @param call
     */
    this.saveNotice = function (notice,call) {
        var url = '/sys/web/notice/save.do';
        $utils.save(url,notice,call);
    }

    /**
     * 获取网站推荐数据列表
     * @param $scope
     * @param call
     */
    this.loadRecommendList = function ($scope,call) {
        var url= '/sys/web/recommend/list.do';
        var defaultParam = {
            sort:"order_sort",
            clientType:$scope.clientType,
            section:$scope.section,
            available:$scope.available
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.recommendList = response.data;
        }));

    }

    /**
     * 获取推荐数据 根据id
     * @param $scope
     * @param id
     * @param call
     */
    this.getRecommend = function ($scope,id,call) {
        var url = '/sys/web/recommend/get/'+id+'.do';
        $utils.findOne(url,$utils.callback(call,function (response) {
            $scope.recommend = response.data;
        }));
    }

    /**
     * 禁用网站推荐信息
     * @param id
     * @param call
     */
    this.disableRecommend = function (id,call) {
        var url = '/sys/web/recommend/disable/'+id+'.do';
        $utils.disable(url,call);
    }

    /**
     * 获取模块可推荐的内容
     * @param id
     * @param call
     */
    this.getTargetList = function ($scope,call) {
        var url = '/sys/web/recommend/targetList.do';
        var defaultParam = {
            page:1,
            rows:10,
            clientType:1,
            name:$scope.key,
        }
        if($scope.recommend && $scope.recommend.section){
            defaultParam['section'] = $scope.recommend.section;
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.targetList = response.data;
        }));
    }

    /**
     * 获取模块可推荐的内容
     * @param id
     * @param call
     */
    this.getWaprecommendList = function ($scope,call) {
        var url = '/sys/web/recommend/targetList.do';
        var defaultParam = {
            page:1,
            rows:10,
            section:$scope.section,
            clientType:2,
            name:$scope.key,
        }
        $utils.query(url,defaultParam,$utils.callback(call,function (response) {
            $scope.targetList = response.data;
        }));
    }

    /**
     * 删除推荐数据
     * @param ids
     * @param call
     */
    this.deleteRecommend = function (ids,call) {
        var url = '/sys/web/recommend/delete/'+ids+'.do';
        $utils.delete(url,call);
    }

    /**
     * 保存推荐数据
     * @param notice
     * @param call
     */
    this.saveRecommend = function (notice,call) {
        var url = '/sys/web/recommend/save.do';
        $utils.save(url,notice,call);
    }

    /**
     * 向上移动推荐信息
     * @param id
     * @param call
     */
    this.upRecommend = function (id,list,call) {
        var url = '/sys/web/recommend/displace.do';
        $utils.up(url,id,list,call);
    }

    /**
     * 向下移动推荐信息
     * @param id
     * @param call
     */
    this.downRecommend = function (id,list,call) {
        var url = '/sys/web/recommend/displace.do';
        $utils.down(url,id,list,call);
    }
    
    /**
     * 获取App版本数据 根据id
     * @param $scope
     * @param id
     * @param call
     */
    this.getAppVersion = function ($scope,id,call) {
        var url = '/sys/app/version/get/'+id+'.do';
        $utils.findOne(url,$utils.callback(call,function (response) {
            $scope.appVersion = response.data;
        }));
    }
    
    /**
     * 保存App版本数据
     * @param appVersion
     * @param call
     */
    this.saveAppVersion = function (appVersion,call) {
        var url = '/sys/app/version/save.do';
        $utils.save(url,appVersion,call);
    }

})