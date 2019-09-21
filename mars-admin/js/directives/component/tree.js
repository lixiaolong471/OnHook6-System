/**
 * Created by Wangsr on 2017/9/1.
 */
angular.module('app')
.directive('tree', function ($utils) {
    return {
        restrict: 'EA', //E表示element, A表示attribute,C表示class，M表示commnent,即注释
        replace     : true,
        require     : "^?ngModel",
        scope:{
            treeData: '=', //@读属性值，=双向绑定，&用户函数
            ngModel     :"=",       /*同步到组件外层selected属性上 sync to outer sphere component attrs*/
        },
        templateUrl: 'tpl/component/tree.html',
        link:function (scope,element,attrs,ngModel) {
            // console.log(11, element);
            /**
             *监听options选项的改变设置ngModel的值
             *设置的ngModel以Number返回
             */
            scope.$watch('treeData',function(n,o){
                if(n){
                    // 默认设置ngmodel的值
                    if(!ngModel.$viewValue){
                        ngModel.$setViewValue("");
                    }
                    scope.$watch('ngModel',function(n,o){
                        if(n){
                            if(ngModel && ngModel!=""){
                                scope.getLeafName(scope.ngModel,'child',scope.treeData,'name')
                                console.log(333, scope.selectedName)
                            }
                        }
                    })
                }
            })

            $(document).click(function(event){
                if($(event.target).parents(".treeview").length == 0){
                    scope.isOpen = false;
                    scope.$apply();
                }
            });

            scope.openDropdown= function (e) {
                e.stopPropagation();
                scope.isOpen = !scope.isOpen;
            }

            scope.openChild = function (item,e) {
                e.stopPropagation();
                if(item.child && item.child.length >0){
                    item.close = !item.close;
                }
            }

            scope.selectPeopleNode = function(item,e){
                e.stopPropagation();
                if(item.name){
                    scope.isOpen = false;
                    scope.selectedName = item.name;
                    ngModel && ngModel.$setViewValue(item.id === null ? null : item.id);
                }
            }
            scope.getLeafName = function (id,key,arr,attr) {
                for(var i=0; i<arr.length;i++){
                    if(arr[i].id == id && arr[i].leaf == true){
                         scope.selectedName =  arr[i][attr];
                         break;
                    }
                    if(arr[i][key]){
                        scope.getLeafName(id,'child',arr[i][key],attr);
                    }

                }
            }
        }
    }
})