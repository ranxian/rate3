<#if (algorithms?size>0) >

<table class="table table-hover" id="algorithm-list">
    <thead>
    <tr>
        <th>Name</th>
        <th>Type</th>
        <th>Protocol</th>
        <th>Description</th>
        <th>Created</th>
        <th>Updated</th>
        <th>Versions</th>
    </tr>
    </thead>
    <tbody>
        <@s.iterator value="algorithms">
        <tr>
            <td class="tableHighlight"><a href="/algorithm/show?uuid=${uuid}">${name}</a></td>
            <td>${type}</td>
            <td>${protocol}</td>
            <td><#if (description=="")>no description<#else>${description}</#if></td>
            <td>${created}</td>
            <td>${updated}</td>
            <td>${numOfVersions}</td>
        </tr>
        </@s.iterator>
    </tbody>
</table>
<#include "../pagination.ftl" />
<#else>

<p>No algorithms yet.</p>

</#if>