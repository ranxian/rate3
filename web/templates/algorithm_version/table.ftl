<#if (algorithmVersions?size>0)>


<table class="table table-hover sortable" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Algorithm</th>
        <th>Created</th>
        <th>Description</th>
        <th>#Results</th>
        <#if isUserSignedIn && currentUser.isVip()><th>Delete</th></#if>
        <#if benchmark??><th>Run</th></#if>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="algorithmVersions" var="algorithmVersion">
    <tr>
        <td><strong><a href="/algorithm_version/show?uuid=${uuid}">${readableName}</a></strong></td>
        <td>${created}</td>
        <td><#if !(algorithmVersion.description=="")>${algorithmVersion.description}<#else>No description</#if></td>
        <td><@s.property value="numOfResults" /></td>
        <#if isUserSignedIn && currentUser.isVip()><td><a href="/algorithm_version/delete?uuid=${uuid}">delete</td></#if>
        <#if benchmark??><td><a href="/run?benchmarkUuid=${benchmark.uuid}&algorithmVersionUuid=${uuid}" class="btn btn-small btn-success">Run Now!</a></td></#if>
    </tr>
    </@s.iterator>
    </tbody>
</table>

<#else>

<p>No Algorithm Versions yet.</p>

</#if>