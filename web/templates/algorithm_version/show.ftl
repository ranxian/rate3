<#include "../base.ftl" />

<#macro main_container>

<h1 class="title">${algorithmVersion.readableName} <em>(#${algorithmVersion.uuidShort})</em></h1>
<h3> In Algorithm <a href="/algorithm/show?uuid=${algorithm.uuid}"><em>${algorithm.name}</em></a></h3>
</small>

<div class="ratehr"></div>

<p>Type: <b>${algorithm.type}</b></p>
<p>Desc: <b><#if !(algorithmVersion.description=="")>${algorithmVersion.description}<#else>No description</#if></b></p>
<p>Created at: <b>${algorithmVersion.created}</b></p>
<hr>

<h3>Tasks by this algorithm version</h3>

<#include "../task/table.ftl" />

<div class="ratehr"></div>

</#macro>

