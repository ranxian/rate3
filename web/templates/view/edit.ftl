<#include "../base.ftl" />

<#macro main_container>

<h3><a href="<@s.url action="show" ><@s.param name="uuid">${view.uuid}</@s.param></@s.url>">${view.name}</a></h3>

<div class="ratehr"></div>
<form class="form-horizontal add-page" enctype="multipart/form-data" action="view/update" method="post">
    <@s.hidden name="view.uuid" value="${view.uuid}"></@s.hidden>

    <div class="control-group">
        <label class="control-label">Name</label>
        <div class="controls">
            <@s.textfield name="view.name"></@s.textfield>
        </div>
    </div>
    <#--<div class="control-group">-->
        <#--<label class="control-label">Type</label>-->
        <#--<div class="controls">-->
            <#--<@s.select-->
                      <#--name="view.type"-->
            <#--list="{'FINGERVEIN', 'FINGERVEIN'}"-->
            <#--headerKey="0"-->
            <#--headerValue="Choose a view type"-->
            <#--></@s.select>-->
        <#--</div>-->
    <#--</div>-->
    <#--<div class="control-group">-->
        <#--<label class="control-label">Generator</label>-->
        <#--<div class="controls">-->
            <#--<@s.select name="view.generator"-->
            <#--list="{'FVC2004', 'FVC2006', 'RATE'}"-->
            <#--headerKey="0"-->
            <#--headerValue="Choose a view generating strategy"-->
            <#--></@s.select>-->
        <#--</div>-->
    <#--</div>-->
    <div class="control-group">
        <label class="control-label">Description</label>
        <div class="controls">
            <@s.textarea name="view.description" rows="5"></@s.textarea>
        </div>
    </div>
    <#--<div class="control-group">-->
        <#--<label class="control-label">Zip</label>-->
        <#--<div class="controls">-->
            <#--<input type="file" />-->
        <#--</div>-->
    <#--</div>-->
    <div class="form-actions">
        <button type="submit" class="btn">Update</button>
    </div>
</form>

</#macro>