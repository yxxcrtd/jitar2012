<table border="0" cellspacing='0' class='res_table'>
<thead>
<tr>
<td class='td_left' style='padding-left:10px'>课题</td>
<td class='td_middle' nowrap='nowrap'>学科/学段</td>
<td class='td_middle' nowrap='nowrap'>授课时间</td>
</tr>
</thead>
<tbody>
<tr>
<td colspan='4' style='padding:4px;'></td>
</tr>
  <#if plan_list??>
  <#list plan_list as c>
     <tr>
      <td style='padding-left:10px'>
        <a target="_blank" href='${SiteUrl}evaluations.action?cmd=content&evaluationPlanId=${c.evaluationPlanId}'>${c.evaluationCaption!?html}</a>
      </td>
      <td>${c.msubjName!}/${c.gradeName!}</td>
      <td>${c.teachDate?string("yyyy-MM-dd")}</td>
      </tr>
   </#list>
   </#if>  
</tbody>
</table> 
 <#if ispage=="1">
    <div class='pgr'><#include "/WEB-INF/ftl/inc/pager.ftl" ></div> 
 </#if>  