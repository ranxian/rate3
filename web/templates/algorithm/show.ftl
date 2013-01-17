<#include "../base.ftl" />

<#macro main_container>

<h1 class="title">Algorithm <em>${algorithm.name}</em></h1>
<hr>
<p>Type: <b>${algorithm.type}</b></p>
<p>Desc: <b>${algorithm.description}</b></p>
<p>Created at: <b>${algorithm.created}</b></p>

<p><a href="/algorithm/edit?uuid=${algorithm.uuid}" class="btn">Edit</a></p>

<hr>

<h3>List of Versions</h3>

<#include "../algorithm_version/table.ftl" />

<p><a class="btn" href="/algorithm_version/new?algorithmUuid=${algorithm.uuid}">Add a new version</a></p>

<hr>

</#macro>

