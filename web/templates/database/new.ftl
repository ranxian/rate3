<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">Import Database</h1>
<div class="ratehr"></div>
<form class="form-horizontal add-page" enctype="multipart/form-data" action="database/create">
    <div class="control-group">
        <label class="control-label">Zip Path</label>
        <div class="controls">
            <@s.select
                name="zipPath"
                list="zipFilePaths"
                headerKey = 0
                headerValue="choose your .zip file path"
            ></@s.select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Type</label>
        <div class="controls">
            <@s.select
                      name="type"
            list="{'FINGERVEIN', 'FINGERVEIN'}"
            headerKey="0"
            headerValue="Choose a view type"
            ></@s.select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">Import Tag</label>
        <div class="controls">
            <@s.textfield name="importTag" value="${currentTime}"></@s.textfield>
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn">Import</button>
        </div>
    </div>
</form>
</#macro>