<#if (benchmarks?size<=0)>
<p>No benchmarks yet.</p>
<#else>

<table class="table table-hover sortable" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Name</th>
        <th>View</th>
        <th>Description</th>
        <th>Created</th>
        <#--<th>Generator</th>-->
        <th>#Tasks</th>
        <#if isUserSignedIn && currentUser.isVip()>
            <th>Delete</th>
        </#if>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="benchmarks">
    <tr>
        <td><a href="/benchmark/show?uuid=${uuid}">${name}</td>
        <td><a href="/view/show?uuid=${view.uuid}">${view.name}</a></td>
        <td>${description!"no description"}</td>
        <td>${created}</td>
        <#--<td>${generator}</td>-->
        <td><a href="/task/bybenchmark?uuid=${uuid}">${numOfTasks}</a></td>
        <#if isUserSignedIn && currentUser.isVip()><td><a href="/benchmark/delete?uuid=${uuid}">delete</a></td></#if>
    </tr>
    </@s.iterator>
    </tbody>
</table>

</#if>