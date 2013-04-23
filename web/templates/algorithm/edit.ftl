<#include "../base.ftl" />
    <#macro main_container>
        <h3><a href="<@s.url action="show" ><@s.param name="uuid">${algorithm.uuid}</@s.param></@s.url>">${algorithm.name}</a></h3>
        <hr>
        <@s.form action="update" cssClass="form-horizontal" enctype="multipart/form-data" theme="simple">
            <@s.hidden name="algorithm.uuid" id="id"></@s.hidden>
            <#include "_form.ftl" />

        </@s.form>
    </#macro>