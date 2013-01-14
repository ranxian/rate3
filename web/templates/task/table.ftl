<table class="table table-hover" id="algorithm-version-list">
    <thead>
    <tr>
        <th>Algorithm</th>
        <th>Version</th>
        <th>Benchmark</th>
        <th>Created</th>
        <th>Finished</th>
        <th>For debug use</th>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="tasks">
    <tr>
        <td><a href="/algorithm/show?uuid=${algorithmVersionByAlgorithmVersionUuid.algorithm.uuid}">${algorithmVersionByAlgorithmVersionUuid.algorithm.name}</a></td>
        <td><a href="/algorithm_version/show?uuid=${algorithmVersionByAlgorithmVersionUuid.uuid}">${algorithmVersionByAlgorithmVersionUuid.uuidShort}</a></td>
        <td><a href="/benchmark/show?uuid=${benchmarkByBenchmarkUuid.uuid}">${benchmarkByBenchmarkUuid.name}</a></td>
        <td>${created}</td>
        <td><a href="/task/show?uuid=${uuid}">${finished!"running"}</a></td>
        <td><a href="/task/delete?uuid=${uuid}">delete</a></td>
    </tr>
    </@s.iterator>
    </tbody>
</table>