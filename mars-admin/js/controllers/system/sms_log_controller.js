/**
 * Created by lixl on 2017/7/23.
 */
var app = angular.module('app',['ui.bootstrap','service.system']);
app.controller('SmsLogController', function ($uibModal,$system,$scope) {
    var $ctrl = this;
    $ctrl.selfMotion = $scope.selfMotion;
    
    $system.loadSmsLogList($ctrl);

    $ctrl.pageIndex = 1;
    $ctrl.pageSize = 20;
    $ctrl.toPage = function (pIndex, pSize){
        $ctrl.pageIndex = pIndex;
        $ctrl.pageSize = pSize;
        $system.loadSmsLogList($ctrl);
    }

    $ctrl.query = function(){
        $ctrl.pageIndex = 1;
        $system.loadSmsLogList($ctrl);
    }

    $ctrl.export = function(){
        $ctrl.pageIndex = 1;
        $system.exportSmslog();
    }
    
    $ctrl.add = function (id) {
        $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'tpl/system/sms_send_add.html',
            controller: 'SmsSendAddController',
            controllerAs: '$ctrl',
            backdrop:'static',
            size: 'lg',
            resolve: {
                id: function () {
                    return id;
                },
                list:function(){
                    return $ctrl;
                },
                statusList:function () {
                    return $ctrl.codeList;
                }
            }
        });
    }
    
    $ctrl.import = function (id,audit) {
        $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'tpl/system/sms_send_import.html',
            controller: 'SmsSendImportController',
            controllerAs: '$ctrl',
            size: 'md',
            backdrop:'static',
            resolve: {
            	id: function () {
                    return id;
                },
                list:function(){
                    return $ctrl;
                },
                statusList:function () {
                    return $ctrl.codeList;
                }
            }
        });
    }
    
});


app.controller('SmsSendAddController', function ($uibModalInstance,$system,id,statusList,list,$scope,$utils){
    var $ctrl = this;
    $ctrl.log = {};
    /*编辑器宽度*/
	$scope.editorConfig = {
	    initialFrameWidth:675
	}
    
	$ctrl.sendAll='true';
    $ctrl.WindowsTitle = "新建发送短信消息";

    $ctrl.sendSms = function () {
    	console.log($ctrl.log);
    	if($ctrl.sendAll=='false'){
    		if(!$ctrl.log.phone){
        		$utils.alert("请输入手机号");
        		return;
    		}
    		console.log($ctrl.log.phone);
    		$ctrl.log.phone=$ctrl.log.phone.replace(/\ +/g,"")
    		console.log($ctrl.log.phone);
    		var strArr=$ctrl.log.phone.split("\n");
    		var myreg = /^1[34578][0-9]{9}$/;
    		for(var i=0;i<strArr.length;i++){
    			if(!myreg.test(strArr[i])) {
        			$utils.alert("手机号"+strArr[i]+"格式不正确");
            		return;
        		}
    		}
    		
    	}else{
    		$ctrl.log.hql="SELECT mobile FROM UserInfoEntity WHERE available=TRUE";
    		$ctrl.log.phone="";
    	}
    	$system.sendSms($ctrl.log,function (response) {
            if(response.code == 1){
                $uibModalInstance.close();
                $system.loadSmsLogList(list);
            }
        });
    };

    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
});

app.controller('SmsSendImportController', function ($uibModalInstance,$system,id,list,statusList,$utils) {
    var $ctrl = this;
    $ctrl.WindowsTitle = "导入发送短信消息";
    
    $ctrl.importFile = function () {
    	var form = new FormData();
    	var fileupload = document.getElementById("fileUpload").files[0];
    	if(!fileupload){
			$utils.alert("请选择导入文件");
    		return;
    	}
	    form.append('fileUpload',fileupload);
	     
        $system.importToSend(form,function(response){
        	if(response.code == 1){
                $uibModalInstance.close();
                $system.loadSmsLogList(list);
            }
        });
    };
    
    $ctrl.downLoad = function () {
    	$system.downLoadSmsImportTemplate();
   };
    
    //取消
    $ctrl.cancel = function () {
        $uibModalInstance.close();
    };
});
