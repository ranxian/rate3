<#include "../base.ftl" />
<#macro main_container>
<h1 class="title">All Algorithms</h1>
<#if isUserSignedIn>
<a class="btn" href="/algorithm/new">New Algorithm</a>
</#if>
<div class="ratehr"></div>

<#include "./table.ftl" />
<#include "../pagination.ftl" />

<div class="ratehr"></div>

</#macro>