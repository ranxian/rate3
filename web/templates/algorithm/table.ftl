<table class="table table-hover" id="algorithm-list">
    <thead>
    <tr>
        <th>Name</th>
        <th>Type</th>
        <th>Protocol</th>
        <th>Description</th>
        <th>Created</th>
        <th>Updated</th>
        <th>Versions</th>
    </tr>
    </thead>
    <tbody>
    <@s.iterator value="algorithms">
    <tr>
        <td><a href="<@s.url action="show"><@s.param name="uuid">${uuid}</@s.param></@s.url>"><@s.property value="name" /></a></td>
        <td><@s.property value="type" /></td>
        <td><@s.property value="protocol" /></td>
        <td><@s.property value="description"></@s.property></td>
        <td><@s.property value="created"></@s.property></td>
        <td><@s.property value="updated"></@s.property></td>
        <td><@s.property value="numOfVersions"></@s.property></td>
    </tr>
    </@s.iterator>
    </tbody>
</table>