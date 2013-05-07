<#include "../base.ftl" />

<#macro main_container>

<div class="alert alert-error">
    <p><h4>Oops, We crushed...!</h4></p>
    <div style ="color:red">
    <@s.fielderror/>
    </ div >
</div>

</#macro>