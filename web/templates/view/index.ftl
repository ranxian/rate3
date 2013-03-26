<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">All Views</h1>
<a href="/view/new" class="btn">Generate</a>
<#--<a class="btn" class="title-addition" href="/view/new">Import Views</a>-->

<div class="ratehr"></div>

    <@s.iterator value="views">
    <div class="view-box">
        <div class="view-header">
            <a href="/view/show?uuid=${uuid}" class="view-title">${name}</a>
            <p class="view-type pull-right"><@s.property value="type" /></p>
        </div>
        <div class="view-inner-box">
            <p class="view-info">Created on: <b>${generated}</b></p>
            <p class="view-info">Generate by: <b><@s.property value="generator" /></b></p>
            <p class="view-info">Created by: <b>Xian Ran</b></p>
        </div>
        <div class="view-inner-box">
            <p class="view-info">Number of classes: <b><@s.property value="numOfClasses" /></b></p>
            <p class="view-info">Number of samples: <b><@s.property value="numOfSamples" /></b></p>
            <p class="view-info">Number of benchmarks: <b><@s.property value="numOfBenchmarks" /></b></p>
        </div>

    </div>
    </@s.iterator>



</#macro>