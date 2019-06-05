<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <title>文章 <#include ('webtitle.ftl') ></title>
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}dtree.css" />  
  <script src='js/jitar/core.js'></script>
  <script type="text/javascript" src="js/jitar/dtree.js"></script>
 </head>
 <body>
<#include 'site_head.ftl'>
<div class='r_s'>
<!-- 文章搜索表单 -->
<form  method='get' name="profile_form" action="search_article.py">
<table align='center' border='0'>
<tr valign='top'>
<td>
  关键字：<input class='s_input' name='k' value="${k!?html}" />   
    选择学段：<select name='gradeId' onchange='grade_changed(this)'>
          <option value="">全部学段</option>
        <#if grade_list?? >
        <#--
        <#list grade_list as grade>
            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                <#if grade.isGrade>
                  ${grade.gradeName!?html}
                <#else>
                  &nbsp;&nbsp;${grade.gradeName!?html}
                </#if>
              </option>
        </#list>
        -->
        <#list grade_list as grade >
            <option value="${grade.gradeId}" ${(grade.gradeId == (gradeId!0))?string('selected', '') } >${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</option>
        </#list>
                 
        </#if>
          </select>
  选择学科：<select name='subjectId'>
            <option value=''>所有学科</option>
        <#if subject_list?? >
          <#list subject_list as subj >
            <#if subjectId?? >
            <#if subj.msubjId == subjectId >
            <option selected value='${subj.msubjId}'>${subj.msubjName}</option>
            <#else >
            <option value='${subj.msubjId}'>${subj.msubjName}</option>
            </#if>
            <#else >
            <option value='${subj.msubjId}'>${subj.msubjName}</option>
            </#if>
          </#list>
        </#if>
          </select>
            <span id='subject_loading' style='display:none'>
              <img src='images/loading.gif' align='absmiddle' hsapce='3' />正在加载学科信息...
            </span>
  选择分类：<select name='categoryId'>
            <option value=''>所有分类</option>
        <#if blog_cates?? >
          <#list blog_cates.all as c >
            <#if categoryId??>
            <#if c.categoryId==categoryId>
            <option selected value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            <#else>
            <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            </#if>
            <#else>
            <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            </#if>
          </#list>
        </#if>
          </select> 
   </td>
   <td>
  <input type='image' src='${SiteThemeUrl}b_s.gif' />
  </td>
  </tr>
  </table>
</form>
</div>
<div style='height:8px;font-size:0'></div>
<div id='main'>
  <div class='main_left'>
    <div class='res1'>
      <div class='res1_c'>
      
        <script type="text/javascript">
        d = new dTree("d");
        d.add(0,-1,"<b>文章分类</b>","articles.py?type=${type!}");
      <#if blog_cates??>
         <#list blog_cates.all as c>
         <#if c.parentId??>
          	d.add(${c.id},${c.parentId},"${c.name}","articles.py?type=${type}&categoryId=${c.id}");
          	
          <#else>
          		d.add(${c.id},0,"${c.name}","articles.py?type=${type}&categoryId=${c.id}");
          </#if>
          </#list>
       </#if>
        document.write(d);
        d.openAll();
      </script>
      </div>
    </div>
  </div>
  <div class='main_right'>
    <div class='tab_outer'>
    
      <div id="article_" class='tab2'>
      <#if !(type??)><#assign type="new" ></#if>
      <div class="${(type == 'rcmd')?string('cur','') }"><a href='articles.py?type=rcmd&categoryId=${categoryId!?default('')}'>编辑推荐</a></div>
      <div class="${(type == 'famous')?string('cur','') }"><a href='articles.py?type=famous&categoryId=${categoryId!?default('')}'>名师文章</a></div>
          <div class="${(type == 'new')?string('cur','')} "><a href='articles.py?type=new&categoryId=${categoryId!?default('')}'>最新发布</a></div>
          <div class="${(type == 'hot')?string('cur','') }"><a href='articles.py?type=hot&categoryId=${categoryId!?default('')}'>点击率</a></div>
          <div class="${(type == 'digg')?string('cur','') }"><a href='articles.py?type=digg&categoryId=${categoryId!?default('')}'>按顶排序</a></div>
          <div class="${(type == 'trample')?string('cur','') }"><a href='articles.py?type=trample&categoryId=${categoryId!?default('')}'>按踩排序</a></div>
          <div class="${(type == 'star')?string('cur','') }"><a href='articles.py?type=star&categoryId=${categoryId!?default('')}'>按星排序</a></div>
          <div class="${(type == 'cmt')?string('cur','') }"><a href='articles.py?type=cmt&categoryId=${categoryId!?default('')}'>评论最多</a></div>
      </div>
  
        <div class='tab_content' style='padding:10px;'>
          <div id="article_0"  style="display: block;">
            <!-- 编辑推荐 -->
            <table border="0" cellspacing='0' class='res_table'>
            <thead>
            <tr>
            <td class='td_left' style='padding-left:10px'>标题</td>
            <td class='td_middle' nowrap='nowrap'>作者</td>
            <td class='td_middle' nowrap='nowrap'>发表日期</td>
            <td class='td_middle' nowrap='nowrap'>点击率</td>
            <td class='td_middle' nowrap='nowrap'>顶数</td>
            <td class='td_middle' nowrap='nowrap'>踩数</td>
            <#if type == 'star' >
            <td class='td_middle' nowrap='nowrap'>星级数</td>
            </#if>
            <td class='td_middle' nowrap='nowrap'>评论数</td>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td colspan='5' style='padding:4px;'></td>
            </tr>
          <#list article_list! as a>
             <tr>
              <td style='padding-left:10px'>
              	<#if a.typeState == false>[原创]<#else>[转载]</#if>
                <a href='${SiteUrl}showArticle.action?articleId=${a.articleId}'>${Util.getCountedWords(a.title!?html,36)}</a>
              </td>
              <td nowrap='nowrap'><a href='${SiteUrl}go.action?loginName=${a.loginName}' target='_blank'>${a.nickName!?html}</a></td>
              <td nowrap='nowrap'>${a.createDate?string('yyyy-MM-dd') }</td>
              <td>${a.viewCount}</td>
              <td>${a.digg}</td>
              <td>${a.trample}</td>
              <#if type == 'star' >
              
              <td>${(a.starCount/a.commentCount)?int}</td>
              </#if>
              <td>${a.commentCount}</td>
              </tr>
             </#list>
            </tbody>
            </table>
            <div class='pgr'><#include 'inc/pager.ftl' ></div>          
        </div>
                           
      </div>    
  </div>  
</div>

<div style='clear:both;'></div>
<#include ('footer.ftl') >

<script>
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.profile_form.subjectId;
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = 'manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '所有学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}

function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  opt = document.createElement("OPTION");
  opt.value = val;
  opt.text = text;
  sel.options.add(opt);
}
</script>

</body>
</html>