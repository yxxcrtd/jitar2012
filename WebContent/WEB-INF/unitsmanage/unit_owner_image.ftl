<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  var chk = true;
  function selAll()
  {
  	var fs = document.getElementsByName("photoId")
  	for(i=0;i<fs.length;i++)
  	fs[i].checked = chk;
  	chk = !chk;
  }
  function doAction(param)
  {
  	document.forms[1].cmd.value = param;
  	document.forms[1].submit();
  }
  function doFilter()
  {
  	var f = document.getElementById('oForm');
  	var sauditState = f.auditState.options[f.auditState.selectedIndex].value;
  	var sisPrivateShow = f.isPrivateShow.options[f.isPrivateShow.selectedIndex].value;
  	var qs = "auditState=" + sauditState + "&isPrivateShow=" +sisPrivateShow +  "&type=filter"
  	var url = "unit_image.py?unitId=${unit.unitId}&";
  	window.location.href = url + qs;  	
  }     
  </script>
</head>
<body>
<h2>
本机构照片管理
</h2>
<form method='GET' action='unit_image.py'>
<input type='hidden' name='unitId' value='${unit.unitId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='0'${(f=='0')?string(' selected="selected"','')}>标题、标签</option>
  <option value='1'${(f=='1')?string(' selected="selected"','')}>简要介绍</option>
  <option value='2'${(f=='2')?string(' selected="selected"','')}>发表用户</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='post' id='oForm'>
<input type="hidden" name="cmd" value="list" />
<table class="listTable" cellSpacing="1" style="table-layout:fixed">
	<thead>
		<tr>
			<th width="5%">
				选&nbsp;&nbsp;择
			</th>
			<th width="10%">
				照&nbsp;&nbsp;片
			</th>
            <th width="20%">
                    照片标题
            </th>
            <th width="10%">
                    登录名/&nbsp;呢称
            </th>
            <th width="25%">
                    上传日期/ <strong>IP</strong>
            </th>
            <th width="10%">
                    系统分类
            </th>
			<th width="10%">
			<nobr>
			<select name='auditState' onchange='doFilter()'>
				<option value=''>审核状态</option>
				<option value='0' ${(auditState=='0')?string(' selected="selected"','')}>已审核</option>
				<option value='1' ${(auditState=='1')?string(' selected="selected"','')}>未审核</option>
			</select>
			</nobr> 
			</th>
			<th width="10%">
			<nobr>
			<select name='isPrivateShow' onchange='doFilter()'>
				<option value=''>显示方式</option>
				<option value='0' ${(isPrivateShow=='0')?string(' selected="selected"','')}>公开</option>
				<option value='1' ${(isPrivateShow=='1')?string(' selected="selected"','')}>个人</option>
			</select>
			</nobr> 
			</th>
		</tr>
	</thead>
	
	<tbody>
		<#if photoList?size == 0>
		<tr>
			<td colSpan="9" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
				没有符合条件的照片信息！
			</td>
		</tr>
		</#if>
		
		<#list photoList as photo>
		<tr>
			<td style="text-align: center;">
				<input type="checkbox" name="photoId" value="${photo.photoId}" />
			</td>
			<td style="text-align: center;">
				<a href="${photo.href!}" target="_blank">
					<img src="${Util.thumbNails(photo.href!'images/default.gif')}" width="64" border="0" title="${photo.title}" />
				</a>
			</td>
            <td style="padding-left: 10px;">
                ${photo.title}
            </td>
            <td style="padding-left: 10px;">
                <a href="${SiteUrl}go.action?loginName=${photo.loginName!}" title="访问 ${photo.loginName!} 的个人空间" target="_blank">${photo.loginName}</a><br>
                <div style="line-height: 6px;">
                    <br />
                </div>
                ${photo.nickName}
            </td>
            <td style="padding-left: 10px;">
                [${photo.createDate!}]<br>
                <div style="line-height: 5px;">
                    <br />
                </div>
                [${photo.addIp!}]
            </td>
            <td style="text-align: center;">
                ${photo.sysPhotoName!}
            </td>
			<td style="text-align: center;">
				<#if photo.auditState == 0>
					已审核
				<#else>
					<font style="color: #FF0000;">
						未审核
					</font>
				</#if>
			</td>
			<td style="text-align: center;">
				<#if photo.isPrivateShow>
					个人
				<#else>
					<font style="color: #FF0000;">
					  公开
					</font>
				</#if>
			</td>
		</tr>
		</#list>
	</tbody>
</table>

<#if photoList?size != 0>
    <div style="width: 100%; text-align: right; margin: 3px auto 3px;">
        <#if pager??>
		  <#include "/WEB-INF/ftl/pager.ftl">		
		</#if>
    </div>
	<div class="funcButton">
		<input class="button" type="button" value="全部选择" onclick="selAll();" />
		<input class="button" type="button" value="审核通过" onclick="doAction('audit');" />
		<input class="button" type="button" value="取消审核" onclick="doAction('unaudit');" />
		<input class="button" type="button" value="删除选择" onclick="doAction('delete');" />
		<input class="button" type="button" value="只在个人空间显示" onclick="doAction('private');" />
	</div>
</#if>

</form>
</body>
</html>