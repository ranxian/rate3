<#include "../base.ftl" />
<#macro main_container>

<h1>Benchmark ${benchmark.name}</h1>

<#--
<p>${benchmark.numOfClasses}</p>
<p>${benchmark.numOfSamples}</p>
-->

<hr>
<h3>Tasks on this Benchmark</h3>

<#include "../task/table.ftl" />

</#macro>
