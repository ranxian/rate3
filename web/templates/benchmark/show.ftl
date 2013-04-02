<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Benchmark <em>${benchmark.name}</em></h1>
<h3>On View <em><a href="/view/show?uuid=${benchmark.view.uuid}">${benchmark.view.name}</a></em></h3>

<div class="ratehr"></div>

<p>Created: <b>${benchmark.created}</b></p>
<p>Generator: <b>${benchmark.generator}</b></p>
<p>Description: <b>${benchmark.description!"No description"}</b></p>

<a class="btn" href="/benchmark/edit?uuid=${benchmark.uuid}">Edit</a>

<#--
<p>${benchmark.numOfClasses}</p>
<p>${benchmark.numOfSamples}</p>
-->

<hr />

<h3>Tasks on this Benchmark</h3>

<#include "../task/table.ftl" />
<a href="/task/bybenchmark?uuid=${benchmark.uuid}">Show All</a>

<hr>
<h3>Usable algorithm versions</h3>
<#include "../algorithm_version/table.ftl" />
<a href="/algorithm_version/bybenchmark?uuid=${benchmark.uuid}">Show All</a>

<div class="ratehr"></div>

</#macro>
