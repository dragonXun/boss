<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<shiro:hasPermission name="courierAction_pageQuery"><h1 style="color: red">这是一个手握大权的大佬</h1></shiro:hasPermission>
	<shiro:authenticated><h1 style="color: red">这是一个考了证书的学生</h1></shiro:authenticated>
	<shiro:hasRole name="admin"><h1 style="color: red">这是一个小角色</h1></shiro:hasRole>
	<shiro:user><h1 style="color: red">用户?????</h1></shiro:user>
	<h1 style="color: red"><shiro:principal></shiro:principal></h1>
	<shiro:hasAnyRoles name="admin"><h1 style="color: red">什么角色</h1></shiro:hasAnyRoles>
	
	<shiro:lacksPermission name="courierAction_pageQuer"><h1 style="color: red">没有这个什么权????</h1></shiro:lacksPermission>
	<shiro:notAuthenticated><h1 style="color: red">没有证书的苦逼</h1></shiro:notAuthenticated>
	<shiro:guest><h1 style="color: red">游客路人甲......</h1></shiro:guest>
	<shiro:lacksRole name="admin"><h1 style="color: red">没有角色的....</h1></shiro:lacksRole>
</body>
</html>
