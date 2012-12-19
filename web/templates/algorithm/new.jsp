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
    <s:hidden name="algorithm.uuid" id="id"></s:hidden>
    <s:textfield name="algorithm.name" label="Name"></s:textfield>
    <s:select label="Type"
              name="algorithm.type"
              list="{'FINGERVEIN', 'FINGERVEIN'}"
              headerKey="0"
              headerValue="Choose a algorithm type"
            ></s:select>
    <s:select label="Protocal"
              name="algorithm.protocol"
              list="{'FVC2006', 'FVC2004', 'RATE'}"
            ></s:select>
    <s:textarea label="Description"
                name="algorithm.description" ></s:textarea>
    <s:submit></s:submit>
</s:form>
</body>
</html>