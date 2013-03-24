<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Detail of Task #</b> ${uuid}
    <small class="pull-right getdown">(${fvc2006Task.finishedTurn}/${fvc2006Task.totalTurn})</small>
    <#if task.finished?exists>
        <small class="pull-right percentage finished">${fvc2006Task.percentage*100}%</small>

    <#else>
        <small class="pull-right percentage running">${fvc2006Task.percentage*100}%
        </small>
    </#if>

</h1>

<div class="result-box">
    <div class="statics">
        <p>EER: ${fvc2006Task.EER*100}% (${fvc2006Task.EER_l*100}% - ${fvc2006Task.EER_h*100}%) </p>
        <p>FMR100: ${fvc2006Task.FMR100*100}%) </p>
        <p>FMR1000: ${fvc2006Task.FMR1000*100}%</p>
        <p>zeroFMR: ${fvc2006Task.zeroFMR*100}%</p>
        <p>zeroFNMR: ${fvc2006Task.zeroFNMR*100}%</p>
        <p>Estimate finish time: ${fvc2006Task.estimateLeftTime}</p>
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
</#macro>
