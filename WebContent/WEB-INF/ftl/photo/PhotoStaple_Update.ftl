<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"]>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>修改相册分类</title>
		<link rel="stylesheet" type="text/css" href="../css/manage.css">
        <script type="text/javascript">
          function checkData(frm)
          {
              if(frm.title.value == ""){  
                  alert("请输入相册分类名称");
                  return false;
              }
              return true;
          }
        </script>		
	</head>
	
	<body onLoad="javascript:document.add_form.title.focus();" style="margin-top: 20px;">
		<h2>修改相册分类</h2>
		<form name="add_form" action="photostaple.action?cmd=updsave" method="post" onsubmit="return checkData(this);">
			<table class="listTable" width="100%" cellSpacing="1" cellPadding="0">
				<tr>
					<td align="right" width="35%" height="30">相册分类名称(<font color=red>*</font>)：</td>
					<td><@s.textfield name="title" size="40" maxLength="25" />不能超过50字符(25个汉字)</td>
					<td><@s.fielderror cssStyle="color:#FF0000; font-weight:bold;" /></td>
				</tr>
                <tr>
                    <td align="right" width="35%" height="30">上级分类：</td>
                    <td>
                        <select name="parentId">
                          <option value='0'>(做为一级分类)</option>
                          <#list category_tree.all as c>
                              <option value='${c.categoryId}' ${(c.categoryId == photoStaple.parentId!0)?string('selected', '')}>${c.treeFlag2} ${c.name?html}</option>
                          </#list>
                        </select>
                    
                    </td>
                    <td><@s.fielderror cssStyle="color:#FF0000; font-weight:bold;" /></td>
                </tr>				
				<tr>
					<td align="right" width="35%" height="30">相册分类描述：</td>
					<td><@s.textarea cssStyle="overflow: auto;" name="stapleDescribe" cols="42" rows="10"></@s.textarea></td>					
					<td><@s.fielderror cssStyle="color:#FF0000; font-weight:bold;" /></td>
				</tr>
				<tr>
					<td align="right" height="30">是否隐藏：</td>
					<td>
						<input type="radio" name="isHide" value="false" ${isHide?string('', 'checked')} />否
						<input type="radio" name="isHide" value="true" ${isHide?string('checked', '')} />是
					</td>
					<td></td>
				</tr>
			</table>				
			<div align="center">
				<input class="button" type="hidden" name="myPhotoStapleId" value="${myPhotoStapleId}" />
				<input class="button" type="submit" value="确定修改" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="button" type="button" value="取消返回" onclick="window.location.href='photostaple.action?cmd=list'" />
			</div>
		</form>
	</body>
</html>
