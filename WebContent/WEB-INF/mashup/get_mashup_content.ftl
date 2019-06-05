<#if platfotm_list?? >
  <table border='0' align='center' cellspacing='4'>
  <#assign columnCount = 5>
  <#if platfotm_list.size() % columnCount == 0>
<#assign rowCount = platfotm_list.size() / columnCount >
  <#else>
<#assign rowCount = (platfotm_list.size() / columnCount)?int + 1 >
  </#if>
  <#list 0..rowCount - 1 as row>
<tr>
  <#list 0..columnCount - 1 as cell>	   
   <td style='width:20%'>
   <#if platfotm_list[row * columnCount + cell]?? >
   <#assign p = platfotm_list[row * columnCount + cell] >	  
   <a href='${p.platformHref}push/platform.py?g=${encUserGuid!?url}'>${p.platformName?html}</a>
   <#else>
   &nbsp;
   </#if>
   </td>
   <#if cell < columnCount - 1 >
   <td class='td_spacer'></td>
   </#if>
  </#list> 
</tr>
</#list>
</table>
</#if>