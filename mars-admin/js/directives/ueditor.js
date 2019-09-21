/**
 * Created by lixl on 2017/8/1.
 */
(function() {
    'use strict';
    var page = angular.module('app')
    page.directive('ueditor',function($timeout) {
            return {
                restrict : 'AE',
                template : '<script id="ueditorId" name="content" type="text/plain">这里写你的初始化内容</script>',
                link:function(scope, element, attrs){
                    var editorFunctions=["source","fontfamily","fontsize","|","blockquote","horizontal","|","removeformat","link","unlink","|","insertvideo","insertimage","music","|","inserttable","tablestyle","bold","italic","underline","forecolor","backcolor","|","justifyleft","justifycenter","justifyright","|","rowspacingtop","rowspacingbottom","lineheight","|","insertorderedlist","insertunorderedlist","|","imagenone","imageleft","imageright","imagecenter"];
                    scope.ueditorId=attrs.id;
                    scope.config={};
                    /*拓展编辑器的属性*/
                    $.extend(scope.config,scope.editorConfig);
                   /* if(attrs.config!=''&&attrs.config!=undefined){
                        scope.config=$.parseJSON(attrs.config);
                        editorFunctions=editorFunctions.concat($.parseJSON(attrs.config).functions);
                    }
*/
                    UE.delEditor(scope.ueditorId);
                    scope.editor = UE.getEditor(scope.ueditorId,{
                        toolbars: [editorFunctions] ,
                        initialContent : scope.config.content?scope.config.content:'',
                        focus: scope.config.focus?scope.config.focus:false,
                        indentValue:scope.config.indentValue?scope.config.indentValue:'2em',
                        initialFrameWidth:scope.config.initialFrameWidth?scope.config.initialFrameWidth:840,  //初始化编辑器宽度,默认1000
                        initialFrameHeight:scope.config.initialFrameHeight?scope.config.initialFrameHeight:320, //初始化编辑器高度,默认320
                        readonly : scope.config.readonly?scope.config.readonly:false ,//编辑器初始化结束后,编辑区域是否是只读的，默认是false
                        enableAutoSave: scope.config.enableAutoSave?scope.config.enableAutoSave:true,     //启用自动保存
                        saveInterval: scope.config.saveInterval?scope.config.saveInterval:500,  //自动保存间隔时间， 单位ms
                        fullscreen : scope.config.fullscreen?scope.config.fullscreen:false,//是否开启初始化时即全屏，默认关闭
                        imagePopup: scope.config.imagePopup?scope.config.imagePopup:true,     //图片操作的浮层开关，默认打开
                        allHtmlEnabled:scope.config.allHtmlEnabled?scope.config.allHtmlEnabled:false //提交到后台的数据是否包含整个html字符串
                    });

                    scope.editor.ready(function(){
                        if(scope.content){
                            scope.editor.setContent(scope.content);
                        }
                    });

                    scope.ueditorGetContent=function(){
                        return scope.editor.getContent();
                    }
                },
            }
        });

})();
