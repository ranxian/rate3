<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">New Algorithm</h1>
<div class="ratehr"></div>

<@s.form action="create" cssClass="add-page form-horizontal" enctype="multipart/form-data" theme="simple">
    <@s.hidden name="algorithm.uuid" id="id"></@s.hidden>
    <div class="control-group">
        <div class="control-label">
            <label>Name</label>
        </div>
        <div class="controls">
            <input type="text" name="algorithm.name" required>
        </div>
    </div>

    <div class="control-group">
        <div class="control-label">
            <label>Type</label>
        </div>
        <div class="controls">
            <@s.select label="Type"
            name="algorithm.type"
            list="{'FINGERVEIN'}"
            ></@s.select>
        </div>
    </div>

    <div class="control-group">
        <div class="control-label">
            <label>Protocol</label>
        </div>
        <div class="controls">
            <@s.select label="Protocol"
            name="algorithm.protocol"
            list="{'PKURATE', 'FVC2006', 'FVC2004'}"
            ></@s.select>
        </div>
    </div>

    <div class="control-group">
        <div class="control-label">
            <label>Description</label>
        </div>
        <div class="controls">
            <@s.textarea label="Description"
            name="algorithm.description" ></@s.textarea>
        </div>
    </div>

    <div class="form-actions">
        <@s.submit cssClass="btn"></@s.submit>
    </div>

</@s.form>
</#macro>