<#if user_leaveword_list.size() &gt; 0 >

<#list user_leaveword_list as lwd >
<table width='100%' style='border:2px solid #999;margin:2px 0;'>
<tr>
<td style='font-weight:bold;background:url(${SiteUrl}css/common/jian01.gif) no-repeat left center;padding-left:14px;'>${lwd.title!?html}</td><td>发送者：${lwd.nickName!?html}</td><td align='right'>${lwd.createDate}</td>
</tr>
<tr>
<td colspan='3' style='border:1px solid #EEE;padding:4px;'>${lwd.content!}</td>
</tr>
<#if lwd.reply ?? >
<tr>
<td colspan='3' style='padding:10px;'>
<div style='font-weight:bold;'>回复：</div>
<div style='padding:4px;border:1px dotted #666;background:#EEE;'>
 ${lwd.reply}
</div>
</td>
</tr>
</#if>
<tr>
<td colspan='3' style='padding:4px'></td>
</tr>
</table>
</#list>  

</#if>