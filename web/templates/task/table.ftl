<#if (tasks?size>0) >

<table class="table table-hover sortable" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Algorithm</th>
        <th>Benchmark</th>
        <th>View</th>
        <th>Created</th>
        <th>Finished</th>
        <th>Runner</th>
        <th>Delete</th>
        <#if isUserSignedIn && currentUser.isVip()>
            <th>ReRun</th>
        </#if>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="tasks">
    <tr>
        <td><a href="/algorithm_version/show?uuid=${algorithmVersion.uuid}"><strong>${algorithmVersion.readableName}</strong></a></td>
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
        <td class="tableHighlight"><a href="/user/algorithms?uuid=${algorithmVersion.algorithm.user.uuid}" class="ratered">${algorithmVersion.algorithm.user.name}</a></td>
        <#if isUserSignedIn && (currentUser.isVip() || author.getUuid().equals(currentUser.getUuid()))>
            <td><a href="/task/delete?uuid=${uuid}">delete</a></td>
        <#else>
            <td></td>
        </#if>
        <#if isUserSignedIn && currentUser.isVip()>
            <td><a href="/task/rerun?uuid=${uuid}">rerun</a></td>
        </#if>
    </tr>
    </@s.iterator>
    </tbody>
</table>

<#else>

<p>No tasks yet.</p>

</#if>