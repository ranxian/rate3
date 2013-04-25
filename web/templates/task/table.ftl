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
        <th>For debug use</th>
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
        <td><a href="/task/show?uuid=${uuid}">
            <#if finished?exists>
                <strong class="ratered">${finished}</strong>
            <#else>
                <strong class="running">running(${generalPercentage*100}%)</strong>
            </#if>
        </a></td>
        <td class="tableHighlight">${runnerName}</td>
        <td><a href="/task/delete?uuid=${uuid}">delete</a></td>
    </tr>
    </@s.iterator>
    </tbody>
</table>

<#else>

<p>No tasks yet.</p>

</#if>