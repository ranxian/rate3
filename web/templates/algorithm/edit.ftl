<#include "../base.ftl" />
    <#macro main_container>
        <h3><a href="<@s.url action="show" ><@s.param name="uuid">${algorithm.uuid}</@s.param></@s.url>">${algorithm.name}</a></h3>
        <hr>
        <@s.form action="update" cssClass="form-horizontal" enctype="multipart/form-data" theme="simple">
            <@s.hidden name="algorithm.uuid" id="id"></@s.hidden>
            <div class="control-group">
                <div class="control-label">
                    <label>Name</label>
                </div>
                <div class="controls">
                    <@s.textfield name="algorithm.name" label="Name"></@s.textfield>
                </div>
            </div>

            <div class="control-group">
                <div class="control-label">
                    <label>Type</label>
                </div>
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
                <div class="control-label">
                    <label>Protocol</label>
                </div>
                <div class="controls">
                    <@s.select label="Protocol"
                    name="algorithm.protocol"
                    list="{'FVC2006', 'FVC2004', 'RATE'}"
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