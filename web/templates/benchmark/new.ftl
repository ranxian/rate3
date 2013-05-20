<#include "../base.ftl" />
<#macro main_container>
    <h1 class="title">Generate Benchmark</h1>
    <div class="ratehr"></div>

    <script type="text/javascript">
        function inputSelect() {
            var prefix = "benchmarkType";
            if (document.getElementById(prefix + "Custom").checked == true) {
                document.getElementsByClassName("Custom").show();
                document.getElementsByClassName("SLSB").hide();
                document.getElementsByClassName("General").hide();
            } else if (document.getElementById(prefix + "SLSB").checked == true) {
                document.getElementsByClassName("Custom").addClass("hide");
                document.getElementsByClassName("SLSB").removeClass("hide");
                document.getElementsByClassName("General").removeClass("hide");
            } else {
                document.getElementsByClassName("Custom").hide();
                document.getElementsByClassName("SLSB").hide();
                document.getElementsByClassName("General").show();
            }
        }
    </script>
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
                <@s.radio list="{'General', 'SLSB', 'Custom'}" id="benchmarkType" theme="simple" name="benchmark.generator"></@s.radio>
            </div>
        </div>

        <div class="control-group">
            <div class="control-label">
                Class Count
            </div>
            <div class="controls">
                <@s.textfield name="classCount"></@s.textfield>
            </div>
        </div>

        <div class="control-group">
            <div class="control-label">
                Sample Count
            </div>
            <div class="controls">
                <@s.textfield name="sampleCount"></@s.textfield>
            </div>
        </div>

        <div class="control-group SLSB">
            <div class="control-label">B4far</div>
            <div class="controls">
                <@s.textfield name="B4far"></@s.textfield>
            </div>
        </div>

        <div class="control-group SLSB">
            <div class="control-label">B4frr</div>
            <div class="controls">
                <@s.textfield name="B4frr"></@s.textfield>
            </div>
        </div>

        <div class="control-group Custom">
            <div class="control-label">Custom Benchmark</div>
            <div class="controls">
                <@s.textarea name="content" cols="10" rows="8"></@s.textarea>
            </div>
        </div>

        <div class="form-actions">
            <@s.submit cssClass="btn"></@s.submit>
        </div>
    </form>
</#macro>