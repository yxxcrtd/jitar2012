<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><@s.text name="groups.photo.manage" /></title>
		<link rel="styleSheet" type="text/css" href="../css/manage.css">
	</head>
	
	<body style="margin-top: 20px;">
		<h2>
			全部分类
		</h2>		

		<table width="100%" cellSpacing="0" cellPadding="0" border="0">
			<tbody>
				<tr>
				<#if userPhotoStapleList.size() != 0>
					<@s.form name="listForm" method="post">
					<#-- 定义要显示的列数 columnCount -->
					<#assign columnCount = 4>				
					<#-- 计算显示当前记录集需要的表格行数 rowCount -->
				<#if userPhotoStapleList.size() % columnCount == 0>
					<#assign rowCount = (userPhotoStapleList.size() / columnCount) - 1>
				<#else>
					<#assign rowCount = (userPhotoStapleList.size() / columnCount)>
				</#if>
				 
				<#-- 输出表格 -->
				<table class="listTable" cellSpacing="1" align="center">					   		
								<#-- 外层循环输出表格的 tr -->
								<#list 0..rowCount as row >
									<tr>
										<#-- 内层循环输出表格的 td  -->
										<#list 0..columnCount - 1 as cell>
											<td align="center" width='${100 / columnCount}%'><br>
												<#-- 判断是否存在当前对象：存在就输出；不存在就输出空格 -->
												<#if userPhotoStapleList[row * columnCount + cell]??>						
													<#assign photo = userPhotoStapleList[row * columnCount + cell]>										
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${photo.title!?html}<br />
													<@s.a href="photo.action?cmd=list&amp;userStapleId=${photo.userStaple!}">
													<img src="${Util.thumbNails(photo.userHref!'images/photo_default.gif')}" style="margin:0 0 2px 18px; border:0px;" border="0" />
													</@s.a>
													<br>
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${photo.count!}&nbsp;&nbsp;张照片
													<br><br>
												<#else>
													&nbsp;<br><br>
												</#if>
											</td>
										</#list>
									</tr>
								</#list>
							</table>
						</@s.form>
					</#if>
				</tr>
			</tbody>
		</table>
	</body>
</html>
