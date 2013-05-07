<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">New Algorithm Version for ${algorithm.name}</h1>
<div class="ratehr"></div>

<form action="/algorithm_version/create" Class="form-horizontal add-page" enctype="multipart/form-data" method="POST" theme="simple">

    <@s.hidden name="algorithmUuid" value="${algorithm.uuid}" id="algorithmUuid" cssStyle="display: none;"></@s.hidden>

    <#include "_form.ftl">
<form>

</#macro>