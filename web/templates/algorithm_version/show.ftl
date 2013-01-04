<#include "../base.ftl" />

<#macro main_container>

<h3>${algorithm.name}</h3>
<hr>
<p>type: ${algorithm.type}</p>
<p>desc: ${algorithm.description}</p>
<p>Created at: ${algorithm.created}</p>

</#macro>

<p><a href="<@s.url action="edit"><@s.param name="uuid">${uuid}</@s.param></@s.url>">Edit Me</a></p>