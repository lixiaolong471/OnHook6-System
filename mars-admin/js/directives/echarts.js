/**
 * Created by lixl on 2017/8/30.
 */
/**
 * Created by lixl on 2017/8/1.
 */
(function() {
    'use strict';
    var app = angular.module('app');
    // console.log("图表指令已被加载...");
    app.directive('echarts',function($timeout) {
        return {
            restrict:'AE',
            scope:{
                option:'@',
                load:'&'
            },
            template:'<div style="height:300px;width: auto;"></div>',
            controller: function($scope){
                // console.log("图表指令controller已被加载...");
            },
            link:function(scope,element,attr){
                // console.log("图表指令已被调用...");
                var chart =  element.find('div')[0];
                var parent = element['context'];
                chart.style.width =parent.clientWidth+'px';
                chart.style.height =parent.clientHeight+'px';
                scope.load({
                    call:function () {
                        setTimeout(function () {
                            var option = scope.$eval(scope.option);
                            var myChart = echarts.init(chart);
                            myChart.setOption(option);

                        },500)
                    }
                });
            }
        }
    });
})();
