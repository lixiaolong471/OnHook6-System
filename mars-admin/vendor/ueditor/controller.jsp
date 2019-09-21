<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cfwx.rox.weixin.uitl.ueditor.ActionEnter"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="com.cfwx.rox.service.system.impl.ConfigKey"%>
<%@page import="com.cfwx.rox.service.system.ConfigService"%>
<%@page import="com.cfwx.rox.util.SpringUtil"%>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	//项目路径
	String webPath = application.getRealPath( "/" );
	//上传路径
	String rootPath = SpringUtil.getBean(ConfigService.class).getValueByKey(ConfigKey.ROXWXWEB_MEITI_REALPATH);
	
	out.write( new ActionEnter( request, rootPath,webPath ).exec() );
	
%>