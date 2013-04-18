<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Add Views</h1>
<div class="ratehr"></div>
<form class="form-horizontal add-page" enctype="multipart/form-data" action="view/create">
    <div class="control-group">
        <label class="control-label">Name</label>
        <div class="controls">
            <@s.textfield name="view.name"></@s.textfield>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Type</label>
        <div class="controls">
            <@s.select
                      name="view.type"
                      list="{'FINGERVEIN'}"
                      headerKey="0"
            ></@s.select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Description</label>
        <div class="controls">
            <@s.textarea name="view.description"></@s.textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Import Tag</label>

        <div class="controls">
            <@s.textfield name="importTag"></@s.textfield>
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn">Import</button>
        </div>
    </div>
</form>
</#macro>