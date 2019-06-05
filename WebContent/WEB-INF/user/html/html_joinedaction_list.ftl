<#if action_list??>
 <table border='0' width='100%' cellspacing='1' class='table1'>
 <thead>
 <tr>
 <td style='width:100%'>活动标题</td>
 <td><nobr>总人数</nobr></td>
 <td>类型</td>
 <td>创建时间</td>
 <td>活动开始时间</td>
 </tr>
 </thead>
 <tbody>
 <#list action_list as a>
 <tr>
 <td><a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title}</a></td>
<td><nobr><#if a.userLimit == 0 >不限<#else>${a.userLimit}</#if></nobr></td>
 <td><nobr><span><#if a.ownerType == 'user' >个人<#elseif a.ownerType == 'group' >群组<#elseif a.ownerType == 'preparecourse' >集备<#elseif a.ownerType == 'subject' >学科<#else>未知</#if>活动</span></nobr></td>
 <td><nobr>${a.createDate}</nobr></td>
 <td><nobr>${a.startDateTime}</nobr></td>
 </tr>
 </#list>
 </tbody>
 </table>
</#if> 