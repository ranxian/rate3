<#if isCurrentPageValid >

<#if prevPageUrl??><a href="${prevPageUrl}">Prev</a></#if>
${page}/${numOfPages}
<#if nextPageUrl??><a href="${nextPageUrl}">Next</a></#if>

<#else>

This page if out of range.

</#if>