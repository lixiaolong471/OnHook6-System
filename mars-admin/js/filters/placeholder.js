/**
 * Created by lixl on 2017/7/24.
 */
'use strict';
angular.module('app')
    .filter('placeholder', function() {
        return function(str,text) {
            if(str){
                return str;
            }else if(text){
                return text;
            }else{
                return '--';
            }

        }
    });