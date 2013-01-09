<table class="table table-hover" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Name</th>
        <th>Created</th>
        <th>Description</th>
        <th>Number of Results</th>
        <th>for debug use</th>
        <th>Run</th>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="algorithmVersions">
    <tr>
        <td><a href="/algorithm/show?uuid=${algorithmByAlgorithmUuid.uuid}">${algorithmByAlgorithmUuid.name}</a></td>
        <td><a href="/algorithm_version/show?uuid=${uuid}">${created}</a></td>
        <td><@s.property value="description" /></td>
        <td><@s.property value="numOfResults" /></td>
        <td><a href="/algorithm_version/delete?uuid=${uuid}">delete</td>
        <td><a href="/run?benchmarkUuid=${benchmark.uuid}&&algorithmVersionUuid=${uuid}">Run Now!</a></td>
    </tr>
    </@s.iterator>
    </tbody>
</table>