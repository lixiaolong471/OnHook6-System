/**
 * Created by Wangsr on 2017/7/24.
 */
angular.module('app')
 .directive('pagination',function ($timeout,$log) {
     return {
         restrict: 'EA', //E表示element, A表示attribute,C表示class，M表示commnent,即注释
         scope:{
             total: '=', //@读属性值，=双向绑定，&用户函数
             pageSize: '=',
             pageIndex: '=',
             pageOptions: "=",
             pageChange: '&'
         },
         templateUrl: 'tpl/component/pagination.html',
         link:function(scope, element, attrs){
             scope.maxSize = 5;
             scope.options = scope.pageOptions ||  [20,50,100];
             scope.selectedOption = scope.options[0];
             scope.changePageSize = function(){
                 scope.num = (scope.pageIndex -1) * scope.pageSize + 1;
                 scope.pageSize = scope.selectedOption;
                 scope.pageIndex =  parseInt(scope.num / scope.pageSize) + 1;
                 scope.pageChange({
                     pIndex:scope.pageIndex,
                     pSize: scope.pageSize
                 });
             }

             scope.changePage = function(){
                 scope.pageChange({
                     pIndex:scope.pageIndex,
                     pSize: scope.pageSize
                 });
             }

         }//DOM操作
     }
 })