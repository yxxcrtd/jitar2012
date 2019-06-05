<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>用户分类管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
		<script type="text/javascript">
		<!--
		function confirm_delete(name) {
			return confirm("您确定要删除 " + name + " 分类吗？");
		}
		//-->
		</script>
	</head>

	<body>
		<h2>个人文章分类</h2>

		<#assign canManage = (loginUser.userStatus == 0) >
		<div class='pos'>您现在的位置：<a href='${SiteUrl}manage/' target='_top'>个人控制面板</a>
			&gt;&gt; <a href='article.action?cmd=list'>文章管理</a>
			&gt;&gt; <a href='?cmd=list'>个人文章分类管理</a>
		</div>

		<table class="listTable" cellspacing="1">
			<thead>
				<tr>
					<th width="10%">标识</th>
					<th width="60%">分类名称(子分类数)</th>
					<th width="10%">文章数</th>
					<th width="20%">分类操作</th>
				</tr>
			</thead>
			<tbody>
				<#list category_tree.all as category>
					<tr>
						<td align="center">
							${category.categoryId}
						</td>
						<td>
							${category.treeFlag2!} ${category.name} <#if (category.childNum !=	0)>(${category.childNum})</#if>
						</td>
						<td align="center">
							${category.itemNum}
						</td>
						<td>
							<#if canManage>
								<a href="usercate.action?cmd=add&amp;cid=${category.id}">添加子分类</a>
								<a href="usercate.action?cmd=edit&amp;cid=${category.id}">修改</a>
								<#if !category.hasChild>
									<a onclick="return confirm_delete('${category.name!?js_string}')" href="usercate.action?cmd=delete&amp;cid=${category.id}">删除</a>
								</#if>
							</#if>
						</td>
					</tr>
				</#list>
			</tbody>
		</table>

		<form name='addCateForm' action='usercate.action' method='get'>
  			<input type='hidden' name='cmd' value='add' />
			<div class='funcButton'>
				<#if canManage>
    				<input type='submit' class='button' value='添加一级分类' />
    				<input type='button' class='button' value='分类排序' onclick='cateOrder();' />
				</#if>
			</div>
		</form>
		
		<form name='setOrderForm' action='category_Order.py' method='post' style='display:none'>
			<input type='hidden' name='cmd' value='list' />
			<input type='hidden' name='type' value='user_${user.userId}' />
			<input type='hidden' name='parentId' value='' />
			
		</form>
		
		<script language="javascript">
		function cateOrder() {
			document.setOrderForm.submit();
		}
		</script>
	</body>
</html>
