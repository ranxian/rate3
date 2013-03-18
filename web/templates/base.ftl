<!DOCTYPE html>
<html>
<head>
    <title>RATE3</title>

    <link rel="stylesheet/less" type="text/css" href="/less/rate.less">
    <script type="text/javascript" src="/js/rate.js"></script>
    <script type="text/javascript" src="/js/less-1.3.3.min.js"></script>
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
        <#if !isUserSignedIn >

        <@s.form action="login" namespace="/" id="login_form" theme="simple">
            <@s.textfield name="username" cssClass="nav-input" placeholder="Username" />
            <@s.password name="password" cssClass="nav-input" placeholder="Password" />

            <@s.hidden name="referUrl" value="${currentPageUrl}" />
            <@s.submit cssClass="btn btn-mini pull-right" value="Login" />
            <a href="/user/new" class="white pull-right">Register</a>
        </@s.form>
        <#else>
            <div class="welcome">
                <p class="title">Welcome!</p>
                <p class="username">${currentUser.name}</p>
                <a href="/logout?referUrl=${currentPageUrl}">Sign Out</a>
            </div>
        </#if>
    </div>
</div>
<div id="navbar">
    <div class="container">
        <ul>
            <li><a href="/index">Home</a></li>
            <li><a href="/view/index">Views</a></li>
            <li><a href="/benchmark/index">Benchmarks</a></li>
            <li><a href="/algorithm/index">Algorithms</a></li>
            <li><a href="/task/index">Tasks</a></li>
            <li><a href="/database/new">Import Database</a></li>
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
