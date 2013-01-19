<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">New Algorithm Version for ${algorithm.name}</h1>
<div class="ratehr"></div>

<form action="/algorithm_version/create" Class="form-horizontal add-page" enctype="multipart/form-data">

    <@s.hidden name="algorithmUuid" value="${algorithm.uuid}" id="algorithmUuid" cssStyle="display: none;"></@s.hidden>

    <div class="control-group">
        <label class="control-label">Description</label>
        <div class="controls">
            <@s.textarea name="algorithmVersion.description" ></@s.textarea>
        </div>
    </div>

<div class="control-group">
    <label class="control-label">Enroll.exe</label>
    <div class="controls">
        <@s.file name ="enrollExe" />
    </div>
</div>

<div class="control-group">
    <label class="control-label">Match.exe</label>
    <div class="controls">
        <@s.file name ="matchExe" />
    </div>
</div>
<div class="control-group">
    <div class="controls">
        <button type="submit" class="btn">Import</button>
    </div>
</div>
<form>

</#macro>