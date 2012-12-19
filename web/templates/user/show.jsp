<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yyk
  Date: 12-12-5
  Time: 下午9:24
  To change this template use File | Settings | File Templates.
--%>

<!--
    This is a test page
-->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:property value="user.name" />'s profile</title>
</head>
<body>
           <p>Just a test to show how to use the model</p>
           <p>User Name: <s:property value="user.name" /></p>
           <p>User Email: <s:property value="user.email" /></p>
           <p>User Org: <s:property value="user.organization" /></p>
           <a href="<s:url action="edit"><s:param name="uuid">${user.uuid}</s:param> </s:url> ">Edit me</a>
</body>
</html>