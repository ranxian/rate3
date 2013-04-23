<#include "../base.ftl" />

<#macro main_container>

<h3><a href="<@s.url action="show" ><@s.param name="uuid">${view.uuid}</@s.param></@s.url>">${view.name}</a></h3>

<div class="ratehr"></div>
<form class="form-horizontal add-page" enctype="multipart/form-data" action="view/update" method="post">
    <@s.hidden name="view.uuid" value="${view.uuid}"></@s.hidden>
    <#include "_form.ftl" />
</form>

</#macro>