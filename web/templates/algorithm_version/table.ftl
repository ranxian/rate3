<#if (algorithmVersions?size>0)>


<table class="table table-hover" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Version</th>
        <th>Created</th>
        <th>Description</th>
        <th>Number of Results</th>
        <th>for debug use</th>
        <#if benchmark??><th>Run</th></#if>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="algorithmVersions">
    <tr>
        <td><a href="/algorithm_version/show?uuid=${uuid}">${uuidShort}</a></td>
        <td>${created}</td>
        <td><#if (description=="")>no description<#else>${description}</#if></td>
        <td><@s.property value="numOfResults" /></td>
        <td><a href="/algorithm_version/delete?uuid=${uuid}">delete</td>
        <#if benchmark??><td><a href="/run?benchmarkUuid=${benchmark.uuid}&algorithmVersionUuid=${uuid}" class="btn btn-small btn-success">Run Now!</a></td></#if>
    </tr>
    </@s.iterator>
    </tbody>
</table>

<#else>

<p>No Algorithm Versions yet.</p>

</#if>