<#include "../base.ftl" />

<#macro main_container>
    <h1>Create Your Account!</h1>
    <hr>
    <div class="success_box" style="display: none;"></div>
    <div class="error_box" style="display: none;"></div>
    <@s.form action="create" cssClass="form-horizontal" theme="simple" name="login-form">
        <div class="control-group">
            <div class="control-label">
                <label>Name</label>
            </div>
            <div class="controls">
                <input name="user.name" type="text" required data-validation-required-message="You must tell us who you are" />
            </div>
        </div>
    <div class="control-group">
        <div class="control-label">
            <label>Password</label>
        </div>
        <div class="controls">
            <input type="password" name="user.password" required >
        </div>
    </div>
    <div class="control-group">
        <div class="control-label">
            <label>Confirm</label>
        </div>
        <div class="controls">
            <input type="password" name="user.confirmation" data-validation-match-match="user.password">
        </div>
    </div>
    <div class="control-group">
        <div class="control-label">
            <label>Email</label>
        </div>
        <div class="controls">
            <input type="email" name="user.email" required >
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
    </script>
</#macro>