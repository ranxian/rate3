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
        <th>Author</th>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="algorithms">
    <tr>
        <td class="tableHighlight"><a href="<@s.url action="show"><@s.param name="uuid">${uuid}</@s.param></@s.url>"><@s.property value="name" /></a></td>
        <td><@s.property value="type" /></td>
        <td><@s.property value="protocol" /></td>
        <td><#if (description=="")>no description<#else>${description}</#if></td>
        <td>${created}</td>
        <td>${updated}</td>
        <td>${numOfVersions}</td>
        <td class="tableHighlight">${authorName}</td>
    </tr>
    </@s.iterator>
    </tbody>
</table>

<#else>

<p>No algorithms yet.</p>

</#if>