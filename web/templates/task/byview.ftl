<#include "../base.ftl" />
<#macro main_container>
        <h1 class="title">All Tasks on View <em><a href="/view/show?uuid=${view.uuid}">${view.name}</a></em></b> </h1>
        <!--<a class="btn" href="/algorithm/new">New Algorithm</a>-->
        <div class="ratehr"></div>

<#include "./table.ftl" />
<#include "../pagination.ftl" />

<div class="ratehr"></div>

</#macro>