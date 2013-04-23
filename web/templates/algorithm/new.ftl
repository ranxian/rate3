<#include "../base.ftl" />
<#macro main_container>

<h1 class="title">New Algorithm</h1>
<div class="ratehr"></div>

<@s.form action="create" cssClass="add-page form-horizontal" enctype="multipart/form-data" theme="simple">
    <@s.hidden name="algorithm.uuid" id="id"></@s.hidden>
    <#include "_form.ftl" />
</@s.form>
</#macro>