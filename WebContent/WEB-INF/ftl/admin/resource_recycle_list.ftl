<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>资源回收站管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript" src='js/admin_resource.js'></script>
</head>

<body>
<h2>资源回收站管理</h2>

<div class='funcButton'>
您现在的位置：<a href='#'>系统管理</a> &gt;&gt;
  <span>资源回收站管理</span> 
</div>

<form action='?' method='get'>
<div class='pager'>
  <input type='hidden' name='cmd' value='recycle_list' />
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
      <#--  </#if>-->
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
  <input type='submit' class='button' value=' 查找 ' />
</div>
</form>
<form name='listForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='recycle_list' />  
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width="10%">ID</th>
      <th>资源标题</th>
      <th width='10%'>上传人</th>
      <th width='10%'>资源类型</th>
      <th width='10%'>学科 / 学段</th>
      <th width='10%'>上传日期</th>
      <th width='10%'><nobr>下载/评论</nobr></th>
      <th width='10%'><nobr>操作</nobr></th>
    </tr>
  </thead>
  <tbody>
  <#if resource_list?size == 0>
    <tr>
      <td colspan='8' style='padding:12px' align='center' valign='center'>没有找到符合条件的资源</td>
    </tr>
  </#if>
  <#list resource_list as resource>
    <tr>
      <td>
        <input type='checkbox' name='resourceId' value='${resource.resourceId}' />${resource.resourceId}
      </td>
      <td>
        <img src='${Util.iconImage(resource.href)}' border='0' align='absmiddle' />
        <a href='../showResource.action?resourceId=${resource.resourceId}&shareMode=${resource.shareMode}' target="_blank">${resource.title!?html}</a>
      </td>
      <td style="text-align: center;"><nobr><a href='${SiteUrl}go.action?loginName=${resource.loginName}' target='_blank'>${resource.nickName!?html}</a></nobr></td>
      <td style="text-align: center;"><nobr>${resource.sysCateName!?html}</nobr></td>
      <td style="text-align: center;">
	   	<#if resource.subjectId??>
			${Util.subjectById(resource.subjectId).msubjName!?html}
		</#if>
		/
      	<#if resource.gradeId??>
        	${Util.gradeById(resource.gradeId).gradeName!?html}
      	</#if>
		</td>
      <td style="text-align: center;">${resource.createDate?string('yyyy-MM-dd')}</td>
      <td style="text-align: center;">
      	${resource.downloadCount}
      	/
      	${resource.commentCount}
      	</td>
      <td style="text-align: center;">
        <nobr><a href='?cmd=recover&amp;resourceId=${resource.resourceId}'>恢复</a></nobr>
        <nobr><a href='?cmd=crash&amp;resourceId=${resource.resourceId}' onclick='return confirm_crash();'>删除</a></nobr>
      </td>
    </tr>
  </#list>
  </tbody>
</table>
<div class='pager'>
  <#include '../inc/pager.ftl'>
</div>
<div class='funcButton'>
  <input type='button'  class='button'  value=' 全 选 ' onclick='select_all()' />
  <input type='button'  class='button' value=' 恢 复 ' onclick='recover_r()' />
  <input type='button'  class='button' value=' 彻底删除 ' onclick='crash_r()' />
</div>
</form>

</body>
</html>
