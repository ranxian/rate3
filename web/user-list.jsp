<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yyk
  Date: 12-12-9
  Time: 上午11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
<table border="1">
    <tr>
        <th>name</th>
        <th>registered</th>
        <th>email</th>
        <th>organization</th>
    </tr>
    <s:iterator value="users">
        <tr>
            <td><a href="/user-detail.action?uuid=<s:property value='uuid' />"><s:property value="name" /></a></td>
            <td><s:property value="registered" /></td>
            <td><s:property value="email" /></td>
            <td><s:property value="organization" /></td>
        </tr>
    </s:iterator>
</table>
</body>
</html>