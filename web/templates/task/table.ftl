<#if (tasks?size>0) >

<table class="table table-hover sortable" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Algorithm</th>
        <th>Version</th>
        <th>Benchmark</th>
        <th>View</th>
        <th>Created</th>
        <th>Finished</th>
        <th>Runner</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="tasks">
    <tr>
        <td class="tableHighlight"><a href="/algorithm/show?uuid=${algorithmVersion.algorithm.uuid}">${algorithmVersion.algorithm.name}</a></td>
        <td><a href="/algorithm_version/show?uuid=${algorithmVersion.uuid}">${algorithmVersion.uuidShort}</a></td>
        <td><a href="/benchmark/show?uuid=${benchmark.uuid}">${benchmark.name}</a></td>
        <td><a href="/view/show?uuid=${benchmark.view.uuid}">${benchmark.view.name}</a></td>
        <td>${created}</td>
        <td>
            <#if finished?exists>
            <a href="/task/show?uuid=${uuid}"><strong class="ratered">${finished}</strong></a>
            <#else>
                <strong class="running">running</strong>
            </#if>
        </td>
        <td class="tableHighlight">${runnerName}</td>
        <#if isUserSignedIn && (currentUser.isVip() || author.getUuid().equals(currentUser.getUuid()))>
            <td><a href="/task/delete?uuid=${uuid}">delete</a></td>
        <#else>
            <td></td>
        </#if>
    </tr>
    </@s.iterator>
    </tbody>
</table>

<#else>

<p>No tasks yet.</p>

</#if>