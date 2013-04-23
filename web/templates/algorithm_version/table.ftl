<#if (algorithmVersions?size>0)>


<table class="table table-hover sortable" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Version</th>
        <th>Algorithm</th>
        <th>Created</th>
        <th>Description</th>
        <th>#Results</th>
        <#if currentUser.isVip()><th>for debug use</th></#if>
        <#if benchmark??><th>Run</th></#if>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="algorithmVersions">
    <tr>
        <td><a href="/algorithm_version/show?uuid=${uuid}">${uuidShort}</a></td>
        <td><a href="/algorithm/show?uuid=${algorithm.uuid}">${algorithm.name}</a></td>
        <td>${created}</td>
        <td><#if description??>no description<#else>${description}</#if></td>
        <td><@s.property value="numOfResults" /></td>
        <#if currentUser.isVip()><td><a href="/algorithm_version/delete?uuid=${uuid}">delete</td></#if>
        <#if benchmark??><td><a href="/run?benchmarkUuid=${benchmark.uuid}&algorithmVersionUuid=${uuid}" class="btn btn-small btn-success">Run Now!</a></td></#if>
    </tr>
    </@s.iterator>
    </tbody>
</table>

<#else>

<p>No Algorithm Versions yet.</p>

</#if>