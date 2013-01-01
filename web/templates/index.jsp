<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>RATE3</title>
    <link rel="stylesheet" type="text/css" href="css/rate.css">
    <script type="text/javascript" src="js/rate.js"></script>
  </head>
  <body>
    <div id="header">
      <div class="container">
        <a href="index.html">
          <h1 id="brand">RATE3
            <small>Recognition Algorithm Test Engine</small>
          </h1>
        </a>
        <div id="border-div"></div>
        <form id="login_form">
          <input type="text" placeholder="Username" class="nav-input" />
          <input type="password" placeholder="Password" class="nav-input" />
          <input type="submit" value="Login" class="btn btn-mini pull-right" />
          <a class="white pull-right" href="#">Register</a>
        </form>
      </div>
    </div>
    <div id="navbar">
      <div class="container">
        <ul>
            <li><a href="<s:url namespace="/view" action="index" />">Views</a></li>
            <li><a href="<s:url namespace="/algorithm" action="index" />">Algorithms</a></li>
            <li><a href="<s:url namespace="/" action="index" />">Status</a></li>
            <li><a href="<s:url namespace="/user" action="index" />">About</a></li>
        </ul>
      </div>
    </div>
    <div id="main">
      <div class="container">
        <p>Welcome to RATE!</p>
      </div>
    </div>
    <div id="footer">
      <div class="container">
        <p>All Rights Reserved 2012 AI LAB, Peking University </p>
        <p>Any problem, Please Contact Administrator</p>
      </div>
    </div>
  </body>
</html>
