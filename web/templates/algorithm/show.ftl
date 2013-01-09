<#include "../base.ftl" />

<#macro main_container>

<h1>${algorithm.name}</h1>
<hr>
<p>type: ${algorithm.type}</p>
<p>desc: ${algorithm.description}</p>
<p>Created at: ${algorithm.created}</p>

<hr>

<p><span> Versions </span><a class="btn" href="/algorithm_version/new?algorithmUuid=${algorithm.uuid}">Add a new version</a></p>

<hr>

<h3>List of Versions</h3>

<#include "../algorithm_version/table.ftl" />

<hr>

</#macro>

<p><a href="<@s.url action="edit"><@s.param name="uuid">${uuid}</@s.param></@s.url>">Edit Me</a></p>