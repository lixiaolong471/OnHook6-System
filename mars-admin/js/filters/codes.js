/**
 * Created by lixl on 2017/7/31.
 */
angular.module('app')
    .filter('codes',function($q,$http,$localStorage) {
    return function(value,key) {
        var codeList = $localStorage[key];
        if(value || (typeof(value)=='number'&&value == 0)){
        	//添加1,2,3解释
        	if(typeof(value)=='string'&& value.indexOf(",")>-1){
        		var arrObj=value.split(",");
        		var valueText="";
        		for(var m  in arrObj){
        			for(var i in codeList){
                        var item = codeList[i];
                        if(item.value == arrObj[m]){
                        	valueText+=(item.name+",");
                        	break;
                        }
                    }
        		}
        		if(valueText.length>0){
        			return valueText.substring(0,valueText.length-1);
        		}
        		return valueText;
        	}else{
        		for(var i in codeList){
                    var item = codeList[i];
                    if(item.value == value){
                        return item.name;
                    }
                }
        	}
        }
        return '--';
    }
});
