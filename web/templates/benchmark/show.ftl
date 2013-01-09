<#include "../base.ftl" />
<#macro main_container>

<h1>Benchmark <em>${benchmark.name}</em></h1>

<div class="ratehr"></div>

<p>Protocol: ${benchmark.protocol}</p>
<p>Created: ${benchmark.created}</p>
<p>Generator: ${benchmark.generator}</p>
<p>Description: ${benchmark.description!"No description"}</p>

<a class="btn" href="/benchmark/edit?uuid=${benchmark.uuid}">Edit</a>

<#--
<p>${benchmark.numOfClasses}</p>
<p>${benchmark.numOfSamples}</p>
-->

<hr />

<h3>Tasks on this Benchmark</h3>

<#include "../task/table.ftl" />

<hr>
<h3>Usable algorithm versions</h3>
<#include "./algorithmVersionTable.ftl" />

<div class="ratehr"></div>

</#macro>
