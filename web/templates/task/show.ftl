<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Detail of Task #</b> </h1>


<p>EER: ${fvc2006Task.EER*100}% (${fvc2006Task.EER_l*100}% - ${fvc2006Task.EER_h*100}%) </p>
<p>FMR100: ${fvc2006Task.FMR100*100}%) </p>
<p>FMR1000: ${fvc2006Task.FMR1000*100}%</p>
<p>zeroFMR: ${fvc2006Task.zeroFMR*100}%</p>
<p>zeroFNMR: ${fvc2006Task.zeroFNMR*100}%</p>

<img src="/chart/score?taskUuid=${task.uuid}" />
<img src="/chart/fmrfnmr?taskUuid=${task.uuid}" />
<img src="/chart/roc?taskUuid=${task.uuid}" />

</#macro>
