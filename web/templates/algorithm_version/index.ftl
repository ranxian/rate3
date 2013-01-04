<#include "../base.ftl">
<#macro main_container>
        <h1 class="title">Versions of Algorithm <b>${algorithm.name}</b> </h1>
        <!--<a class="btn" href="/algorithm/new">New Algorithm</a>-->
        <div class="ratehr"></div>
        <table class="table table-hover" id="algorithm-version-list">
          <thead>
            <tr>
              <th>Created</th>
              <th>Description</th>
              <th>Number of Results</th>
            </tr>
          </thead>
          <tbody>
          <@s.iterator value="algorithm.algorithmVersionsByUuid">
              <tr>
                  <td><a href="<@s.url action="show"><@s.param name="uuid">${uuid}</@s.param></@s.url>"><@s.property value="created"></a></@s.property></td>
                  <td><@s.property value="description" /></td>
                  <td><@s.property value="numOfResults" /></td>
              </tr>
          </@s.iterator>
          </tbody>
        </table>
</#macro>