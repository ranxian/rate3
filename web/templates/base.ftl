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
            <h1 id="brand">RATE
                <small>Recognition Algorithm Test Engine</small>
            </h1>
        </a>

        <div id="border-div"></div>
        <form id="login_form">
            <input type="text" placeholder="Username" class="nav-input"/>
            <input type="password" placeholder="Password" class="nav-input"/>
            <input type="submit" value="Login" class="btn btn-mini pull-right"/>
            <a class="white pull-right" href="#">Register</a>
        </form>
    </div>
</div>
<div id="navbar">
    <div class="container">
        <ul>
            <li><a href="/view/index">View</a></li>
            <li><a href="/algorithm/index">Algorithm</a></li>
            <li><a href="/status/index">Status</a></li>
        </ul>
    </div>
</div>
<div id="main">
    <div class="container">

    <#if main_container?exists>
      <@main_container/>
    </#if>

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