<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>资源管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <link rel="stylesheet" href="css/flora.all.css" type="text/css" title="Flora (Default)" />
  <script type="text/javascript" src='js/admin_resource.js'></script>
  <script type="text/javascript">
  	function confirmDel(id)
  	{
		var toolbarSty = "dialogwidth=400px;dialogheight=200px;scroll:no;border=no;status=no;help=no";
		var url = "${SiteUrl}PunishScoreConfirm.py?seltype=score.resource.adminDel";
		var res = window.showModalDialog(url,null,toolbarSty);
		if(res==undefined){
			res="";
			return;
		}
		var arr=res.split("|");
  		self.document.location.href="admin_resource.py?ppp=${pager.currentPage}&cmd=delete&resourceId="+id+"&score="+arr[0]+"&reason="+encodeURI(arr[1]);
  	}
  	
	function publishtozyk(resourceId){
		var url="resource.action?cmd=select_resource_cate&resourceId="+resourceId;
		//window.open(url,"");
		//window.showModelessDialog(url,"","dialogHeight:540px;dialogWidth:700px;dialogLeft:50px;dialogTop:50px;resizable:yes;");
		window.showModalDialog(url,"","dialogHeight:540px;dialogWidth:700px;dialogLeft:50px;dialogTop:50px;resizable:yes;");
	}
  	
  </script>  
</head>

<body>
<#if !(type??)><#assign type = ''></#if>
<#if type == 'rcmd'><#assign typeTitle = '推荐资源管理'>
<#elseif type == 'unaudit'><#assign typeTitle = '待审核资源管理'>
<#else><#assign typeTitle = '资源管理'>
</#if>
<h2>${typeTitle}</h2>
  
<form action='?' method='get'>
<div class='pager'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value="${type!?html}" />
  关键字：<input type='text' name='k' value="${k!?html}" size='16' />
<#if !(f??)><#assign f = 'title'></#if>
  <select name='f'>
    <option value='title' ${(f == 'title')?string('selected', '')}>资源标题</option>
    <option value='intro' ${(f == 'intro')?string('selected', '')}>资源简介</option>
    <option value='uname' ${(f == 'uname')?string('selected', '')}>上传用户</option>
    <option value='unit' ${(f == 'unit')?string('selected', '')}>用户所属机构</option>
  </select>
<#if subject_list?? >
  <#if !(su??)><#assign su = 0></#if>
  <select name='su'>
    <option value=''>所属学科</option>
  <#list subject_list as subj>
    <option value='${subj.msubjId}' ${(su==subj.msubjId)?string('selected', '')}>
    	${subj.msubjName!?html}
    </option>
  </#list>
  </select>
</#if>
<select name="gradeId">
        <option value=''>所属学段</option>
<#if grade_list?? >
        <#list grade_list as grade>
        <#--<#if grade.isGrade>-->
            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                <#if grade.isGrade>
                  ${grade.gradeName!?html}
                <#else>
                  &nbsp;&nbsp;${grade.gradeName!?html}
                </#if>
              </option>
        <#--</#if>-->
        </#list>
</#if>
    </select>
    
<#if resource_categories??>
  <#if !(sc??)><#assign sc = 0></#if>
  <select name='sc'>
    <option value=''>资源类型</option>
  <#list resource_categories.all as c>
    <option value='${c.categoryId}' ${(sc == c.categoryId)?string('selected', '')}>${c.treeFlag2!} ${c.name!}</option>
  </#list>
  </select>
</#if>
<#if res_type??>
  <select name='rt'>
    <option value=''>元数据类型</option>
  <#list res_type.all as c>
    <option value='${c.categoryId}' ${((rt!0) == c.categoryId)?string('selected', '')}>${c.treeFlag2!} ${c.name!}</option>
  </#list>
  </select>
</#if>
<#if sm??>
  <select name='sm'>
    <option value='-1'>发布方式</option>
      <option <#if sm==1000>selected</#if> value='1000'>完全公开</option>
	  <option <#if sm==500>selected</#if>  value='500'>组内公开</option>
	  <option <#if sm==0>selected</#if> value='0'>私有</option>
  </select>
</#if>
<#if isHaszykUrl=='true'>
	<#if zyk??>
	  <select name='zyk'>
	  	<option value=''>推送状态</option>
	  	<option <#if zyk=='1'>selected</#if> value='1'>已推送到资源库</option>
	  	<option <#if zyk=='0'>selected</#if> value='0'>未推送到资源库</option>
	  </select>
	</#if>
