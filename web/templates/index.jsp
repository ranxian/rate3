<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: XianRan
  Date: 12-12-5
  Time: 下午1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
    <h1>Welcome to Rate3!</h1>
    <p>FAQ lists here</p>
    <hr />

    <list>
        <li><a href="<s:url namespace="/view" action="index" />">Views</a></li>
        <li><a href="<s:url namespace="/algorithm" action="index" />">Algorithms</a></li>
        <li><a href="">Status</a></li>
        <li><a href="<s:url namespace="/user" action="index" />">Users</a></li>
    </list>

    <hr>
    <p>By Yu Yuankai and Xian Ran</p>
  </body>
</html>