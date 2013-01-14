<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">All Views</h1>

<#--<a class="btn" class="title-addition" href="/view/new">Import Views</a>-->

<div class="ratehr"></div>

    <div class="view-box">
        <@s.iterator value="views">
            <a href="<@s.url action="show"><@s.param name="uuid">${uuid}</@s.param></@s.url>"><@s.property value="name" /></a>
            <p class="view-type pull-right"><@s.property value="type" /></p>
            <div class="view-inner-box">
                <p class="view-info">Created on: <b>${generated}</b></p>
                <p class="view-info">Generate by: <b><@s.property value="generator" /></b></p>
                <div class="view-inner-box">
                    <p class="view-info">Number of classes: <b><@s.property value="numOfClasses" /></b></p>
                    <p class="view-info">Number of samples: <b><@s.property value="numOfSamples" /></b></p>
                    <p class="view-info">Number of benchmarks: <b><@s.property value="numOfBenchmarks" /></b></p>
                </div>
            </div>
        </@s.iterator>
    </div>

</#macro>