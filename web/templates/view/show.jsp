<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: XianRan
  Date: 12-12-13
  Time: 下午9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h3>${view.name}</h3>
<hr>
<p>type:${view.type}</p>
<p>generator:${view.generator}</p>
<!-- no such info in database schema? <p>created:</p> -->
<p><a href="<s:url action="edit"><s:param name="uuid">${uuid}</s:param></s:url>">Edit Me</a></p>
</body>
</html>