<#include "../base.ftl" />

<#macro main_container>

<h1 class=title><b class="ratered">${user.name}</b>'s Info Center
    <small class="pull-right"><p class="privilege">${user.privilege}</p></small></h1>
<#include "./algorithm_table.ftl" />

</#macro>