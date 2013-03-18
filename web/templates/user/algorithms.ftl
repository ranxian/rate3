<#include "../base.ftl" />

<#macro main_container>

<h1 class=title><b class="ratered">${user.name}</b>'s Info Center</h1>
<div class="ratehr"></div>
<#include "./algorithm_table.ftl" />
<#include "../pagination.ftl" />

</#macro>