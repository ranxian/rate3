<#include "../base.ftl" />
<#macro main_container>
    <h1 class="title">Generate Benchmark</h1>
    <div class="ratehr"></div>

    <form action="/benchmark/create" method="post" class="form-horizontal add-page">

        <input type="hidden" name="viewUuid" id="uuid" value="${viewUuid}"/>

        <div class="control-group">
            <div class="control-label">Name</div>
            <div class="controls">
                <input type="text" name="benchmark.name" required />
            </div>
        </div>

        <div class="control-group">
            <div class="control-label">
                Description
            </div>
            <div class="controls">
                <@s.textarea
                name="benchmark.description" ></@s.textarea>
            </div>
        </div>

        <div class="control-group">
            <div class="control-label">Generator</div>
            <div class="controls">
                <@s.select
                name="benchmark.generator"
                list="{'SMALL', 'MEDIUM', 'LARGE', 'VERY_LARGE', 'OneClassImposter','SLSB(100C)', 'SLSB(1000C)'}"
                            ></@s.select>
            </div>
        </div>

        <div class="form-actions">
            <@s.submit cssClass="btn"></@s.submit>
        </div>
    </form>
</#macro>