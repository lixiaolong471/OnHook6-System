var music;

var uploadMusicList=[],
uploadFile,
onlineMusic;
window.onload = function(){
    music.initTabs();
    addOkListener();
    initUpload();
};
function Music() {
    this.init();
}

    var pages = [],
        panels = [],
        selectedItem = null;
    Music.prototype = {
        total:70,
        pageSize:10,
        dataUrl:"http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.common",
        playerUrl:"http://box.baidu.com/widget/flash/bdspacesong.swf",

        init:function () {
            var me = this;
            domUtils.on($G("J_searchName"), "keyup", function (event) {
                var e = window.event || event;
                if (e.keyCode == 13) {
                    me.dosearch();
                }
            });
            domUtils.on($G("J_searchBtn"), "click", function () {
                me.dosearch();
            });
        },
        initTabs:function(){
        	var me = this;
        	var tabs = $G('tabHeads').children;
            for (var i = 0; i < tabs.length; i++) {
                domUtils.on(tabs[i], "click", function (e) {
                    var target = e.target || e.srcElement;
                    me.setTabFocus(target.getAttribute('data-content-id'));
                });
            }
            $(tabs[0]).click();
        },
        setTabFocus:function(id){
        	var me = this;
        	if(!id) return;
            var i, bodyId, tabs = $G('tabHeads').children;
            for (i = 0; i < tabs.length; i++) {
                bodyId = tabs[i].getAttribute('data-content-id');
                if (bodyId == id) {
                    domUtils.addClass(tabs[i], 'focus');
                    domUtils.addClass($G(bodyId), 'focus');
                } else {
                	$(tabs[i]).removeClass('focus');
                	$($G(bodyId)).removeClass('focus');
                }
            }
            switch (id) {
                case 'online':
                	onlineMusic = onlineMusic || new OnlineMusic('musicList');
                	onlineMusic.reset();
                    break;
                case 'upload':
                	uploadFile.refresh();
                break;
            }
        },
        callback:function (data) {
            var me = this;
            me.data = data.song_list;
            console.log(me);
            setTimeout(function () {
                $G('J_resultBar').innerHTML = me._renderTemplate(data.song_list);
            }, 300);
        },
        dosearch:function () {
            var me = this;
            selectedItem = null;
            var key = $G('J_searchName').value;
            if (utils.trim(key) == "")return false;
            key = encodeURIComponent(key);
            me._sent(key);
        },
        doselect:function (i) {
            var me = this;
            if (typeof i == 'object') {
                selectedItem = i;
            } else if (typeof i == 'number') {
                selectedItem = me.data[i];
            }
        },
        onpageclick:function (id) {
            var me = this;
            for (var i = 0; i < pages.length; i++) {
                $G(pages[i]).className = 'pageoff';
                $G(panels[i]).className = 'paneloff';
            }
            $G('page' + id).className = 'pageon';
            $G('panel' + id).className = 'panelon';
        },
        listenTest:function (elem) {
            var me = this,
                view = $G('J_preview'),
                is_play_action = (elem.className == 'm-try'),
                old_trying = me._getTryingElem();

            if (old_trying) {
                old_trying.className = 'm-try';
                view.innerHTML = '';
            }
            if (is_play_action) {
                elem.className = 'm-trying';
                view.innerHTML = me._buildMusicHtml(me._getUrl(true));
            }
        },
        _sent:function (param) {
            var me = this;
            $G('J_resultBar').innerHTML = '<div class="loading"></div>';

            utils.loadFile(document, {
                src:me.dataUrl + '&query=' + param + '&page_size=' + me.total + '&callback=music.callback&.r=' + Math.random(),
                tag:"script",
                type:"text/javascript",
                defer:"defer"
            });
        },
        _removeHtml:function (str) {
            var reg = /<\s*\/?\s*[^>]*\s*>/gi;
            return str.replace(reg, "");
        },
        _getUrl:function (isTryListen) {
            var me = this;
            var param = 'from=tiebasongwidget&url=&name=' + encodeURIComponent(me._removeHtml(selectedItem.title)) + '&artist='
                + encodeURIComponent(me._removeHtml(selectedItem.author)) + '&extra='
                + encodeURIComponent(me._removeHtml(selectedItem.album_title))
                + '&autoPlay='+isTryListen+'' + '&loop=true';
            return  me.playerUrl + "?" + param;
        },
        _getTryingElem:function () {
            var s = $G('J_listPanel').getElementsByTagName('span');

            for (var i = 0; i < s.length; i++) {
                if (s[i].className == 'm-trying')
                    return s[i];
            }
            return null;
        },
        _buildMusicHtml:function (playerUrl) {
            var html = '<embed class="BDE_try_Music" allowfullscreen="false" pluginspage="http://www.macromedia.com/go/getflashplayer"';
            html += ' src="' + playerUrl + '"';
            html += ' width="1" height="1" style="position:absolute;left:-2000px;"';
            html += ' type="application/x-shockwave-flash" wmode="transparent" play="true" loop="false"';
            html += ' menu="false" allowscriptaccess="never" scale="noborder">';
            return html;
        },
        _byteLength:function (str) {
            return str.replace(/[^\u0000-\u007f]/g, "\u0061\u0061").length;
        },
        _getMaxText:function (s) {
            var me = this;
            s = me._removeHtml(s);
            if (me._byteLength(s) > 12)
                return s.substring(0, 5) + '...';
            if (!s) s = "&nbsp;";
            return s;
        },
        _rebuildData:function (data) {
            var me = this,
                newData = [],
                d = me.pageSize,
                itembox;
            for (var i = 0; i < data.length; i++) {
                if ((i + d) % d == 0) {
                    itembox = [];
                    newData.push(itembox)
                }
                itembox.push(data[i]);
            }
            return newData;
        },
        _renderTemplate:function (data) {
            var me = this;
            if (data.length == 0)return '<div class="empty">' + lang.emptyTxt + '</div>';
            data = me._rebuildData(data);
            var s = [], p = [], t = [];
            s.push('<div id="J_listPanel" class="listPanel">');
            p.push('<div class="page">');
            for (var i = 0, tmpList; tmpList = data[i++];) {
                panels.push('panel' + i);
                pages.push('page' + i);
                if (i == 1) {
                    s.push('<div id="panel' + i + '" class="panelon">');
                    if (data.length != 1) {
                        t.push('<div id="page' + i + '" onclick="music.onpageclick(' + i + ')" class="pageon">' + (i ) + '</div>');
                    }
                } else {
                    s.push('<div id="panel' + i + '" class="paneloff">');
                    t.push('<div id="page' + i + '" onclick="music.onpageclick(' + i + ')" class="pageoff">' + (i ) + '</div>');
                }
                s.push('<div class="m-box">');
                s.push('<div class="m-h"><span class="m-t">' + lang.chapter + '</span><span class="m-s">' + lang.singer
                    + '</span><span class="m-z">' + lang.special + '</span><span class="m-try-t">' + lang.listenTest + '</span></div>');
                for (var j = 0, tmpObj; tmpObj = tmpList[j++];) {
                    s.push('<label for="radio-' + i + '-' + j + '" class="m-m">');
                    s.push('<input type="radio" id="radio-' + i + '-' + j + '" name="musicId" class="m-l" onclick="music.doselect(' + (me.pageSize * (i-1) + (j-1)) + ')"/>');
                    s.push('<span class="m-t">' + me._getMaxText(tmpObj.title) + '</span>');
                    s.push('<span class="m-s">' + me._getMaxText(tmpObj.author) + '</span>');
                    s.push('<span class="m-z">' + me._getMaxText(tmpObj.album_title) + '</span>');
                    s.push('<span class="m-try" onclick="music.doselect(' + (me.pageSize * (i-1) + (j-1)) + ');music.listenTest(this)"></span>');
                    s.push('</label>');
                }
                s.push('</div>');
                s.push('</div>');
            }
            t.reverse();
            p.push(t.join(''));
            s.push('</div>');
            p.push('</div>');
            return s.join('') + p.join('');
        },
        exec:function () {
            var me = this;
            if (selectedItem == null)   return;
            $G('J_preview').innerHTML = "";
            editor.execCommand('music', {
                url:me._getUrl(false),
                width:400,
                height:95
            });
        }
        
    };

    /*初始化上传标签*/
    function initUpload(){
        uploadFile = new UploadFile('queueList');
    }
    
    function findFocus( id, returnProperty ) {
        var tabs = $G( id ).children,
                property;
        for ( var i = 0, ci; ci = tabs[i++]; ) {
            if ( ci.className=="focus" ) {
                property = ci.getAttribute( returnProperty );
                break;
            }
        }
        return property;
    }
    
    function insertUpload(){
    	var me = this;
    	var videoObjs=[],
        uploadDir = editor.getOpt('musicUrlPrefix'),
        width = $G('upload_width').value || 420,
        height = $G('upload_height').value || 280,
        align = findFocus("upload_alignment","name") || 'none';
        for(var key in uploadMusicList) {
            var file = uploadMusicList[key];
            videoObjs.push({
                url: uploadDir + file.url,
                width:width,
                height:height,
                align:align
            });
        }
        var count = uploadFile.getQueueCount();
        if (count) {
            $('.info', '#queueList').html('<span style="color:red;">' + '还有2个未上传文件'.replace(/[\d]/, count) + '</span>');
            return false;
        } else {
        	addMusicEdit(videoObjs);
        }
    }
    
    function addMusicEdit(addJson){
    	for(var s in addJson){
    		editor.execCommand('music', {
                url:addJson[s].url,
                width:400,
                height:95
            });
    	}
    }
