<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>后台管理</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
</head>
<body>
<#include "admin_header.ftl" >
<h2>后台管理</h2>
<table class="listTable" cellSpacing="1">
<thead>
<tr>
<td style="width: 100%; height: 30px; font-weight: bold;" colSpan="2">
<b>&nbsp;&nbsp;&nbsp;&nbsp;系统内部状态：</b>
</td>
</tr>
</thead>
<#if site_stat??>
<tbody>
<tr>
<td style="width: 20%; height: 30px; text-align: right; padding-right: 10px;">注册用户人数： </td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.userCount!0}</td>                    
</tr>
<tr>
<td style="height: 30px; text-align: right; padding-right: 10px;">发表的文章数：</td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.totalArticleCount!0}</td>                    
</tr>
<tr>
<td style="height: 30px; text-align: right; padding-right: 10px;">发布的资源数：</td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.totalResourceCount!0}</td>
</tr>
<tr>
<td style="height: 30px; text-align: right; padding-right: 10px;">发布的图片数：</td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.photoCount!0}</td>  
</tr>
<tr>
<td style="height: 30px; text-align: right; padding-right: 10px;">发布的视频数：</td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.videoCount!0}</td>
</tr>
<tr>
<td style="height: 30px; text-align: right; padding-right: 10px;">创建的集备数：</td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.prepareCourseCount!0}</td>
</tr>
<tr>
<td style="height: 30px; text-align: right; padding-right: 10px;">发布的评论数：</td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.commentCount!0}</td>
</tr>
<tr>
<td style="height: 30px; text-align: right; padding-right: 10px;">创建的协作组数：</td>
<td style="padding-left: 10px; color: #FF0000; font-weight: bold;">${site_stat.groupCount!0}</td>
</tr>
</tbody>
</#if>
</table>
</body>
</html>