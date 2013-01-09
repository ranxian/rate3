<#include "../base.ftl" />
    <#macro main_container>
        <h3><a href="<@s.url action="show" ><@s.param name="uuid">${algorithm.uuid}</@s.param></@s.url>">${algorithm.name}</a></h3>
        <hr>
        <@s.form action="update">
            <@s.hidden name="algorithm.uuid" id="id"></@s.hidden>
            <@s.textfield name="algorithm.name" label="Name"></@s.textfield>
            <@s.select label="Type"
                      name="algorithm.typ3e"
                      list="{'FINGERVEIN', 'FINGERVEIN'}"
                      headerKey="0"
                      headerValue="Choose a algorithm type"
                    ></@s.select>
            <@s.select label="Protocal"
                      name="algorithm.protocol"
                      list="{'FVC2006', 'FVC2004', 'RATE'}"
                    ></@s.select>
            <@s.textarea label="Description"
                        name="algorithm.description" ></@s.textarea>
            <@s.submit></@s.submit>
        </@s.form>
        <@s.debug/>
    </#macro>