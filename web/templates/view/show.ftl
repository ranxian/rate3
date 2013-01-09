<#include "../base.ftl" />

<#macro main_container>

<h1>${view.name}</h1>
<hr>
<p>Type: ${view.type}</p>
<p>Generator: ${view.generator}</p>
<!-- no such info in database schema? <p>created:</p> -->
<p><a class="btn" href="<@s.url action="edit"><@s.param name="uuid">${uuid}</@s.param></@s.url>">Edit Me</a></p>

<hr />
<h3>Benchmarks on this View</h3>
<#include "../benchmark/table.ftl" />

<hr>
<h3>Tasks on this View</h3>

<#include "../task/table.ftl" />

</#macro>
