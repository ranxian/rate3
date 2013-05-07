<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Add Views</h1>
<div class="ratehr"></div>
<form class="form-horizontal add-page" enctype="multipart/form-data" action="view/create">
    <#include "_form.ftl" />

    <div class="control-group">
        <label class="control-label">Import Tag</label>

        <div class="controls">
            <@s.textfield name="importTag"></@s.textfield>
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn">Submit</button>
        </div>
    </div>
</form>
</#macro>