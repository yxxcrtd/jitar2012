<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>视频管理</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css" />
	</head>

	<body style="margin-top: 20px;">
		<h2>视频管理</h2>

		<table align="center">
			<tr>
				<td colspan="2">
					<@s.actionerror cssClass="actionError" />
				</td>
			</tr>
		</table>

		<@s.form name="editForm" action="video" method="post" enctype="multipart/form-data">	
			<table class="listTable" cellSpacing="1">
				<tbody>
                    <tr>
						<td style="text-align: right; width: 15%;">
							<b>视频类型：</b>
                        </td>
                        <td>
                        	<@s.radio name="typeState" value="false" list=r'#{"true" : "原创", "false" : "转载"}' required="true" />
						</td>
					</tr>
                    <tr>
                        <td style="text-align: right;">
                            <b>选择视频：</b>
                        </td>
                        <td>
                            <input type="file" name="file" id="uploadfile" value="" style="width: 335px;" />&nbsp;<font style="color: #FF0000; font-weight: bold;">*</font>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align: right;">
                            <b>视频标题：</b>
                        </td>
                        <td>
                            <input type="text" name="title" value="" style="width: 268px;" />
                            <input type="checkbox" name="nameAsTitle" value="true" checked="checked" />使用文件名作为标题 
                        </td>
                    </tr>
					<tr>
						<td style="text-align: right;">
							<b>视频标签：</b>
						</td>
						<td>
							<input type="text" name="tags" value="" style="width: 268px;" />&nbsp;(多个标签以英文的逗号分隔)
						</td>
					</tr>
                    <tr>
						<td style="text-align: right;">
							<b>视频分类：</b>
                        </td>
                        <td>
                        	<@s.radio name="staple" list=r'#{"教育":"教育", "社会":"社会", "综艺":"综艺", "音乐":"音乐", "体育":"体育", "搞笑":"搞笑", "游戏":"游戏", "动漫":"动漫", "军事":"军事", "汽车":"汽车", "旅游":"旅游", "其他":"其他"}' required="true" />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">
							<b>视频描述：</b>
						</td>
						<td>
						<textarea name="summary" style="width:100%;height:30px">${video.summary!?html}</textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<@s.hidden name="cmd" value="save" />
							<@s.hidden name="videoId" value="${videoId!}" />
							<input type="submit" class="button" value=" 上   传 " />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="button" value=" 返   回 " onClick="window.history.back(-1)" />
						</td>
					</tr>
				</tbody>
			</table>
		</@s.form>
	</body>
</html>
