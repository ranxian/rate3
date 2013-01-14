<#include "../base.ftl" />

<#macro main_container>

<h1 class="title">Algorithm <em>${algorithm.name}</em></h1>
<hr>
<p>type: ${algorithm.type}</p>
<p>desc: ${algorithm.description}</p>
<p>Created at: ${algorithm.created}</p>

<p><a href="/algorithm/edit?uuid=${algorithm.uuid}" class="btn">Edit</a></p>

<hr>

<h3>List of Versions</h3>

<#include "../algorithm_version/table.ftl" />

<p><a class="btn" href="/algorithm_version/new?algorithmUuid=${algorithm.uuid}">Add a new version</a></p>

<hr>

</#macro>

