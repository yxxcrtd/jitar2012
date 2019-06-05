<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>用户收藏管理</title>
		<link rel="stylesheet" type="text/css" href="${SiteUrl}css/manage.css" />
		<script type="text/javascript">
		<!--
		function confirm_delete(name) {
			return confirm("您确定要删除" + name + "吗？");
		}
		//-->
		</script>
	</head>

	<body>
		<h2>收藏管理</h2>

		<#assign canManage = (loginUser.userStatus == 0) >
		<div class='pos'>您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
			&gt;&gt; <a href='?cmd=list'>我的收藏夹</a>
		</div>

		<table class="listTable" cellspacing="1">
			<thead>
				<tr>
					<th width="40%">收藏标题</th>
					<th width="38%">收藏网址</th>
					<th width="16%">收藏时间</th>
					<th width="6%">删除</th>
				</tr>
			</thead>
			<tbody> 
			<#if fav_list??>
				<#list fav_list as fav>
					<tr>
						<td align="center">
							${fav.favTitle!?html}
						</td>
						<td>
						<a href='${fav.favHref!}' target='_blank'>${fav.favHref!?html}</a>
						</td>
						<td align="center">
							${fav.favDate}
						</td>
						<td align="center">
							<a href="?cmd=del&amp;favId=${fav.favId}" onclick="return confirm_delete('${fav.favTitle!?js_string}')">删除</a>
						</td>
					</tr>
				</#list>
			</#if>	
			</tbody>
		</table>
<div class='pager'>
	<#include "../inc/pager.ftl">
</div>
		
	</body>
</html>
