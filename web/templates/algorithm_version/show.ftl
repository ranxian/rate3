<#include "../base.ftl" />

<#macro main_container>

<h1 class="title">Version <em>#${algorithmVersion.uuidShort}</em></h1>
<h3>Algorithm <a href="/algorithm/show?uuid=${algorithm.uuid}"><em>${algorithm.name}</em></a></h3>

<div class="ratehr"></div>

<p>Type: <b>${algorithm.type}</b></p>
<p>Desc: <b><#if !(algorithm.description=="")>${algorithm.description}<#else>No description</#if></b></p>
<p>Algorithm created at: <b>${algorithm.created}</b></p>

<hr>

<h3>Tasks by this algorithm version</h3>

<#include "../task/table.ftl" />

<div class="ratehr"></div>

</#macro>

