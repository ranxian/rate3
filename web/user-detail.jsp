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
           <p>Requested User uuid: <s:property value="uuid" /></p>
           <p>User Email: <s:property value="user.email" /></p>
</body>
</html>