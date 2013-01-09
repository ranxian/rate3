<#if (benchmarks?size<=0)>
<p>No benchmarks yet.</p>
<#else>

<table class="table table-hover" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Name</th>
        <th>Protocol</th>
        <th>Description</th>
        <th>Created</th>
        <#--<th>Generator</th>-->
        <th>Num of Tasks</th>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="benchmarks">
    <tr>
        <td><a href="/benchmark/show?uuid=${uuid}">${name}</td>
        <td>${protocol}</td>
        <td>${description!"no description"}</td>
        <td>${created}</td>
        <#--<td>${generator}</td>-->
        <td>${numOfTasks}</td>
    </tr>
    </@s.iterator>
    </tbody>
</table>

</#if>