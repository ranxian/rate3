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
<h3><a href="<s:url action="show" ><s:param name="uuid">${view.uuid}</s:param></s:url>">${view.name}</a></h3>
<hr>
<s:form action="update">
   <s:hidden name="view.uuid" id="id"></s:hidden>
    <s:textfield name="view.name" label="Name"></s:textfield>

    <s:select label="Generator"
              name="view.generator"
              list="{'FVC2004', 'FVC2006'}"
              headerKey="0"
              headerValue="Choose a view generating strategy"
            ></s:select>
    <s:select label="Type"
              name="view.type"
              list="{'FINGERVEIN', 'FINGERVEIN'}"
              headerKey="0"
              headerValue="Choose a view type"
            ></s:select>
    <s:submit></s:submit>
</s:form>
<s:debug/>
</body>
</html>