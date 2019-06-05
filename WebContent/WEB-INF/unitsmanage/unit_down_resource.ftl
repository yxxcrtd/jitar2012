<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
  
  <script type="text/javascript">
  function doAction(param)
  {
  	document.getElementById("oForm").cmd.value = param;
  	document.getElementById("oForm").submit();
  }
  function select_all(o)
  {
    ele = document.getElementsByName("guid")
    for(i = 0;i<ele.length;i++) ele[i].checked=o.checked;
  }
  var ary = [];
  
  function Filter()
  {
  	var app = document.getElementById("oForm").approve.value;
  	var rcd = document.getElementById("oForm").rcmd.value;
  	var delState = document.getElementById("oForm").delState.value;
  	var url = "${SiteUrl}units/manage/unit_down_resource.py?unitId=${unit.unitId}&auditState="+app+"&delState="+delState+"&recommendState=" + rcd;
  	window.location.href=url;
  }
  </script>
</head>
<body>
<h2>下级全部资源管理</h2>
<form method='get' action='unit_down_resource.py'>
<input type='hidden' name='unitId' value='${unit.unitId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>资源标题、标签</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>资源发表用户</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>
<form method='post' id='oForm'>
<input type='hidden' name='cmd' value='' />
<table class='listTable' cellspacing='1' style="table-layout:fixed">
  <thead>
    <tr>
      <th style='width:17px'><input type='checkbox' onclick='select_all(this)' id='chk' /></th>
      <th>上传者</th>
      <th>资源类型</th>
      <th>学科/学段</th>
      <th>作者机构</th>
      <th>上传时间</th>
      <th>大小</th>
<th><nobr>
<select name="delState" onchange="Filter()">
<option value="">删除状态</option>
<option value="0"<#if delState=="0"> selected="selected"</#if>>正常</option>
<option value="1"<#if delState=="1"> selected="selected"</#if>>待删除</option>
</select>
</nobr></th>
<th><nobr>
<select name="approve" onchange="Filter()">
<option value="">审核状态</option>
<option value="0"<#if auditState=="0"> selected="selected"</#if>>已审核</option>
<option value="1"<#if auditState=="1"> selected="selected"</#if>>未审核</option>
</select>
</nobr></th>
<th><nobr>
<select name="rcmd" onchange="Filter()">
<option value="">推荐状态</option>
<option value="1"<#if recommendState=="1"> selected="selected"</#if>>已推荐</option>
<option value="0"<#if recommendState=="0"> selected="selected"</#if>>未推荐</option>
</select>
</nobr></th>
    </tr>
  </thead>
  <tbody>
  <#if resource_list?size == 0>
    <tr>
      <td colspan='10' style='padding:12px' align='center' valign='center'>没有找到符合条件的资源</td>
    </tr>
  </#if>
  <#list resource_list as resource>
    <#assign u = Util.userById(resource.userId)>
    <tr>
      <td style='background-color:#eeeeee;'>
        <input type='checkbox' name='guid' value='${resource.resourceId}' />
      </td>      
      <td style="background-color: #EEEEEE; text-align: center;">
        <a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!}</a>
      </td>
      <td style="background-color: #EEEEEE; text-align: center;">
        ${resource.sysCateName!?html}
      </td>
      <td style="background-color: #EEEEEE; text-align: center;">
	   	<#if resource.subjectId??>
			${Util.subjectById(resource.subjectId).msubjName!?html}
		</#if>
		/
    	<#if resource.gradeId??>
      	${Util.gradeById(resource.gradeId).gradeName!?html}
    	</#if>
	  </td>
	  <td style='background-color:#eeeeee'><nobr>
		<#if resource.unitId??>
		<#assign rUnit = Util.unitById(resource.unitId)>
		<#if rUnit?? && rUnit != "">
		<a href='${SiteUrl}go.action?unitName=${rUnit.unitName}' target='_blank'>${rUnit.unitTitle!?html}</a>
		</#if>
		</#if>
		</nobr>
		</td>
		<td style='background-color:#eeeeee'><nobr>${resource.createDate?string('yyyy-MM-dd HH:mm')}</nobr></td>
    <td style="background-color: #EEEEEE; text-align: center;">
      	${resource.fsize} K
    </td>
    <td style="background-color:#EEEEEE; text-align: center;color:red"><#if resource.delState>待删除</#if></td>
 	<td style="background-color:#EEEEEE; text-align: center;color:red" id='container_a_${resource.resourceId}'>
	<script>
	ary.push(${resource.resourceId});
	</script>
	</td>
	<td style="background-color:#EEEEEE; text-align: center;color:red" id='container_r_${resource.resourceId}'></td>
    </tr>
    <tr>
      <td colspan='9' style='padding-left:8px;line-height:1.5em'>
      	<b>资源标题： </b><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' />&nbsp;<a href='${SiteUrl}showResource.action?resourceId=${resource.resourceId}&shareMode=${resource.shareMode}' target="_blank">${resource.title!}</a>
        <#if resource.recommendState><img src='${SiteUrl}manage/images/ico_rcmd.gif' border='0' align='absmiddle' /></#if>
        <b> 下载： </b> ${resource.downloadCount!}, <b>评论： </b> ${resource.commentCount!}
        <br/>
        <b>文件路径： </b><a href='${SiteUrl}${resource.href!}' target='_blank'>${Util.fileName(resource.href)}</a>
        <br/>
        <b>上传者IP：</b> ${resource.addIp!'未知'}
        <b>标签：</b><#list Util.tagToList(resource.tags!) as t>${t!?html}${t_has_next?string(', ','')}</#list>  
       
       
       <#if resource.approvedPathInfo?? >
       <br/>
       <b>机构审核状态：</b>
       ${resource.approvedPathInfo}
       
       <#assign at = Util.convertUnitStringPathToUnitTitlePath(resource.approvedPathInfo)>
				${at!}
			</#if>
       <#if resource.rcmdPathInfo??>
       <br/><b>机构推荐状态：</b>
       
				${resource.rcmdPathInfo}
				<#assign ct = Util.convertUnitStringPathToUnitTitlePath(resource.rcmdPathInfo)>
				${ct!}
				</#if>
       </td>      
    </tr>
  </#list>
  </tbody>
