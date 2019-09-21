/**
 * Created by wangsr on 2017/8/1.
 */

angular.module('app')
    .directive('uploadPicModal',function ($timeout,$uibModal,$log) {
        return {
            restrict: 'EA',
            scope:{
                selectedPic: '=', //@读属性值，=双向绑定，&用户函数
            },
            template: '<div>Hi there <span></span></div>',
            link: function(scope, element, attrs) {

            }
        }
    })