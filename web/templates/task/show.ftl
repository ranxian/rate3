<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Detail of Task #</b> ${uuid}
    <#--<small class="pull-right getdown">(${generalTask.finishedTurn}/${generalTask.totalTurn})</small>-->
    <#if task.finished?exists>
        <small class="pull-right percentage finished">FINISHED</small>

    <#else>
        <small class="pull-right percentage running">RUNNING
        </small>
    </#if>

</h1>
<strong>Algorithm: </strong><a href="/algorithm_version/show?uuid=${task.algorithmVersion.uuid}" class="btn">${task.algorithmVersion.readableName}</a>
<strong>Benchmark: </strong><a href="/benchmark/show?uuid=${task.benchmark.uuid}" class="btn">${task.benchmark.name}</a>

<div class="result-box">
    <div class="statics">
        <p>EER: ${generalTask.EER*100}% (${generalTask.EER_l*100}% - ${generalTask.EER_h*100}%) </p>
        <p>FMR100: ${generalTask.FMR100*100}%) </p>
        <p>FMR1000: ${generalTask.FMR1000*100}%</p>
        <p>zeroFMR: ${generalTask.zeroFMR*100}%</p>
        <p>zeroFNMR: ${generalTask.zeroFNMR*100}%</p>
        <p>Estimate finish time: ${generalTask.estimateLeftTime}</p>
    </div>
    <img src="/chart/score?taskUuid=${task.uuid}" />
    <img src="/chart/fmrfnmr?taskUuid=${task.uuid}" />
    <img src="/chart/roc?taskUuid=${task.uuid}" />

</div>
<hr>
<h1 class="title">Bad Result:</h1>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td><strong>Genuine</strong></td>
        <#list 1..10 as i>
            <td>
                <a href="/task/badresult?uuid=${task.uuid}&resultType=genuine&num=${i}" target="_blank"><strong>G-${i}</strong></a>
            </td>
        </#list>
    </tr>
    <tr>
        <td><strong>Imposter</strong></td>
        <#list 1..10 as i>
            <td>
                <a href="/task/badresult?uuid=${task.uuid}&resultType=imposter&num=${i}" target="_blank"><strong>I-${i}</strong></a>
            </td>
        </#list>
    </tr>

    </tbody>

</table>
<strong>TOP 20 Bad Results:</strong> <a href="/task/downloadResult?uuid=${task.uuid}" class="btn btn-success">${task.uuid + ".zip"}</a> <br>
</#macro>