</table>

<div class='pager'>
<#if pager??>
  <#include "/WEB-INF/ftl/pager.ftl">		
</#if> 
</div>
<div style='padding:6px'>
  <input class='button' type='button' value='全选' onclick='document.getElementById("chk").click()' />
  <input class='button' type='button' value='待删除' onclick='doAction("delete")' />
  <input class='button' type='button' value='撤销待删除' onclick='doAction("undelete")' />
  <input class='button' type='button' value='彻底删除' onclick='doAction("crash")' />
  <input class='button' type='button' value='审核' onclick='doAction("approveLevel")' />
  <input class='button' type='button' value='取消审核' onclick='doAction("unapproveLevel")' />
	<input class='button' type='button' value='设为推荐' onclick='doAction("setRcmd")' />
	<input class='button' type='button' value='取消推荐' onclick='doAction("unsetRcmd")' />
	<#if resource_categories??>
  <input type='button'  class='button' value='设置资源类型' onclick='doAction("move_cate");' />
  <select name='sysCateId'>
    <option>选择资源类型</option>
    <option value='0'>设置为空类型</option>
  <#list resource_categories.all as c>
    <option value='${c.categoryId}'>${c.treeFlag2!} ${c.name!}</option>
  </#list>
  </select>
</#if>
</div>
</form>
<script>
if(ary.length>0)
{
postData = "unitId=${unit.unitId}&data=" + ary.toString();
new Ajax.Request('${SiteUrl}jython/getResourceStatus.py?'+ (Date.parse(new Date())), { 
      method: 'post',
      parameters:postData,
      onSuccess:function(xport){
      retId = xport.responseText.replace(/\r\n/g,"")
      retId = retId.split(",")
      for(i=0;i<retId.length;i++)
      {
      if(retId[i]!="")
      {
       document.getElementById("container_a_"+retId[i]).innerHTML="<span style='color:red'>待审核</span>"
      }
      }
      
      },
      onException: function(xport, ex){}        
    }
  );
new Ajax.Request('${SiteUrl}jython/getResourceRcmdStatus.py?'+ (Date.parse(new Date())), { 
      method: 'post',
      parameters:postData,
      onSuccess:function(xport){
      retId = xport.responseText.replace(/\r\n/g,"")
      retId = retId.split(",")
      for(i=0;i<retId.length;i++)
      {
      if(retId[i]!="")
      {
       document.getElementById("container_r_"+retId[i]).innerHTML="<span style='color:red'>未推荐</span>"
      }
      }
      
      },
      onException: function(xport, ex){}        
    }
  );
    
  
}
</script>
</body>
</html>