<#include "../base.ftl" />
<#macro main_container>
<h1 class="title">Usable Algorithm Versions for Benchmark <em><a href="/benchmark/show?uuid=${benchmark.uuid}">${benchmark.name}</a></em></h1>
<!--<a class="btn" href="/algorithm/new">New Algorithm</a>-->
<div class="ratehr"></div>

    <#include "./table.ftl" />
    <#include "../pagination.ftl" />

<div class="ratehr"></div>

</#macro>