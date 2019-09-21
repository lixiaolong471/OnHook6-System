/**
 * Created by Wangsr on 2017/8/4.
 */

angular.module('app')
    .directive('noData',function ($timeout,$log) {
        return {
            restrict: 'EA', //E表示element, A表示attribute,C表示class，M表示commnent,即注释
            scope:{
                list: '=', //@读属性值，=双向绑定，&用户函数
            },
            template: '<div class="no-data" ng-show="list.length <= 0">' +
            '<i class="fa fa-smile-o"></i>' +
            '<p>没有数据</p>' +
            '</div>',
            link:function(scope, element, attrs){

            }//DOM操作
        }
    })