<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">New Algorithm</h1>
<div class="ratehr"></div>

<@s.form action="create" cssClass="form-horizontal add-page" enctype="multipart/form-data">
    <@s.hidden name="algorithm.uuid" id="id"></@s.hidden>
    <div class="control-group">
        <div class="controls">
            <@s.textfield name="algorithm.name" label="Name"></@s.textfield>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <@s.select label="Type"
            name="algorithm.type"
            list="{'FINGERVEIN', 'FINGERVEIN'}"
            headerKey="0"
            headerValue="Choose a algorithm type"
            ></@s.select>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <@s.select label="Protocol"
            name="algorithm.protocol"
            list="{'FVC2006', 'FVC2004', 'RATE'}"
            ></@s.select>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <@s.textarea label="Description"
            name="algorithm.description" ></@s.textarea>
        </div>
    </div>

    <@s.submit></@s.submit>
</@s.form>
</#macro>