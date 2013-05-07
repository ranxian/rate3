<#include "../base.ftl" />

<#macro main_container>

<h1 class="title">View <em>${view.name}</em></h1>

<div class="ratehr"></div>

<p>Type: <b>${view.type}</b></p>
<p>Generator: <b>${view.generator}</b></p>
<p>Descriptin: <b>${view.description}</b></p>
<!-- no such info in database schema? <p>created:</p> -->
<#if isUserSignedIn && currentUser.isVip()>
<p><a class="btn" href="<@s.url action="edit"><@s.param name="uuid">${uuid}</@s.param></@s.url>">Edit Me</a></p>
</#if>

<hr />
<h3>Benchmarks on this View</h3>
<#include "../benchmark/table.ftl" />
    <#if isUserSignedIn && currentUser.isVip()>

<a href="/benchmark/new?viewUuid=${view.uuid}" class="btn">New Benchmark</a>
    <a href="/benchmark/custom?viewUuid=${view.uuid}" class="btn">Custom Benchmark</a>
    </#if>

<hr>
<h3>Tasks on this View</h3>

<#include "../task/table.ftl" />
<a href="/task/byview?uuid=${view.uuid}">Show All</a>

<div class="ratehr"></div>

</#macro>
