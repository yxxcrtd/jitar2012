<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>文章回收站管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript" src='js/admin_article.js'></script>
</head>
<body>
<#include 'admin_header.ftl' >
<h2>文章回收站管理</h2>

<div class='pager'>
<form name='searchForm' action='?' method='get'>
  <input type='hidden' name='cmd' value='recycle_list' />
  关键字：<input type='text' name='k' value="${k!?html}" size='12' />
  <#if !(f??)><#assign f = 'title'></#if>
  <select name='f'>
    <option value='title' ${(f == 'title')?string('selected', '')}>标题标签</option>
    <option value='intro' ${(f == 'intro')?string('selected', '')}>文章简介</option>
    <option value='uname' ${(f == 'uname')?string('selected', '')}>发表用户</option>
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
       <#-- <#if grade.isGrade>-->
            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                <#if grade.isGrade>
                  ${grade.gradeName!?html}
                <#else>
                  &nbsp;&nbsp;${grade.gradeName!?html}
                </#if>
              </option>
       <#-- </#if>-->
        </#list>
</#if>
    </select>
<#if article_categories??>
  <#if !(sc??)><#assign sc = 0></#if>
  <select name='sc'>
    <option value=''>所属文章分类</option>
  <#list article_categories.all as c >
    <option value='${c.categoryId}' ${(sc == c.categoryId)?string('selected', '')}>${c.treeFlag2} ${c.name!?html}</option>
  </#list>
  </select>
</#if>
  <input type='submit' class='button' value='查找' />
</form>
</div>
<form name='articleForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='list' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='20'>ID</th>
      <th width='600'>标题</th>
      <th width='120'>作者</th>
      <th width='140'>学科 / 学段</th>
      <th width='140'>所属分类</th>
      <th width='80'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#if article_list?size == 0>
    <tr>
      <td colspan='6' style='padding:12px' align='center' valign='center'>没有找到符合条件的文章.</td>
    </tr>
  </#if>
  <#list article_list as article>
    <tr>
      <td style='background-color:#eeeeee'><input type='checkbox' name='articleId' value='${article.articleId}' /></td>
      <td style='background-color:#eeeeee'>
        <#if article.typeState == false>[原创]<#else>[转载]</#if>
        <a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
        <#if article.bestState!false><img src='images/ico_best.gif' border='0' align='absmiddle' hspace='2' 
        /></#if><#if article.recommendState!false><img src='images/ico_rcmd.gif' border='0' align='absmiddle' /></#if>
        <#if article.auditState != 0><font color='red'>未审核</font></#if>
      </td>
      <td style="background-color: #EEEEEE; text-align: center;">
        <a href='${SiteUrl}go.action?loginName=${article.loginName!}' target='_blank'>${article.nickName!?html}</a>
      </td>
      <td style="background-color: #EEEEEE; text-align: center;">
	   	<#if article.subjectId??>
			${Util.subjectById(article.subjectId).msubjName!?html}
		</#if>
		/
      	<#if article.gradeId??>
        	${Util.gradeById(article.gradeId).gradeName!?html}
      	</#if>
      </td>
      <td style="background-color: #EEEEEE; text-align: center;">
        ${article.sysCateName!?html}
      </td>
      <td style='background-color:#eeeeee'>
        <a href='?cmd=recover&amp;articleId=${article.articleId}'>恢复</a>
        <a href='?cmd=crash&amp;articleId=${article.articleId}' onclick="return confirm('您是否确定要彻底删除 ${article.nickName!?js_string} 的文章 \'${article.title!?js_string}\'??');">彻底删除</a>
      </td>
    </tr>
    <tr>
      <td>${article.articleId}</td>
      <td colspan='6' style='padding:6px;'>
        <div style='margin-bottom:4px;text-align:right;'>
           发表于: ${article.createDate!}, 最后修改: ${article.lastModified!}, IP: ${article.addIp!} 
        </div>
        <div style='color:#555555;padding-left:8px;margin-bottom:10px;'>
        ${Util.eraseHtml(article.articleContent!, 255)}
        </div>
      </td>
    </tr>
  </#list>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>

<div class='funcButton'>
  <input type='button' class='button' value=' 全 选 ' onclick='select_all();' ondblclick='select_all();' />
  <input type='button' class='button' value=' 恢   复 ' onclick='recover_a();' />
  <input type='button' class='button' value='彻底删除' onclick='crash_a();' />
</div>

</form>
<div>
</div>
<br/><br/>

<#include 'admin_footer.ftl' >
</body>
</html>
