<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: XianRan
  Date: 12-12-13
  Time: 下午9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>This is your views' list</h1>
<p>Views lists here</p>

<table border="1">
    <tr>
        <th>name</th>
        <th>generator</th>
        <th>type</th>
        <th>timestamp</th>
    </tr>
    <s:iterator value="views">
        <tr>
            <td><a href="<s:url action="show"><s:param name="uuid">${uuid}</s:param></s:url>"><s:property value="name" /></a></td>
            <td><s:property value="generator" /></td>
            <td><s:property value="type" /></td>
            <td><s:property value="timestamp" /></td>
            <td><a href="<s:url action="delete"><s:param name="uuid">${uuid}</s:param></s:url>">Delete</a></td>
        </tr>
    </s:iterator>
</table>
<hr>
<a href="<s:url action="new"></s:url>"><button>New View</button></a>
</body>
</html>