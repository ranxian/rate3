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
            <td class="tableHighlight"><a href="/algorithm/show?uuid=${algorithm.uuid}">${algorithm.name}</a></td>
            <td>${algorithm.type}</td>
            <td>${algorithm.protocol}</td>
            <td><#if (algorithm.description=="")>no description<#else>${algorithm.description}</#if></td>
            <td>${algorithm.created}</td>
            <td>${algorithm.updated}</td>
            <td>${algorithm.numOfVersions}</td>
        </tr>
        </@s.iterator>
    </tbody>
</table>

<#else>

<p>No algorithms yet.</p>

</#if>