</#if>  
  <input type='submit' class='button' value=' 查找 ' />
</div>
</form>

<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th>ID</th>
      <th>资源标题</th>
      <th>上传者</th>
      <th>资源类型</th>
      <th>学科/学段</th>
      <th>上传时间</th>
      <th>大小</th>
      <#if isHaszykUrl=='true'>
      <th>推送到</th>
      </#if>
      <th width="8%">操作</th>
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
      <td style='background-color:#eeeeee'>
        <input type='checkbox' name='resourceId' value='${resource.resourceId}' />
      </td>
      <td style='background-color:#eeeeee'>
        <a href='../showResource.action?resourceId=${resource.resourceId}&shareMode=${resource.shareMode}' target="_blank"><img src='${Util.iconImage(resource.href!, 1)}' 
          border='0' hspace='4' align='absmiddle' />${resource.title!}</a>
        <#if resource.recommendState><img src='images/ico_rcmd.gif' border='0' align='absmiddle' /></#if>
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
      <td style='background-color:#eeeeee'>${resource.createDate}</td>
      <td style="background-color: #EEEEEE; text-align: center;">
      	${resource.fsize} K
      </td>
      <#if isHaszykUrl=='true'>
      <td style="background-color: #EEEEEE; text-align: center;">
      	<#if resource.publishToZyk == true >
      		已推送
      	<#else>
      		<a href="#" onclick="publishtozyk(${resource.resourceId});">资源库</a>
      	</#if>	
      </td>
      </#if>	
      <td style="background-color:#EEEEEE; text-align: center;">
      <#if resource.auditState != 0 >
        <a href='?cmd=audit&amp;resourceId=${resource.resourceId}'>通过审核</a>
      </#if>
        <a href='?cmd=edit&amp;resourceId=${resource.resourceId}'>修改</a>&nbsp;&nbsp;
        <a href='#' onclick="confirmDel(${resource.resourceId});">删除</a>
      </td>
    </tr>
    <tr>
      <td>${resource.resourceId}</td>
      <td colspan='4' style='padding-left:12px;'>
       <div>标签: [ <#list Util.tagToList(resource.tags!) as t>${t!?html}${t_has_next?string(', ','')}</#list>],  
           文件名: <a href='${SiteUrl}${resource.href!}' target='_blank'>${Util.fileName(resource.href)}</a></div>
       <div>上传IP: ${resource.addIp!'未知'}</div>
      </td>
      <td colspan='3'>
        下载: ${resource.downloadCount!}, 评论: ${resource.commentCount!}
        <br/>
        <#if resource.auditState==1><font color='red'>待审核</font></#if>
        <#if platformType == "2">
        <#if resource.pushState == 1>已推送</#if>
        <#if resource.pushState == 2><font color='red'>待推送</font></#if>
        </#if>
      </td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>

<div class='funcButton'>
  <input type='button'  class='button' value=' 全 选 ' onclick='select_all();' ondblclick='select_all();' />
<#if platformType == "2">
<#if topsite_url??>
  <input type='button'  class='button' value=' 推  送 ' onclick='do_cmd("push");' />
  <input type='button'  class='button' value='取消推送' onclick='do_cmd("unpush");' />
</#if>
</#if>
  <input type='button'  class='button' value=' 审核通过 ' onclick='audit_r();' />
<#if type == 'rcmd'>
  <input type='button'  class='button' value=' 取消推荐 ' onclick='unrcmd_r();' />
<#else>
  <input type='button'  class='button' value=' 推 荐 ' onclick='rcmd_r();' />
</#if>
  <input type='button' class='button' value=' 删 除 ' onclick='delete_r();' />
  <select onchange='submit_command(this.options[this.selectedIndex].value);'>
      <option value=''>请选择一个操作</option>
    <optgroup label="审核操作">
	    <option value='audit'>审核通过</option>
	    <option value='unaudit'>取消审核</option>
    </optgroup>
    <optgroup label='推荐操作'>
	    <option value='rcmd'>设置为推荐</option>
	    <option value='unrcmd'>取消推荐</option>
    </optgroup>
  </select>
  
<#if resource_categories??>
  <input type='button'  class='button' value='设置资源类型' onclick='move_cate_r();' />
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
<br/><br/>

</body>
</html>
