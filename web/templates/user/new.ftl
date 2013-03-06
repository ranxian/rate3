<#include "../base.ftl" />

<#macro main_container>
    <h1>Create Your Account!</h1>
    <hr>
    <@s.form action="create" cssClass="form-horizontal" theme="simple">
        <div class="control-group">
            <div class="control-label">
                <label>Name</label>
            </div>
            <div class="controls">
                <@s.textfield  name="user.name" />
            </div>
        </div>
    <div class="control-group">
        <div class="control-label">
            <label>Password</label>
        </div>
        <div class="controls">
            <@s.password  name="user.password" />
        </div>
    </div>
    <div class="control-group">
        <div class="control-label">
            <label>Email</label>
        </div>
        <div class="controls">
            <@s.textfield name="user.email"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-label">
            <label>Organization</label>
        </div>
        <div class="controls">
            <@s.textfield name="user.organization" />
        </div>
    </div>

    <div class="form-actions">
        <@s.submit cssClass="btn" value="Register"></@s.submit>
    </div>

    </@s.form>

</#macro>