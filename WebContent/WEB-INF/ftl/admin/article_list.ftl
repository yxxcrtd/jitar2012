<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>文章管理</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />
  <script type="text/javascript" src='js/admin_article.js'></script>
  <script type="text/javascript">
  	function confirmDel(id,nickname,title)
  	{
  		if (!confirm("您确定要删除 "+nickname+" 的文章 "+ title +"?"))
  			return;
		var toolbarSty = "dialogwidth=240px;dialogheight=160px;scrolling:no;border=no;status=no;help=no";
		var url = "${SiteUrl}PunishScoreConfirm.py?seltype=score.article.adminDel";
		var res = window.showModalDialog(url,null,toolbarSty);
		if(res==undefined){
			res="";
			return;
		}
		var arr=res.split("|");
  		self.document.location.href="admin_article.py?ppp=${pager.currentPage}&cmd=delete&articleId="+id+"&score="+arr[0]+"&reason="+encodeURI(arr[1]);
  	}
  </script>
</head>
<body>
<#include 'admin_header.ftl' >
<#if !(type??)><#assign type=''></#if>
<#if type == 'best'>
  <#assign typeTitle = '精华文章管理' >
<#elseif type == 'rcmd'>
  <#assign typeTitle ='推荐文章管理' >
<#elseif type == 'unaudit'>
  <#assign typeTitle ='待审核文章管理' >
<#else>
  <#assign typeTitle ='文章管理' >
</#if>
<h2>${typeTitle!}</h2>
 
<div class='pager'>
<form name='searchForm' action='?' method='get'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value='${type!?html}' />
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
        <#--<#if grade.isGrade>-->
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
      <th width="6%">ID</th>
      <th width="25%">标题</th>
      <th width="120">作者</th>
      <th width="140">学科 / 学段</th>
      <th width="140">所属分类</th>
      <th width="10%">状态</th>
      <th width="80">操作</th>
    </tr>
  </thead>
  <tbody>
  <#if article_list?size == 0>
    <tr>
      <td colspan='7' style='padding:12px' align='center' valign='center'>没有找到符合条件的文章.</td>
    </tr>
  </#if>
  
	<#list article_list as article>
		<tr>
      		<td style='background-color:#EEEEEE'>
      			<input type='checkbox' name='articleId' value='${article.articleId}' /></td>
      <td style='background-color:#EEEEEE'>
      <#if article.systemCategory?? >[<a href='?cmd=list&amp;sc=${article.sysCateId}'>${article.systemCategory.name!}</a>]</#if>
        <#if article.typeState == false>[原创]<#else>[转载]</#if>
        <a href='${SiteUrl}showArticle.action?articleId=${article.articleId}' target='_blank'>${article.title!?html}</a>
        <#-- <#if article.bestState!false><img src='images/ico_best.gif' border='0' align='absmiddle' hspace='2' 
        /></#if>--><#if article.recommendState!false><img src='images/ico_rcmd.gif' border='0' align='absmiddle' /></#if>
      </td>
      <td style='background-color:#EEEEEE; text-align: center;'>
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
      <td style='background-color:#EEEEEE; text-align: center;'>
        ${article.sysCateName!?html}
      </td>
      <td style='background-color:#EEEEEE; text-align: center;'>
      
      <#if article.auditState == 0><font color='green'>已审核</font>
      <#elseif article.auditState == 1><font color='red'>待审核</font>
      <#else><font color='red'>未知</font>
      </#if>
      <#if article.hideState != 0><font color='gray'>隐藏</font></#if>
      <#if platformType == "2">
      <#if article.pushState == 1>已推送</#if>
      <#if article.pushState == 2><font color='red'>待推送</font></#if>
      </#if>
      </td>
      <td style='background-color:#eeeeee'>
        <a href='?cmd=edit&amp;articleId=${article.articleId}'>修改</a>
        <a href='#' onclick="confirmDel('${article.articleId}','${article.nickName!?js_string}','${Util.toUnicode(article.title!)}');">删除</a>
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
<#if platformType == "2">
<#if topsite_url??>
  <input type='button' class='button' value=' 推  送 ' onclick='push();' />
  <input type='button' class='button' value='取消推送' onclick='unpush();' />
</#if>
</#if>
<#if type == 'best'>
  <input type='button' class='button' value=' 取消精华 ' onclick='unbest_a();' />
<#elseif type == 'rcmd'>
  <input type='button' class='button' value=' 取消推荐 ' onclick='unrcmd_a();' />
</#if>
<#if type != 'invalid'>
  <input type='button' class='button' value=' 审  核 ' onclick='audit_a();' />
</#if>
  <input type='button' class='button' value=' 删 除 ' onclick='delete_a();' />
  
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

<#if article_categories??>
  <input type='button' class='button' value=' 移动到&gt;&gt; ' onclick='move_cate_a();' />
  <select name='sysCateId'>
    <option>选择目标文章分类</option>
    <option value=''>设置为空文章分类</option>
  <#list article_categories.all as c >
    <option value='${c.categoryId}'>${c.treeFlag2} ${c.name!?html}</option>
  </#list>
  </select>
</#if>
</div>

</form>
<br/><br/>
<div>
</div>
<br/><br/>

<#include 'admin_footer.ftl' >
</body>
</html>
