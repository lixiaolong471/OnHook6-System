/**
 * Created by lixl on 2017/7/18.
 */
angular.module('service.utils', ['toaster']).service('$utils',function ($http,$uibModal,$location,$window,$log,toaster) {

    this.message = {
            save:{
                success:"保存成功",
                error:"保存失败",
            },
            delete:{
                success:"删除成功",
                error:"删除失败",
            },
            modify:{
                success:"修改成功",
                error:"修改失败",
            },
            disable:{
                success:"操作成功",
                error:"操作失败",
            },
            top:{
                success:"操作成功",
                error:"操作失败",
            },
            audit:{
                success:"操作成功",
                error:"操作失败",
            },
            exOperate:{
                success:"操作成功",
                error:"操作失败",
            },
            query:{
                error:"加载数据失败",
            }
        }

    /**
     * callback方法合并
     * @param call
     * @param back
     * @returns {Function}
     */
    this.callback = function (call,back) {
        return function (response) {
            if(back){
                back(response);
            }
            if(call){
                call(response);
            }

        }
    }

    /**
     * 两个对象指定属性对比
     * @param one
     * @param two
     * @param attr
     * @returns {boolean}
     */
    this.eq = function(one,two,attr){
        if(one[attr] === two[attr]){
            return true;
        }else{
            false;
        }
    }

    this.singleSelect = function(item,list,attr){
        if(!item.selected){
            list.push(item[attr]);
            item.selected = true;
        }else{
            for (var i = 0; i < list.length; i++) {
                if(list[i] == item[attr]){
                    list.splice(i,1);
                    break;
                }

            }
        }
    }



    this.request = function(url,method,call,message,data){
        var utils = this;

        var para = {
            method: method,
            url:url
        }
        if(method === 'post'){
            para.data = data;
        }
        utils.dataLoading();
        $http(para).then(function (response) {
            utils.removeLoading();
            if(call){
                call(response.data);
            }
            if(response.data.code == 1) {
                if (message.success) {
                    console.log(123445555);
                    console.log('code=' + response.data.code);
                    toaster.pop("success", "提示", message.success);
                }
            }else if(response.data.code == -10){
                $location.path('/login')
            } else if(response.data.code == 0){
                toaster.pop("error", "提示", response.data.message);
            } else{
                if(message.error){
                    toaster.pop("error", "提示", message.error);
                }
            }
        })
    }

    /**
     * GET方法请求数据
     * @param url
     * @param message
     * @param call
     */
    this.get = function (url,message,call) {
        this.request(url,'get',call,message,null);
    }

    /**
     * POST方法请求数据
     * @param url
     * @param message
     * @param data
     * @param call
     */
    this.post = function (url,message,data,call) {
        // console.log(message);
        this.request(url,'post',call,message,data);
    }

    /**
     * 查询列表数据
     * @param url
     * @param data
     * @param call
     */
    this.query = function (url,data,call) {
        data.page = data.page || 1;
        data.rows = data.rows || 20;
        this.post(url,this.message.query,data,call);
    }

    /**
     * 保存数据
     * @param url
     * @param data
     * @param call
     */
    this.save = function (url,data,call) {
        // console.log(message);
        this.post(url,this.message.save,data,call);
    }

    /**
     * 保存数据
     * @param url
     * @param data
     * @param call
     */
    this.update = function (url,data,call) {
        // console.log(message);
        this.post(url,this.message.modify,data,call);
    }

    /**
     * 删除数据
     * @param url
     * @param call
     */
    this.delete = function (url,call) {
        // console.log(message);
        this.get(url,this.message.delete,call);
    }

    /**
     * 禁用数据
     */

    this.disable = function (url,call) {
        this.get(url,this.message.disable,call);
    }

    /**
     * 置顶数据
     */
    this.top = function (url,call) {
        this.get(url,this.message.top,call);
    }
    
    /**
     * 操作
     */
    this.exOperate = function (url,call) {
        this.get(url,this.message.exOperate,call);
    }

    /**
     * 上移操作
     * @param url
     * @param id
     * @param list
     * @param call
     */
    this.up = function (url,id,list,call) {
        var last = 0;
        for(var i=0;i<list.length;i++){
            var data = list[i];
            if(data["id"] == id){
                if(i == 0){
                    $utils.alert("已经是最顶端了");
                    return;
                }
                last = list[i-1];
                var temp = last;
                list[i-1] =  list[i];
                list[i] =  temp;
                break;
            }
        }
        this.post(url,this.message.top,{"id1":id,"id2":last.id},call);
    }

    /**
     * 下移操作
     * @param url
     * @param id
     * @param list
     * @param call
     */
    this.down = function (url,id,list,call) {
        var next = 0;
        for(var i=0;i<list.length;i++){
            var data = list[i];
            console.log((i+1));
            if(data["id"] == id){

                console.log(list.length);
                if((i+1) == list.length){
                    $utils.alert("已经是最底端了");
                    return;
                }
                next = list[i+1];
                var temp = next;
                list[i+1] =  list[i];
                list[i] =  temp;
                break;
            }
        }
        console.log(next);
        this.post(url,this.message.top,{"id1":id,"id2":next["id"]},call);
    }


    /**
     * 审核
     * @param url
     * @param call
     */
    this.audit = function (data,url,call) {
        this.post(url,this.message.audit,data,call);
    }

    /**
     * 查找单个数据
     * @param url
     * @param call
     */
    this.findOne = function (url,call) {
        this.get(url,this.message.query,call);
    }
    
    this.upload = function (url,message,data,call){
    	var utils = this;
    	var para={
	            method:'POST',
	            url:url,
	            data: data,
	            headers: {'Content-Type':undefined},
	            transformRequest: angular.identity 
            } 
        utils.dataLoading();
    	$http(para).then(function (response) {
        	utils.removeLoading();
    		console.log(response);
            if(call){
                call(response.data);
            }
            if(response.data.code == 1) {
                if (message.success) {
                    console.log('code=' + response.data.code);
                    toaster.pop("success", "提示", message.success);
                }
            }else if(response.data.code == -10){
                $location.path('/login')
            }else{
                if(message.error){
                    toaster.pop("error", "提示", message.error);
                }
            }
        })
    }
    
    
    this.myexport = function(url,data,message,fileName,call) {
    	var utils = this;
        var para={
            url: url,
            method: "POST",
            data: data,
            responseType:'blob'
            //responseType: 'arraybuffer'
        };
        utils.dataLoading();
        $http(para).then(function (response, status, headers, config) {
        	utils.removeLoading();
        	console.log(response);
            if(call){
                call(response);
            }
        	if(response.status==200){
        		var success=false;
        		try
                {
                    // Try using msSaveBlob if supported
                    console.log("Trying saveBlob method ...");
                    var blob = new Blob([response.data], { type: 'text/html' });
                    if(navigator.msSaveBlob)
                        navigator.msSaveBlob(blob, fileName);
                    else {
                        // Try using other saveBlob implementations, if available
                        var saveBlob = navigator.webkitSaveBlob || navigator.mozSaveBlob || navigator.saveBlob;
                        if(saveBlob === undefined) throw "Not supported";
                        saveBlob(blob, fileName);
                    }
                    console.log("saveBlob succeeded");
                    success = true;
                } catch(ex)
                {
                    console.log("saveBlob method failed with the following exception:");
                    console.log(ex);
                }

        		if(!success){
        			console.log("others ...");
                    var url = (window.URL || window.webkitURL).createObjectURL(new Blob([response.data]));
                    var a = document.createElement('a');
                    document.body.appendChild(a); //此处增加了将创建的添加到body当中
                    a.href = url;
                    a.download = fileName;
                    a.target = '_blank';
                    a.click();
                     //将a标签移除
                    setTimeout(a.remove(), 500);
        		}
        		
        	}else{
        		if(message.error){
                    toaster.pop("error", "提示", message.error);
                }
        	}
        });
    };
    
    this.dataLoading = function (options) {
        this.randerLoadHtml(options);
    }

    this.removeLoading = function (options) {
        $(".load-tips-box").remove();
    }

    this.randerLoadHtml = function () {
        var defaults = {
            parentWrap:"body",
            positionName:'fixed',
            bg:'rgba(0,0,0,0.5);'
        }
        var options = $.extend(defaults, options);
        var loadHtml = '<div class="load-tips-box" style="position:' + options.positionName + ';background:' + options.bg + '">' +
            '<img src="/img/icon/loading.gif" class="load-icon">' +
            '</div>';
        if(options.parentWrap){
            $(defaults.parentWrap).css('position','relative');
        }
        $(options.parentWrap).append(loadHtml);
    }
    //所有提示信息框
    this.alertMsg = function(options){
        $uibModal.open({
            windowClass : 'cm-modal tips-modal',
            templateUrl : 'tpl/component/tips_modal.html',
            backdrop    : 'static',
            size: 'sm',
            controller  : function ($scope, $uibModalInstance,$timeout,$location) {
                var defaults = {
                    headerText  : '温馨提示', //提示标题
                    msg         : '确定删除该记录吗？',   //提示内容
                    yes         : '确 定',
                    no          : '取 消',
                    ok          : null,        //确定的回调
                    cancel      : null,    //取消的回调
                    type        :'confirm',//弹窗类型，confirm:操作确认型弹窗，alert:提示型弹窗
                    iconClass    :'fa fa-warning'//提示图标的样式,fa fa-warning:操作确认型弹窗，success:成功样式，err:出错样式
                };
                $scope.options = $.extend(defaults, options);
                // if($scope.options.type == "confirm"){
                //     $scope.options.iconClass = "fa fa-warning";
                // } else {
                //     $scope.options.iconClass = "glyphicon-ok-sign";
                // }
                $scope.ok = function() {
                    /*确定的回调函数*/
                    $scope.options.ok && $scope.options.ok();
                    $uibModalInstance.close();

                };
                $scope.cancel = function() {
                    /*取消的回调函数*/
                    $scope.options.cancel && $scope.options.cancel();
                    $uibModalInstance.close();
                };
            },
            resolve: {}
        })
    }


    /**
     * 普通消息弹窗
     * @param msg
     */
    this.alert = function(msg){
        this.alertMsg({msg:msg,type:"alert","iconClass":"glyphicon glyphicon-exclamation-sign"});
    }

    /**
     * 确认消息弹窗
     * @param msg
     * @param ok
     */
    this.confirm = function(msg,ok){
        this.alertMsg({msg:msg,ok:ok});
    }

    /**
     * 成功消息弹窗
     * @param msg
     */
    this.success = function(msg){
        this.alertMsg({msg:msg,type:"alert",iconClass:"glyphicon glyphicon-ok"});
    }

    /**
     * 失败消息弹窗
     * @param msg
     */
    this.error = function(options){
        this.alertMsg({msg:msg,type:"alert",iconClass:"glyphicon glyphicon-remove-sign"});
    }

    /*表格单选*/
    this.radioSelect = function (item,selectedData,dataArr,fieldId,isCheckAll,$ctrl) {
        console.log(111);
        item.selected = !item.selected;
        if(item.selected){
            selectedData.push(item);
            if(selectedData.length == dataArr.length){
                $ctrl[isCheckAll] = true;
            }
        }else{
            for (var i = 0; i < selectedData.length; i++) {
                if(selectedData[i][fieldId] == item[fieldId]){
                    selectedData.splice(i,1);
                    break;
                }
            }
        }
    }

    /*表格多选*/
    this.mtiSelect = function(selectedData,dataArr,fieldId,isCheckAll,$ctrl){
        console.log(222);
        $ctrl[isCheckAll] = !$ctrl[isCheckAll];
        if($ctrl[isCheckAll]){
            $.each(dataArr,function (i,item) {
                if($.inArray(item[fieldId], selectedData < 0)){
                    item.selected = true;
                    selectedData.push(item);
                }
            })
        }else{
            $.each(dataArr,function (i,item) {
                item.selected = false;
                selectedData.length = 0;
            })
        }
    }

    /**
     *判断选中和添加一些提示的逻辑
     *@param:obj(Object) 配置参数
     * obj.type: delete:删除，export:导出
     */
    this.operate = function(obj){
        if(obj.type == 'delete'){
            obj.equalZero = "请选择要删除的记录";
            obj.gtZero = "确认要删除选中的记录?";
        }
        if(obj.type == 'export'){
            obj.equalZero = "请选择要导出的记录";
            obj.gtZero = "确认要导出选中的记录?";
        }
        if(obj.dataList && obj.dataList.length >0){
            if(obj.selectedList.length == 0){
                obj.options.msg = obj.equalZero;
            }
            if(obj.selectedList.length > 0){
                obj.options.msg = obj.gtZero;
            }
            this.alertMsg(obj.options);
            return this.getTagFiled(obj.selectedList,obj.field);
        }else{
            console.log('not data');
        }
    }
    
    this.showPicInModal=function(url){
    	if(url){
            $uibModal.open({
                windowClass : 'cm-modal look-pic-modal',
                templateUrl : 'tpl/component/look_big_pic.html',
                backdrop    : 'static',
                controller  : function ($scope, $uibModalInstance,$timeout,$location) {
                    var rotatedNum = 0;
                    $scope.imgUrl = url;

                    $scope.turnClockWise = function(){
                        rotatedNum++;
                        var degNum = rotatedNum*90;
                        $(".big-pic-wrap img").css("transform","rotate("+degNum+"deg)");
                    };
                    $scope.turnAntiClickWise = function(){
                        rotatedNum--;
                        var degNum = rotatedNum*90;
                        $(".big-pic-wrap img").css("transform","rotate("+degNum+"deg)");
                    };


                    $scope.ok = function() {
                        /*确定的回调函数*/
                        $uibModalInstance.close();
                    };
                    $scope.cancel = function() {
                        /*取消的回调函数*/
                        $uibModalInstance.close();
                    };
                },
                resolve: {}
            })
        }
    }

    this.lookBigPic = function (url, e) {
        if(e){
            e.stopPropagation();
        }
    	var $myscope=this;
        if(url&&url.indexOf("smallimage") >= 0){
        	var param={"smallUrl":url}
        	this.post("/sys/system/attachment/getResourceUrl.do",this.message.query,param,function(response){
        		if(response.code==1&&response.data&&response.data.resourceUrl){
        			$myscope.showPicInModal(response.data.resourceUrl)
        		}else{
        			$myscope.showPicInModal(url);
        		}
        	});
        	return;
        }
        $myscope.showPicInModal(url);
    }



    /**
     *获取数据指定字段：如id,以字符串','的形式分隔返回
     *@param:datalist(Array) 目标数组
     *@param:field(String) 字段名称默认为ids
     */
    this.getTagFiled = function(datalist,field){
        var field = field || 'ids';
        var result = [];
        $.each(datalist, function (i,item) {
            result.push(item[field]);
        });
        return result.join(',');
    }

    /**
     * table删除记录后刷新当前页
     */
    this.renderTable = function(selectedData, dataList,$ctrl){
        if(selectedData.length == dataList.length){
            if($ctrl.pageIndex > 1){
                $ctrl.pageIndex = $ctrl.pageIndex - 1;
            }
        }

    }

    /**
     * tree中根据id获取上级的名称/根据id获取name
     *
     */
    this.getParentName = function (id,key,arr,attr) {
        if(arr){
            for(var i in arr){
                if(arr[i].id == id){
                    return arr[i][attr];
                }
                else {
                    if(arr[i][key]){
                        return this.getParentName(id,'child',arr[i][key],attr)
                    }
                }
            }
        }
    }

    /**
     * tree中根据id获取name
     *
     */
    this.getLeafName = function (id,key,arr,attr) {
        for(var i=0; i<arr.length;i++){
            console.log(888, arr[i].id, arr[i].name);
            if(arr[i].id == id && arr[i].leaf == true){
                return  arr[i][attr];
            }
            if(arr[i][key]){
                return this.getLeafName(id,'child',arr[i][key],attr);
            }

        }
    }


    /**
     * 生成num的数组
     * @param num
     * @returns {Array}
     */
    this.getNumber = function(num) {
        return new Array(num);
    }


    /*用于强制刷新scope*/
    this.saveApply = function($scope, fn) {
        var phase = $scope.$root.$$phase;
        if(phase == '$apply' || phase == '$digest') {
            if(fn && (typeof(fn) === 'function')) {
                fn();
            }
        } else {
            $scope.$apply(fn);
        }
    }


    this.export = function (url) {
        $window.location = url;
    }
    
    this.downLoad = function (url) {
        $window.location = url;
    }

    /**
     * 日期格式化
     * @param fmt
     * @returns {*}
     * @constructor
     */
    this.dateformat = function (date,fmt) {

        if(!date){
            return '';
        }

        var o = {
            "M+": date.getMonth() + 1, //月份
            "d+": date.getDate(), //日
            "h+": date.getHours(), //小时
            "m+": date.getMinutes(), //分
            "s+": date.getSeconds(), //秒
            "q+": Math.floor((date.getMonth() + 3) / 3), //季度
            "S": date.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    //2017-11-15 12:00:00 转换为 2017-11-15 00:00:00
    /**
     * 日期时分秒设置为 00:00:00秒，其他不变
     *
     * 2017-11-15 12:00:00 转换为 2017-11-15 00:00:00
     */
    this.getStartDate = function(date){
        if(date && date.length > 10){
            date = date.substring(0,10)+" 00:00:00.000";
            console.log(date);
        }
        return date;
    }

    this.getEndDate = function(date){
        if(date && date.length > 10){
            date = date.substring(0,10)+" 23:59:59.000";
            console.log(date);
        }
        return date;
    }

    /**
     * 解析yyyy-MM-dd字符串格式的日期
     * @param date
     * @returns {*}
     */
    this.dateParse = function (date) { //author: meizz
        return date? new Date(Date.parse(date.replace(/-/g,   "/"))):'';
    }

    /**
     * 判断<code>arr</code>集合中是否存在<code>obj</code>元素
     * @param arr
     * @param obj
     * @returns {boolean}
     */
    this.contains = function (arr, obj) {
        for(var i in arr){
            if (arr[i] == obj) {
                return true;
            }
        }
        return false;
    }

    /**
     * 数组 删除指定元素
     * @param arr
     * @param val
     */
    this.arrayRemove = function(arr, val) {
        for(var i=0; i<arr.length; i++) {
            if(arr[i] == val) {
                arr.splice(i, 1);
                break;
            }
        }
    }
    
    this.getBirthByIdCard=function(idCard){
		//获取出生日期
		birth=idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
		return birth;
    }
    
    this.getSexByIdCard=function(idCard){
		//获取性别
		if (parseInt(idCard.substr(16, 1)) % 2 == 1) {
		//男
		return "男";
		} else {
		//女
		return "女";
		}
    }
    
    this.getAgeByIdCard=function(idCard){
		//获取年龄
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var day = myDate.getDate();
		var age = myDate.getFullYear() - idCard.substring(6, 10) - 1;
		if (idCard.substring(10, 12) < month || idCard.substring(10, 12) == month && idCard.substring(12, 14) <= day) {
			age++;
		}
		return age;
    }

    this.fixTableBox = function (obj) {
        if($(".table-responsive").hasClass("fixed-table-container")){
            var height = $(window).height();
            var height1 = $(".table-responsive").offset().top;
            var height2 = $(".cm-pager").outerHeight();
            var tableBoxH = height - height1 - height2-20;
            var tableBoxW = $(".query-box").outerWidth() + 20;
            $(".wrapper .panel").css({
                'margin-bottom':"0",
                'overflow':'hidden'
            })
            $(".app-content-full").css({
                'overflow':'hidden'
            })
            $(".table-responsive").css(
                {'width':tableBoxW,
                    'height': tableBoxH
                })
            fixTable($(".table-responsive")[0]);
        }
    }
    this.fixTableWrap = function () {
        this.fixTableBox();
        var _this = this;
        $(window).resize(function() {
            _this.fixTableBox();
        });
    }
});