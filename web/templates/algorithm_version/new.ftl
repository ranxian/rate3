<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">New Algorithm Version for ${algorithm.name}</h1>
<div class="ratehr"></div>

<@s.form action="/algorithm_version/create" cssClass="form-horizontal add-page" enctype="multipart/form-data">

    <@s.hidden name="algorithmUuid" value="${algorithm.uuid}" id="algorithmUuid"></@s.hidden>

    <div class="control-group">
        <div class="controls">
            <@s.textarea label="Description"
            name="algorithmVersion.description" ></@s.textarea>
        </div>
    </div>

    <@s.submit></@s.submit>
</@s.form>

</#macro>