/**
     * 监听确认和取消两个按钮事件，用户执行插入或者清空正在播放的视频实例操作
     */
    function addOkListener(){
        dialog.onok = function(){
            $G("J_preview").innerHTML = "";
            var currentTab =  findFocus("tabHeads","tabSrc");
            switch(currentTab){
	            case "search":
	                return music.exec();
	                break;
	            case "upload":
	                return insertUpload();
	                break;
	            case "online":
	            	var lists = onlineMusic.getInsertList();
	            	if(lists) {
	            		addMusicEdit(lists);
	                    //remote && editor.fireEvent("catchRemoteImage");
	                }
	                break;
	        }
        };
        dialog.oncancel = function(){
            $G("J_preview").innerHTML = "";
        };
    }
    
    /* 获取对齐方式 */
    function getAlign(){
        var align = $G("align").value || 'none';
        return align == 'none' ? '':align;
    }
    
    /*=============================================================================*/
    /* 在线图片 */
    function OnlineMusic(target) {
        this.container = utils.isString(target) ? document.getElementById(target) : target;
        this.init();
    }
    OnlineMusic.prototype = {
        init: function () {
            this.reset();
            this.initEvents();
        },
        /* 初始化容器 */
        initContainer: function () {
            this.container.innerHTML = '';
            this.list = document.createElement('ul');
            this.clearFloat = document.createElement('li');

            domUtils.addClass(this.list, 'list');
            domUtils.addClass(this.clearFloat, 'clearFloat');

            this.list.appendChild(this.clearFloat);
            this.container.appendChild(this.list);
        },
        /* 初始化滚动事件,滚动到地步自动拉取数据 */
        initEvents: function () {
            var _this = this;

            /* 滚动拉取图片 */
            domUtils.on($G('musicList'), 'scroll', function(e){
                var panel = this;
                if (panel.scrollHeight - (panel.offsetHeight + panel.scrollTop) < 10) {
                    _this.getImageData();
                }
            });
            /* 选中图片 */
            domUtils.on(this.container, 'click', function (e) {
                var target = e.target || e.srcElement,
                    li = target.parentNode;

                if (li.tagName.toLowerCase() == 'li') {
                    if (domUtils.hasClass(li, 'selected')) {
                        domUtils.removeClasses(li, 'selected');
                    } else {
                        domUtils.addClass(li, 'selected');
                    }
                }
            });
        },
        /* 初始化第一次的数据 */
        initData: function () {

            /* 拉取数据需要使用的值 */
            this.state = 0;
            this.listSize = editor.getOpt('musicManagerListSize');
            this.listIndex = 0;
            this.listEnd = false;

            /* 第一次拉取数据 */
            this.getImageData();
        },
        /* 重置界面 */
        reset: function() {
            this.initContainer();
            this.initData();
        },
        /* 向后台拉取图片列表数据 */
        getImageData: function () {
            var _this = this;

            if(!_this.listEnd && !this.isLoadingData) {
                this.isLoadingData = true;
                var url = editor.getActionUrl(editor.getOpt('musicManagerActionName')),
                    isJsonp = utils.isCrossDomainUrl(url);
                
                ajax.request(url, {
                    'timeout': 100000,
                    'dataType': isJsonp ? 'jsonp':'',
                    'data': utils.extend({
                            start: this.listIndex,
                            size: this.listSize
                        }, editor.queryCommandValue('serverparam')),
                    'method': 'get',
                    'onsuccess': function (r) {
                        try {
                            var json = isJsonp ? r:eval('(' + r.responseText + ')');
                            if (json.state == 'SUCCESS') {
                                _this.pushData(json.list);
                                _this.listIndex = parseInt(json.start) + parseInt(json.list.length);
                                if(_this.listIndex >= json.total) {
                                    _this.listEnd = true;
                                }
                                _this.isLoadingData = false;
                            }
                        } catch (e) {
                            if(r.responseText.indexOf('ue_separate_ue') != -1) {
                                var list = r.responseText.split(r.responseText);
                                _this.pushData(list);
                                _this.listIndex = parseInt(list.length);
                                _this.listEnd = true;
                                _this.isLoadingData = false;
                            }
                        }
                    },
                    'onerror': function () {
                        _this.isLoadingData = false;
                    }
                });
            }
        },
        /* 添加图片到列表界面上 */
        pushData: function (list) {
            var i, item, img, icon, _this = this,
                urlPrefix = editor.getOpt('musicManagerUrlPrefix');
            for (i = 0; i < list.length; i++) {
                if(list[i] && list[i].url) {
                	var name = list[i].title;
                    item = document.createElement('li');
                    item.title = name;
                    img = document.createElement('audio');
                    icon = document.createElement('span');
                    i_icon = document.createElement('i');
                    i_icon.setAttribute('class','file-preview file-type-mp3');
                    pTitle = document.createElement('p');
                    pTitle.setAttribute('class','title');
                    pTitle.innerText = name;
                    
                    domUtils.on(img, 'load', (function(image){
                        return function(){
                            _this.scale(image, image.parentNode.offsetWidth, image.parentNode.offsetHeight);
                        }
                    })(img));
                    img.width = 113;
                    img.setAttribute('src', urlPrefix + list[i].url + (list[i].url.indexOf('?') == -1 ? '?noCache=':'&noCache=') + (+new Date()).toString(36) );
                    img.setAttribute('_src', urlPrefix + list[i].url);
                    domUtils.addClass(icon, 'icon');
                    
                    item.appendChild(pTitle);
                    item.appendChild(img);
                    item.appendChild(icon);
                    item.appendChild(i_icon);
                    this.list.insertBefore(item, this.clearFloat);
                }
            }
        },
        /* 改变图片大小 */
        scale: function (img, w, h, type) {
            var ow = img.width,
                oh = img.height;

            if (type == 'justify') {
                if (ow >= oh) {
                    img.width = w;
                    img.height = h * oh / ow;
                    img.style.marginLeft = '-' + parseInt((img.width - w) / 2) + 'px';
                } else {
                    img.width = w * ow / oh;
                    img.height = h;
                    img.style.marginTop = '-' + parseInt((img.height - h) / 2) + 'px';
                }
            } else {
                if (ow >= oh) {
                    img.width = w * ow / oh;
                    img.height = h;
                    img.style.marginLeft = '-' + parseInt((img.width - w) / 2) + 'px';
                } else {
                    img.width = w;
                    img.height = h * oh / ow;
                    img.style.marginTop = '-' + parseInt((img.height - h) / 2) + 'px';
                }
            }
        },
        getInsertList: function () {
            var i, lis = this.list.children, align = getAlign();
            var listObj=[];
            for (i = 0; i < lis.length; i++) {
                if (domUtils.hasClass(lis[i], 'selected')) {
                    var img = $(lis[i]).find('audio');
                    var src = img.attr('_src'); 
                    listObj.push({
                    	url: src,
                        width:420,
                        height:280,
                        align: align
                    });
                }

            }
            return listObj;
        }
    };
    /*=============================================================================*/
    
