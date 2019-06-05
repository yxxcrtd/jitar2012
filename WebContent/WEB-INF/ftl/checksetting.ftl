<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style>
table{background:#000;border:4px solid #74a621;border-radius:8px; box-shadow: 0 0 3px #fff, 0 0 6px #74a621, 0 0 6px #74a621;}
tr{background: #FFF} /* 兼容低版本浏览器  */
th{text-align:right;background: #FFF}
tr:nth-child(odd) {background: #FFF}
tr:nth-child(even) {background: #e5f3d2}
a:link {color: #0000FF;}     /* unvisited link */
a:visited {color: #F00;}  /* visited link */
a:hover {color: #FF0000}   /* mouse over link */
a:active {color: #0000FF}   /* selected link */
</style>
<title>网站配置管理</title>
</head>
<body>
<h2>网站配置管理</h2>
<hr/>
<#if errorMessage??>
<div style="padding:10px;text-align:center;color:#00f;border:2px solid #00f;font-weight:bold;">${errorMessage}</div>
<br/>
</#if>
<table border="0" cellpadding="6" cellspacing="1">
<tr>
<th>网站地址：</th>
<td><a href="${SiteUrl!}">${SiteUrl!}</a> | <a href="${SiteUrl!}">访问当前网站</a></td>
</tr>
<tr>
<th>统一用户地址配置</th>
<td>${UserMgrClientUrl!}，<a href="login.jsp">用户登录</a> | <a href="manage/admin.py">进入系统后台管理</a></td>
</tr>
<tr>
<th>Java 安装目录</th>
<td>${JavaHome!}</td>
</tr>
<tr>
<th>是否有根单位</th>
<td>
<#if rootUnit??>
${rootUnit.unitTitle!}（${rootUnit.siteTitle!}）
<#else>
没有根单位，<a href="add_root_unit.py">创建根单位</a>
</#if>
</td>
</tr>
<tr>
<th>网站样式配置</th>
<td>
<#if siteThemeUrlConfig??>
${siteThemeUrlConfig!},<a href="checksetting.py?cmd=themeurl">重新配置网站样式</a>
<#else>
没有配置网站样式，<a href="checksetting.py?cmd=themeurl">配置网站样式为：${addSiteThemeUrl!}</a>
</#if>
</td>
</tr>
<tr>
<th>首页缓存文件是否存在</th>
<td>
<#if indexHtmlFile??>
存在，<a href="checksetting.py?cmd=delete_index">删除首页缓存文件</a> | <a href="index.action?debug=1">无缓存方式访问网站首页</a>
<#else>
不存在，<a href="checksetting.py?cmd=create_index">重新生成首页缓存文件</a>
</#if>
</td>
</tr>
<tr>
<th>学科导航缓存文件是否存在</th>
<td>
<#if subjectNavFile??>
存在，<a href="checksetting.py?cmd=delete_subject_nav">删除学科导航缓存文件</a> | <a href="checksetting.py?cmd=create_subject_nav">重新生成学科导航缓存文件</a>
<#else>
不存在，<a href="checksetting.py?cmd=create_subject_nav">生成学科导航缓存文件</a>
</#if>
</td>
</tr>
<tr>
<th>网站更新信息文件是否存在</th>
<td>
<#if updateInfoFile??>
存在，<a href="checksetting.py?cmd=empty_updateinfo">清空网站更新信息文件内容</a>
<#else>
不存在，<a href="checksetting.py?cmd=add_updateinfo">创建一个网站更新信息文件</a>
</#if>
</td>
</tr>
<tr>
<th>单位缓存</th>
<td>
<a href="checksetting.py?cmd=empty_unit">清空单位缓存文件夹</a>
</td>
</tr>
<tr>
<th>用户缓存</th>
<td>
<a href="checksetting.py?cmd=empty_user">清空用户缓存文件夹</a>
</td>
</tr>
<tr>
<th>其他内存缓存</th>
<td>
<a href="checksetting.py?cmd=empty_cache">清空所有内存缓存</a>
</td>
</tr>
<tr>
<th>是否有效许可证</th>
<td>
<#if IsValid>是<#else>否</#if>
</td>
</tr>
<tr>
<th>许可证单位名称</th>
<td>
${ProductName!}
</td>
</tr>
<tr>
<th>许可证单位级别</th>
<td>
${UnitLevel!}
</td>
</tr>
<tr>
<th>许可证用户数量</th>
<td>
${UserCount!}
</td>
</tr>
</table>
</body>
</html>