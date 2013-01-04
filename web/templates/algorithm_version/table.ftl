<table class="table table-hover" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Created</th>
        <th>Description</th>
        <th>Number of Results</th>
        <th>for debug use</th>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="algorithmVersions">
    <tr>
        <td><a href="/algorithm_version/show?uuid=${uuid}"><@s.property value="created"></a></@s.property></td>
        <td><@s.property value="description" /></td>
        <td><@s.property value="numOfResults" /></td>
        <td><a href="/algorithm_version/delete?uuid=${uuid}">delete</td>
    </tr>
    </@s.iterator>
    </tbody>
</table>