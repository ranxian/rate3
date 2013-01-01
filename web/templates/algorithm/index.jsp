<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
  <head>
    <title>RATE3</title>
    <link rel="stylesheet" type="text/css" href="/css/rate.css">
    <script type="text/javascript" src="/js/rate.js"></script>
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
          <li><a href="faq.html">FAQ</a></li>
          <li><a href="view.html">View</a></li>
          <li><a href="algorithm.html" class="active">Algorithm</a></li>
          <li><a href="status.html">Status</a></li>
          <li><a href="about.html">About</a></li>
        </ul>
      </div>
    </div>
    <div id="main">
      <div class="container">
        <h1 class="title">Your Algorithms</h1>
        <a class="btn" href="algorithm-new.html">New Algorithm</a>
        <div class="ratehr"></div>
        <table class="table table-hover" id="algorithm-list">
          <thead>
            <tr>
              <th>Name</th>
              <th>Type</th>
              <th>Protocol</th>
              <th>Description</th>
              <th>Created</th>
            </tr>
          </thead>
          <tbody>
          <s:iterator value="algorithms">
              <tr>
                  <td><a href="<s:url action="show"><s:param name="uuid">${uuid}</s:param></s:url>"><s:property value="name" /></a></td>
                  <td><s:property value="type" /></td>
                  <td><s:property value="protocol" /></td>
                  <td><s:property value="description"></s:property></td>
                  <td><s:property value="created"></s:property></td>
              </tr>
          </s:iterator>
          </tbody>
        </table>
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
