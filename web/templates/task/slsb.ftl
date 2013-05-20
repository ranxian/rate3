<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Detail of Task #</b> ${uuid}
    <#--<small class="pull-right getdown">(${slsbTask.finishedTurn}/${slsbTask.totalTurn})</small>-->
    <#--<#if task.finished?exists>-->
        <small class="pull-right percentage finished">FINISHED</small>
        <br>
        <br>

    <#--<#else>-->
        <#--<small class="pull-right percentage running">${slsbTask.percentage*100}%-->
        <#--</small>-->
    <#--</#if>-->
</h1>

<strong>Algorithm: </strong><a href="$/algorithmVersion/show?uuid={task.algorithmVersion.uuid}" class="btn">${task.algorithmVersion.readableName}</a>
<strong>Benchmark: </strong><a href="/benchmark/show?uuid=${task.benchmark.uuid}" class="btn">${task.benchmark.name}</a>
<hr>
<br>
<div class="result-box">
    <img src="/chart/SLSBFrr?taskUuid=${task.uuid}" />
    <img src="/chart/SLSBFar?taskUuid=${task.uuid}" />
</div>
</#macro>
