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
<h3>${algorithm.name}</h3>
<hr>
<p>type: ${algorithm.type}</p>
<p>desc: ${algorithm.description}</p>
<p>Created at: ${algorithm.created}</p>

<p><a href="<s:url action="edit"><s:param name="uuid">${uuid}</s:param></s:url>">Edit Me</a></p>
</body>
</html>