<#include "../base.ftl" />
<#macro main_container>

<h2 class="title pull-left">
${result.sample1.uuid}
</h2>

<h2 class="pull-left title match">Match</h2>

<h2 class="title pull-right">${result.sample2.uuid}</h2>

<div class="result-info">

</div>
<div class="ratehr"></div>

<div class="score">
    <h1 class="title">Score: ${result.score}</h1>
    <hr>
</div>

<div class="log">
    <h1 class="title">Stdout from algorithm</h1>
    <pre class="pre-scrollable">
    ${result.log}
    </pre>
    <hr>
</div>

<div class="sample">
    <h1 class="title">Sample</h1>
    <table class="table">
        <thead>
        <th></th>
        <th>Sample1</th>
        <th>Sample2</th>
        </thead>
        <tbody>
        <tr>
            <td>uuid</td>
            <td>${result.sample1.uuid}</td>
            <td>${result.sample2.uuid}</td>
        </tr>
        <tr>
            <td>Image</td>
            <td><img src="/image?uuid=${result.sample1.uuid}" alt="" /></td>
            <td><img src="/image?uuid=${result.sample2.uuid}" alt="" /></td>
        </tr>
        </tbody>

    </table>
</div>

</#macro>
