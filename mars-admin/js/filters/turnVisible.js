/**
 * Created by tantx on 2017/12/2.
 */
angular.module('app')
    .filter('turnVisible',function($q,$http,$localStorage) {
    return function(value,list,keyName,visibleName) {
    	if(value || (typeof(value)=='number'&&value == 0)){
        	for(var i in list){
                var item = list[i];
                if(item[keyName] == value){
                    return item[visibleName];
                }
            }
        }
        return '--';
    }
})
