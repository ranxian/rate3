<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yyk
  Date: 12-12-8
  Time: 下午11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add a user</title>
</head>
<body>
<s:form action="add">

    <s:textfield  name="user.name" label="Name" />
    <s:password  name="user.password" label="Password" />
    <s:textfield name="user.email"  label ="Email"/>
    <s:textfield name="user.organization"  label="Organization"  />

    <s:submit/>

</s:form>
</body>
</html>