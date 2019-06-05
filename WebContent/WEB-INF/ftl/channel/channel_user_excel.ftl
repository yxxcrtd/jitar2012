<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv=Content-Type content="text/html; charset=GB2312">
<meta name=ProgId content=Excel.Sheet>
<style>
.X1{mso-number-format:"\@";}
</style>
</head>
<body>
<table border="1">
<thead>
<tr>
<th colspan="7">
${channel.title} 
<#if k != "" && f != "">
关键字：${k}
条件：<#if f == "0">用户登录名<#elseif f=="1">用户真实姓名<#elseif f == "2">用户机构真实名称<#else>未知</#if>
</#if>
<#if startDate != "">
开始时间：${startDate}
</#if>
<#if endDate != "">
结束时间：${endDate}
</#if>
</th>
</tr>
<tr>
<th>用户登录名</th>
<th>用户真实姓名</th>
<th>机构名称</th>
<th>文章数</th>
<th>资源数</th>
<th>视频数</th>
<th>图片数</th>
</tr>
</thead>
<#if stat_list?? >
<tbody>
<#list stat_list as t>
<tr>
<td>${t.loginName!}</td>
<td>${t.userTrueName!}</td>
<td>${t.unitTitle!}</td>
<td class="X1">${t.articleCount}</td>
<td class="X1">${t.resourceCount}</td>
<td class="X1">${t.videoCount}</td>
<td class="X1">${t.photoCount}</td>
</tr>
</#list>
</tbody>
<#else>
<tbody>
<td colspan="7">无数据</td>
</tbody>
</#if>
</table>
</body>
</html>