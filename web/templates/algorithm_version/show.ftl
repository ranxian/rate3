<#include "../base.ftl" />

<#macro main_container>

<h1>Version: ${algorithmVersion.created}</h1>
<h3>Algorithm name: ${algorithm.name}</h3>

<hr>

<p>type: ${algorithm.type}</p>
<p>desc: ${algorithm.description}</p>
<p>Algorithm Created at: ${algorithm.created}</p>

<hr>

<#include "../task/table.ftl" />

</#macro>

<p><a href="<@s.url action="edit"><@s.param name="uuid">${uuid}</@s.param></@s.url>">Edit Me</a></p>