<#include "../base.ftl" />
<#macro main_container>
<div class="ratehr"></div>

<form action="/benchmark/update" method="post" class="form-horizontal add-page">

    <input type="hidden" name="benchmark.uuid" id="uuid" value="${benchmark.uuid}"/>

    <div class="control-group">
        <div class="controls">
            <@s.textfield label="Name"
            name="benchmark.name" ></@s.textfield>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <@s.textarea label="Description"
            name="benchmark.description" ></@s.textarea>
        </div>
    </div>

    <div class="control-group">
        <div class="control-label">Visible?</div>
        <div class="controls">
            <label for="benchmark.visibleYES">YES</label>
            <input type="radio" value="YES" name="benchmark.visible" id="benchmark.visibleYES">
            <label for="benchmark.visibleNO">NO</label>
            <input type="radio" value="NO" name="benchmark.visible" id="benchmark.visibleNO">
        </div>
    </div>

    <@s.submit></@s.submit>
</form>
</#macro>