/* 上传附件 */
function UploadFile(target) {
    this.$wrap = target.constructor == String ? $('#' + target) : $(target);
    this.init();
}
UploadFile.prototype = {
    init: function () {
        this.fileList = [];
        this.initContainer();
        this.initUploader();
    },
    initContainer: function () {
        this.$queue = this.$wrap.find('.filelist');
    },
    /* 初始化容器 */
    initUploader: function () {
        var _this = this,
            $ = jQuery,    // just in case. Make sure it's not an other libaray.
            $wrap = _this.$wrap,
        // 图片容器
            $queue = $wrap.find('.filelist'),
        // 状态栏，包括进度和控制按钮
            $statusBar = $wrap.find('.statusBar'),
        // 文件总体选择信息。
            $info = $statusBar.find('.info'),
        // 上传按钮
            $upload = $wrap.find('.uploadBtn'),
        // 上传按钮
            $filePickerBtn = $wrap.find('.filePickerBtn'),
        // 上传按钮
            $filePickerBlock = $wrap.find('.filePickerBlock'),
        // 没选择文件之前的内容。
            $placeHolder = $wrap.find('.placeholder'),
        // 总体进度条
            $progress = $statusBar.find('.progress').hide(),
        // 添加的文件数量
            fileCount = 0,
        // 添加的文件总大小
            fileSize = 0,
        // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,
        // 缩略图大小
            thumbnailWidth = 113 * ratio,
            thumbnailHeight = 113 * ratio,
        // 可能有pedding, ready, uploading, confirm, done.
            state = '',
        // 所有文件的进度信息，key为file id
            percentages = {},
            supportTransition = (function () {
                var s = document.createElement('p').style,
                    r = 'transition' in s ||
                        'WebkitTransition' in s ||
                        'MozTransition' in s ||
                        'msTransition' in s ||
                        'OTransition' in s;
                s = null;
                return r;
            })(),
        // WebUploader实例
            uploader,
            actionUrl = editor.getActionUrl(editor.getOpt('musicActionName')),
            fileMaxSize = editor.getOpt('musicMaxSize'),
            acceptExtensions = (editor.getOpt('musicAllowFiles') || []).join('').replace(/\./g, ',').replace(/^[,]/, '');;
         
        if (!WebUploader.Uploader.support()) {
            $('#filePickerReady').after($('<div>').html(lang.errorNotSupport)).hide();
            return;
        } else if (!editor.getOpt('musicActionName')) {
            $('#filePickerReady').after($('<div>').html(lang.errorLoadConfig)).hide();
            return;
        }
        uploader = _this.uploader = WebUploader.create({
            pick: {
                id: '#filePickerReady',
                label: lang.uploadSelectFile
            },
            swf: '../../third-party/webuploader/Uploader.swf',
            server: actionUrl,
            fileVal: editor.getOpt('musicFieldName'),
            duplicate: true,
            fileSingleSizeLimit: fileMaxSize,
            compress: false
        });
        uploader.addButton({
            id: '#filePickerBlock'
        });
        uploader.addButton({
            id: '#filePickerBtn',
            label: lang.uploadAddFile
        });
        
        setState('pedding');

        // 当有文件添加进来时执行，负责view的创建
        function addFile(file) {
            var $li = $('<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>' +
                    '<p class="progress"><span></span></p>' +
                    '</li>'),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">' + lang.uploadDelete + '</span>' +
                    '<span class="rotateRight">' + lang.uploadTurnRight + '</span>' +
                    '<span class="rotateLeft">' + lang.uploadTurnLeft + '</span></div>').appendTo($li),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find('p.imgWrap'),
                $info = $('<p class="error"></p>').hide().appendTo($li),

                showError = function (code) {
                    switch (code) {
                        case 'exceed_size':
                            text = lang.errorExceedSize;
                            break;
                        case 'interrupt':
                            text = lang.errorInterrupt;
                            break;
                        case 'http':
                            text = lang.errorHttp;
                            break;
                        case 'not_allow_type':
                            text = lang.errorFileType;
                            break;
                        default:
                            text = lang.errorUploadRetry;
                            break;
                    }
                    $info.text(text).show();
                };

            if (file.getStatus() === 'invalid') {
                showError(file.statusText);
            } else {
                $wrap.text(lang.uploadPreview);
                if ('|png|jpg|jpeg|bmp|gif|'.indexOf('|'+file.ext.toLowerCase()+'|') == -1) {
                    $wrap.empty().addClass('notimage').append('<i class="file-preview file-type-' + file.ext.toLowerCase() + '"></i>' +
                        '<span class="file-title">' + file.name + '</span>');
                } else {
                    if (browser.ie && browser.version <= 7) {
                        $wrap.text(lang.uploadNoPreview);
                    } else {
                        uploader.makeThumb(file, function (error, src) {
                            if (error || !src || (/^data:/.test(src) && browser.ie && browser.version <= 7)) {
                                $wrap.text(lang.uploadNoPreview);
                            } else {
                                var $img = $('<img src="' + src + '">');
                                $wrap.empty().append($img);
                                $img.on('error', function () {
                                    $wrap.text(lang.uploadNoPreview);
                                });
                            }
                        }, thumbnailWidth, thumbnailHeight);
                    }
                }
                percentages[ file.id ] = [ file.size, 0 ];
                file.rotation = 0;

                /* 检查文件格式 */
                if (!file.ext || acceptExtensions.indexOf(file.ext.toLowerCase()) == -1) {
                    showError('not_allow_type');
                    uploader.removeFile(file);
                }
            }

            file.on('statuschange', function (cur, prev) {
                if (prev === 'progress') {
                    $prgress.hide().width(0);
                } else if (prev === 'queued') {
                    $li.off('mouseenter mouseleave');
                    $btns.remove();
                }
                // 成功
                if (cur === 'error' || cur === 'invalid') {
                    showError(file.statusText);
                    percentages[ file.id ][ 1 ] = 1;
                } else if (cur === 'interrupt') {
                    showError('interrupt');
                } else if (cur === 'queued') {
                    percentages[ file.id ][ 1 ] = 0;
                } else if (cur === 'progress') {
                    $info.hide();
                    $prgress.css('display', 'block');
                } else if (cur === 'complete') {
                }

                $li.removeClass('state-' + prev).addClass('state-' + cur);
            });

            $li.on('mouseenter', function () {
                $btns.stop().animate({height: 30});
            });
            $li.on('mouseleave', function () {
                $btns.stop().animate({height: 0});
            });

            $btns.on('click', 'span', function () {
                var index = $(this).index(),
                    deg;

                switch (index) {
                    case 0:
                        uploader.removeFile(file);
                        return;
                    case 1:
                        file.rotation += 90;
                        break;
                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if (supportTransition) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                }

            });

            $li.insertBefore($filePickerBlock);
        }

        // 负责view的销毁
        function removeFile(file) {
            var $li = $('#' + file.id);
            delete percentages[ file.id ];
            updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        }

        function updateTotalProgress() {
            var loaded = 0,
                total = 0,
                spans = $progress.children(),
                percent;

            $.each(percentages, function (k, v) {
                total += v[ 0 ];
                loaded += v[ 0 ] * v[ 1 ];
            });

            percent = total ? loaded / total : 0;

            spans.eq(0).text(Math.round(percent * 100) + '%');
            spans.eq(1).css('width', Math.round(percent * 100) + '%');
            updateStatus();
        }

        function setState(val, files) {

            if (val != state) {

                var stats = uploader.getStats();

                $upload.removeClass('state-' + state);
                $upload.addClass('state-' + val);

                switch (val) {

                    /* 未选择文件 */
                    case 'pedding':
                        $queue.addClass('element-invisible');
                        $statusBar.addClass('element-invisible');
                        $placeHolder.removeClass('element-invisible');
                        $progress.hide(); $info.hide();
                        uploader.refresh();
                        break;

                    /* 可以开始上传 */
                    case 'ready':
                        $placeHolder.addClass('element-invisible');
                        $queue.removeClass('element-invisible');
                        $statusBar.removeClass('element-invisible');
                        $progress.hide(); $info.show();
                        $upload.text(lang.uploadStart);
                        uploader.refresh();
                        break;

                    /* 上传中 */
                    case 'uploading':
                        $progress.show(); $info.hide();
                        $upload.text(lang.uploadPause);
                        break;

                    /* 暂停上传 */
                    case 'paused':
                        $progress.show(); $info.hide();
                        $upload.text(lang.uploadContinue);
                        break;

                    case 'confirm':
                        $progress.show(); $info.hide();
                        $upload.text(lang.uploadStart);

                        stats = uploader.getStats();
                        if (stats.successNum && !stats.uploadFailNum) {
                            setState('finish');
                            return;
                        }
                        break;

                    case 'finish':
                        $progress.hide(); $info.show();
                        if (stats.uploadFailNum) {
                            $upload.text(lang.uploadRetry);
                        } else {
                            $upload.text(lang.uploadStart);
                        }
                        break;
                }

                state = val;
                updateStatus();

            }

            if (!_this.getQueueCount()) {
                $upload.addClass('disabled')
            } else {
                $upload.removeClass('disabled')
            }

        }

        function updateStatus() {
            var text = '', stats;

            if (state === 'ready') {
                text = lang.updateStatusReady.replace('_', fileCount).replace('_KB', WebUploader.formatSize(fileSize));
            } else if (state === 'confirm') {
                stats = uploader.getStats();
                if (stats.uploadFailNum) {
                    text = lang.updateStatusConfirm.replace('_', stats.successNum).replace('_', stats.successNum);
                }
            } else {
                stats = uploader.getStats();
                text = lang.updateStatusFinish.replace('_', fileCount).
                    replace('_KB', WebUploader.formatSize(fileSize)).
                    replace('_', stats.successNum);

                if (stats.uploadFailNum) {
                    text += lang.updateStatusError.replace('_', stats.uploadFailNum);
                }
            }

            $info.html(text);
        }

        uploader.on('fileQueued', function (file) {
            fileCount++;
            fileSize += file.size;

            if (fileCount === 1) {
                $placeHolder.addClass('element-invisible');
                $statusBar.show();
            }

            addFile(file);
        });

        uploader.on('fileDequeued', function (file) {
            fileCount--;
            fileSize -= file.size;

            removeFile(file);
            updateTotalProgress();
        });

        uploader.on('filesQueued', function (file) {
            if (!uploader.isInProgress() && (state == 'pedding' || state == 'finish' || state == 'confirm' || state == 'ready')) {
                setState('ready');
            }
            updateTotalProgress();
        });

        uploader.on('all', function (type, files) {
            switch (type) {
                case 'uploadFinished':
                    setState('confirm', files);
                    break;
                case 'startUpload':
                    /* 添加额外的GET参数 */
                    var params = utils.serializeParam(editor.queryCommandValue('serverparam')) || '',
                        url = utils.formatUrl(actionUrl + (actionUrl.indexOf('?') == -1 ? '?':'&') + 'encode=utf-8&' + params);
                    uploader.option('server', url);
                    setState('uploading', files);
                    break;
                case 'stopUpload':
                    setState('paused', files);
                    break;
            }
        });

        uploader.on('uploadBeforeSend', function (file, data, header) {
            //这里可以通过data对象添加POST参数
            header['X_Requested_With'] = 'XMLHttpRequest';
        });

        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            $percent.css('width', percentage * 100 + '%');
            percentages[ file.id ][ 1 ] = percentage;
            updateTotalProgress();
        });

        uploader.on('uploadSuccess', function (file, ret) {
            var $file = $('#' + file.id);
            try {
                var responseText = (ret._raw || ret),
                    json = utils.str2json(responseText);
                if (json.state == 'SUCCESS') {
                    uploadMusicList.push({
                        'url': json.url,
                        'type': json.type,
                        'original':json.original
                    });
                    $file.append('<span class="success"></span>');
                } else {
                    $file.find('.error').text(json.state).show();
                }
            } catch (e) {
                $file.find('.error').text(lang.errorServerUpload).show();
            }
        });

        uploader.on('uploadError', function (file, code) {
        });
        uploader.on('error', function (code, file) {
            if (code == 'Q_TYPE_DENIED' || code == 'F_EXCEED_SIZE') {
                addFile(file);
            }
        });
        uploader.on('uploadComplete', function (file, ret) {
        });

        $upload.on('click', function () {
            if ($(this).hasClass('disabled')) {
                return false;
            }

            if (state === 'ready') {
                uploader.upload();
            } else if (state === 'paused') {
                uploader.upload();
            } else if (state === 'uploading') {
                uploader.stop();
            }
        });

        $upload.addClass('state-' + state);
        updateTotalProgress();
    },
    getQueueCount: function () {
        var file, i, status, readyFile = 0, files = this.uploader.getFiles();
        for (i = 0; file = files[i++]; ) {
            status = file.getStatus();
            if (status == 'queued' || status == 'uploading' || status == 'progress') readyFile++;
        }
        return readyFile;
    },
    refresh: function(){
        this.uploader.refresh();
    }
};
