<#assign grpName="协作组">
<#assign grpShowName="小组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
        <#assign grpShowName="课题">
    <#else>
        <#assign grpName="协作组">
        <#assign grpShowName="小组">
    </#if>
</#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>添加修改组内链接</title>
  <link rel="stylesheet" href="../css/manage.css" type="text/css" />
  <script type="text/javascript">
     function checkData(frm)
    {
        if(frm.title.value=="")
        {
            alert("请输入链接名称");
            return false;
        }
        if(frm.address.value=="" || frm.address.value=="http://")
        {
            alert("请输入链接地址");
            return false;
        }        
        return true;
     }  
   </script>  
</head>
<body>
 <#include 'group_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>${grpName}管理首页</a>
  &gt;&gt; <a href='groupLink.action?cmd=listLink&amp;groupId=${group.groupId}'>${grpName}链接管理</a>
  &gt;&gt; <span>${(link.linkId == 0)?string("添加","修改")}组内链接</span>
</div>
<br/>

<form name="list_form" action="groupLink.action" method="post" onsubmit="return checkData(this);">
  <input type='hidden' name='cmd' value='saveLink' />
  <input type='hidden' name='groupId' value='${group.groupId}'>
  <input type='hidden' name='linkId' value='${link.linkId}' />
<#if __referer??>
	<input type='hidden' name='__referer' value='${__referer!}' />
</#if>
<table class="listTable" cellspacing="1">
	<tr>
		<td align="right" width='20%'><b>链接名称(<font color="red">*</font>)：</b></td>
		<td>
			<input type="text" name="title" value="${link.title!?html}" size="40"/>
		</td>
	</tr>
	<tr>
		<td align="right" width='20%'><b>链接地址(<font color="red">*</font>)：</b></td>
		<td>
		<#if link.linkId == 0>
			<input type="text" size="65" name="address" value="http://${link.linkAddress!?html}">
		<#else>
			<input type="text" size="65" name="address" value="${link.linkAddress!?html}">
		</#if>
		</td>
	</tr>
	<tr>
		<td align="right"><b>链接类型：</b></td>
		<td>
			<select name="type">
				<option value="1" ${(link.linkType == 1)?string('selected', '')}>外部链接</option>
				<option value="2" ${(link.linkType == 1)?string('selected', '')}>内部链接</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="right" valign="top"><b>链接描述：</b></td>
		<td>
			<textarea type="text" name="description" rows='10' cols="68">${link.description!?html}</textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><b>图片链接地址：</b>	</td> 			
		<td>
		<#if link.linkId == 0>
			<input type="text" name="linkIcon"  size="65" value="http://${link.linkIcon!?html}">
		<#else>
			<input type="text" name="linkIcon"  size="65" value="${link.linkIcon!?html}"> 						
		</#if>
		</td>
	</tr>
	<tr>
	  <td></td>
	  <td>
		  <input class="button" type="submit" value="${(link.linkId == 0)?string(' 添  加 ',' 修  改 ')} " />
		  <input class="button" type="button" value=" 返 回 " onclick="window.location.href='groupLink.action?cmd=listLink&groupId=${group.groupId}'" />
	  </td>
	</tr>
</table>

</form>

</body>
</html>
