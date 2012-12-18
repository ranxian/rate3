<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: XianRan
  Date: 12-12-18
  Time: 下午9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<s:form action="update">
    <s:hidden name="user.uuid"></s:hidden>
    <s:textfield name="user.name" label="Name" />
    <s:password  name="user.password" label="Password" />
    <s:textfield name="user.email"  label ="Email"/>
    <s:textfield name="user.organization"  label="Organization"  />

    <s:submit/>

</s:form>
</body>
</html>