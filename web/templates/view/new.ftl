<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Add Views - Import your data</h1>
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
                      list="{'FINGERVEIN', 'FINGERVEIN'}"
                      headerKey="0"
                      headerValue="Choose a view type"
            ></@s.select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Generator</label>
        <div class="controls">
            <@s.select name="view.generator"
            list="{'FVC2004', 'FVC2006', 'RATE'}"
            headerKey="0"
            headerValue="Choose a view generating strategy"
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
        <label class="control-label">Zip</label>
        <div class="controls">
            <input type="file" />
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn">Import</button>
        </div>
    </div>
</form>
</#macro>