<#include "../base.ftl" />

<#macro main_container>

<h3><a href="<@s.url action="show" ><@s.param name="uuid">${view.uuid}</@s.param></@s.url>">${view.name}</a></h3>

<div class="ratehr"></div>

<@s.form action="update">
   <@s.hidden name="view.uuid" id="id"></@s.hidden>
    <@s.textfield name="view.name" label="Name"></@s.textfield>

    <#--
    <@s.select label="Generator"
              name="view.generator"
              list="{'FVC2004', 'FVC2006'}"
              headerKey="0"
              headerValue="Choose a view generating strategy"
            ></@s.select>
    <@s.select label="Type"
              name="view.type"
              list="{'FINGERVEIN', 'FINGERVEIN'}"
              headerKey="0"
              headerValue="Choose a view type"
            ></@s.select>
     -->
    <@s.submit></@s.submit>
</@s.form>

</#macro>