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
<s:form action="create">
    <s:textfield name="view.name" label="Name"></s:textfield>
    <s:select label="Generator"
              name="view.generator"
              list="{'FVC', 'FVC', 'FVC'}"
              headerKey="0"
              headerValue="Choose a view generating strategy"
            ></s:select>
    <s:select label="Type"
              name="view.type"
              list="{'aa', 'bb', 'cc'}"
              headerKey="0"
              headerValue="Choose a view type"
            ></s:select>
    <s:submit></s:submit>
</s:form>
</body>
</html>