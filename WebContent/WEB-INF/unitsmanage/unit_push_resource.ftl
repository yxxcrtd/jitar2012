<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script type="text/javascript">
  function doAction(param)
  {
  	document.getElementById("F2").cmd.value = param;
  	document.getElementById("F2").submit();
  }
  function select_all(o)
  {
    ele = document.getElementsByName("guid")
    for(i = 0;i<ele.length;i++) ele[i].checked=o.checked;
  }
  
  function doFilter()
  {
  	var f = document.getElementById('F2');
  	var srecommendState = f.recommendState.options[f.recommendState.selectedIndex].value;
  	var sauditState = f.auditState.options[f.auditState.selectedIndex].value;
  	<#if unit.parentId !=0>
   	var smultiPushState = f.multiPushState.options[f.multiPushState.selectedIndex].value;
   	<#else>
   	var smultiPushState = "";
   	</#if>
  	var qs = "recommendState=" + srecommendState + "&auditState=" + sauditState + "&multiPushState=" + smultiPushState + "&type=filter"
  	var url = "unit_push_resource.py?unitId=${unit.unitId}&";
  	window.location.href = url + qs;  	
  }  
  </script>
</head>
<body>
<h2>下级推送资源管理</h2>
<form method='GET' action='unit_push_resource.py'>
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='unitId' value='${unit.unitId}' />
<div style='text-align:right'>
关键字：<input name='k' size='20' value="${k!?html}" />
<select name='f'>
  <option value='title'${(f=='title')?string(' selected="selected"','')}>资源标题、标签</option>
  <option value='uname'${(f=='uname')?string(' selected="selected"','')}>发表用户</option>
</select>
<input type='submit' value='搜索' />
</div>
</form>

<form method='post' id='F2' action="?">
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='unitId' value='${unit.unitId}' />
<#if resource_list??>
<table class='listTable' cellspacing='1' style="table-layout:fixed">
  <thead>
    <tr>
      <th style='width:17px'><input type='checkbox' onclick='select_all(this)' id='chk' /></th>
      <th>上传者</th>
      <th>资源类型</th>
      <th>学科/学段</th>
      <th>上传时间</th>
      <th>大小</th>
      <th>
			<nobr>
			<select name='auditState' onchange='doFilter()'>
				<option value=''>本级审核状态</option>
				<option value='0' ${(auditState=='0')?string(' selected="selected"','')}>已审</option>
				<option value='1' ${(auditState=='1')?string(' selected="selected"','')}>未审核</option>
			</select>
			</nobr>      
    </th>
		<th><nobr>
		<select name='recommendState' onchange='doFilter()'>
			<option value=''>推荐状态</option>
			<option value='1' ${(recommendState=='1')?string(' selected="selected"','')}>已推荐</option>
			<option value='0' ${(recommendState=='0')?string(' selected="selected"','')}>未推荐</option>
		</select>
		</nobr>
		</th>
		<#if unit.parentId !=0>
		<th><nobr>
		<select name='multiPushState' onchange='doFilter()'>
			<option value=''>向上级推送状态</option>
			<option value='1' ${(multiPushState=='1')?string(' selected="selected"','')}>已推送</option>
			<option value='0' ${(multiPushState=='0')?string(' selected="selected"','')}>未推送</option>
		</select>
		</nobr>
		</th>
	    </#if>
    </tr>
  </thead>
  <tbody>
  <#if resource_list?size == 0>
    <tr>
      <td colspan='8' style='padding:12px' align='center' valign='center'>没有找到符合条件的资源</td>
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
      <td style='background-color:#eeeeee'><nobr>${resource.createDate?string('yyyy-MM-dd HH:mm')}</nobr></td>
      <td style="background-color: #EEEEEE; text-align: center;">
      	${resource.fsize} K
      	</td>
      <td style="background-color:#EEEEEE; text-align: center;">
     	<nobr>
			<#if resource.approvedPathInfo?? >
			 <#if resource.approvedPathInfo?index_of("/" + unit.unitId + "/") == -1 >
			 <span style="color:red">未审核</span>
			 <#else>
			  已审核
			 </#if>
			<#else>
			<span style="color:red">未审核</span>
			</#if>
			</nobr>
      </td>
      <td style="background-color:#EEEEEE; text-align: center;"> 
        <nobr>
				<#if resource.rcmdPathInfo??>
				<#if resource.rcmdPathInfo?index_of('/'+ unit.unitId +'/') &gt; -1 >
				已推荐
				</#if>
				</#if>
				</nobr>
      </td>
      <#if unit.parentId !=0>
      <td style="background-color:#EEEEEE; text-align: center;"> 
        <nobr>
				<#if resource.unitPathInfo?index_of("/"+ unit.parentId +"/") ==-1 >
				<span style="color:red">未推送</span>
				<#else>
				已推送
				</#if>
				</nobr>
      </td>
      </#if>
    </tr>
    <tr>
      <td colspan='8' style='padding-left:8px;line-height:1.5em'>
      	<b>资源标题： </b><img src='${Util.iconImage(resource.href!)}' border='0' align='absmiddle' />&nbsp;<a href='${SiteUrl}showResource.action?resourceId=${resource.resourceId}&shareMode=${resource.shareMode}' target="_blank">${resource.title!}</a>
        <#if resource.rcmdPathInfo??>
				<#if resource.rcmdPathInfo?index_of('/'+ unit.unitId +'/') &gt; -1 >
				<img src='${SiteUrl}manage/images/ico_rcmd.gif' border='0' align='absmiddle' />
				</#if>
				</#if>
        <b> 下载： </b> ${resource.downloadCount!}, <b>评论： </b> ${resource.commentCount!}
        <br/>
        <b>文件路径： </b><a href='${SiteUrl}${resource.href!}' target='_blank'>${Util.fileName(resource.href)}</a>
        <br/>
        <b>上传者IP：</b> ${resource.addIp!'未知'}
        <b>标签：</b><#list Util.tagToList(resource.tags!) as t>${t!?html}${t_has_next?string(', ','')}</#list>  
       </td>
      <#if platformType == "2">
      <td>
      <#if resource.pushState  == 1 >已推送</#if>
	  <#if resource.pushState  == 2 ><span style='color:red'>待推送</span></#if>
      </td>
      </#if>
    </tr>
  </#list>
  </tbody>
</table>
</#if>
<div style='padding:6px'>
  <input class='button' type='button' value='全选' onclick='document.getElementById("chk").click()' />
  <input class='button' type='button' value='删除' onclick='doAction("remove")' />
  <input class='button' type='button' value='审核' onclick='doAction("approveLevel")' />
  <input class='button' type='button' value='取消审核' onclick='doAction("unapproveLevel")' />
  <#if unit.parentId!=0>
  <input class='button' type='button' value='向上级推送' onclick='doAction("pushup")' />
  <input class='button' type='button' value='取消向上级推送' onclick='doAction("unpushup")' />
  </#if>
  <input class='button' type='button' value='设为推荐' onclick='doAction("setRcmd")' />
  <input class='button' type='button' value='取消推荐' onclick='doAction("unsetRcmd")' />
</div>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">		
</#if>
</form>
</body>
</html>