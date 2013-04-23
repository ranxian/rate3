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
            <span class="help-block">${.now}</span>
        </div>
    </div>
</form>
</#macro>