<table align='center' border='0'>
<tr>
<td align='center'>${today!?string('yyyy年M月d日')}</td>
</tr>
<tr>
<#if weekday == 1 || weekday == 7 >
<#assign color='#f00'>
<#else>
<#assign color='#00f'>
</#if>
<td align='center' style='font-size:24px;color:${color};font-weight:bold;'>
${today!?string('E')}
</td>
</tr>
<tr>
<td align='center' style='color:#060;'>
农历${nongli!}
<#if jieqi?? >
今日${jieqi!}
 </#if>
</td>
</tr>
</table>