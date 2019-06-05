<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage/left.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <div class="panel">
<#if isSystemAdmin == "1"  || isUserAdmin == "1" >
    <div id='Title_1' class="panelName_cur"><a href='#' onclick='return toggle(1);'>学科网站系统管理</a></div>
    <div id='Menu_1' class="panelItems" style='display:'>
      <ul>
      <#if isSystemAdmin == "1">
      	<li><a href="subjectinfo.py?id=${subject.subjectId}" target="main" onClick="ac(this)">学科信息配置</a></li>
      	<#if SubjectUrlPattern??>
      	<li><a href="${SubjectUrlPattern.replace('{subjectCode}',subject.subjectCode)}?preview=1" target='_blank' onclick='ac(this)'>学科网站布局管理</a></li>
      	<#else>
        <li><a href="${SiteUrl}k/${subject.subjectCode}/?preview=1" target='_blank' onclick='ac(this)'>学科网站布局管理</a></li>
        </#if>
        <li><a href="tmpl.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>学科网站模板管理</a></li>
        <li><a href="theme.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>学科网站样式管理</a></li>
        <li><a href="contentspace_list.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>自定义分类管理</a></li>
        <li><a href="subject_contentspace_article_list.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>自定义分类文章管理</a></li>
        <li><a href="sysmodule.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>系统模块管理</a></li>              
        <li><a href="custormmodule.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>自定义模块管理</a></li>
        <li><a href="subject_sitenav.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>自定义网站导航</a></li>
        <!--
        <li><a href="clear_sitenav_cache.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>清空导航数据</a></li>
        -->
        </#if>
        <#if isUserAdmin == "1" || isSystemAdmin == "1">
        <li><a href="subjectuser.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>学科用户管理</a></li>
        <#if isSystemAdmin == "1">
        <li><a href="subjectadmin.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>学科管理员管理</a></li>
        </#if>
        </#if>
        <#if isSystemAdmin == "1">
        <li><a href="news.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>图片新闻、学科动态管理</a></li>
        <li><a href="placard.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>学科公告管理</a></li>
        <li><a href="subjectlinks.py?id=${subject.subjectId}" target="main" onClick="ac(this)">友情链接管理</a></li>
        <#if bklist??>
        <li><a href="count_history_article_subj.py?id=${subject.subjectId}" target="main" onClick="ac(this)">更新历史文章统计</a></li>
        </#if>
        </#if>
       </ul>
   </div>
</#if>
<#if isContentAdmin == "1" || isSystemAdmin == "1">
   <div id='Title_2' class="panelName"><a href='#' onclick='return toggle(2);'>学科网站内容管理</a></div>
    <div id='Menu_2' class="panelItems" style='display:<#if isAdmin == '0'>block<#else>none</#if>'>
      <ul>
        <li><a href="article.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>文章管理</a></li>
        <li><a href="resource.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>资源管理</a></li>
        <li><a href="video.py?id=${subject.subjectId}" target="main" onClick="ac(this)">视频管理</a></li>
        <li><a href="preparecourse.py?id=${subject.subjectId}" target="main" onClick="ac(this)">集备管理</a></li>
        <li><a href="${SiteUrl}mod/vote/manage_list.action?guid=${subject.subjectGuid}&type=subject&show=nohead" target="main" onClick="ac(this)">调查投票管理</a></li>
        <li><a href="${SiteUrl}mod/questionanswer/question_manage_list.action?guid=${subject.subjectGuid}&type=subject&show=nohead" target="main" onClick="ac(this)">提问与解答</a></li>
        <li><a href="${SiteUrl}mod/topic/admin_topic_list_by_object.py?guid=${subject.subjectGuid}&type=subject" target="main" onClick="ac(this)">话题讨论(论坛)</a></li>
        <li><a href="huodong.py?id=${subject.subjectId}" target="main" onClick="ac(this)">教研活动管理</a></li>
        <li><a href="clear_cache.py?id=${subject.subjectId}" target="main" onClick="ac(this)">清空本学科页面缓存</a></li>
        <#if bklist??>
        <hr/>
        <#list bklist as bakyear>
        <li><a href="admin_history_article_subject.py?id=${subject.subjectId}&backYear=${bakyear.backYear}" target="main" onClick="ac(this)">管理${bakyear.backYear}年历史文章</a></li>
        </#list>
        </#if>
       </ul>
    </div>
    
   <div id='Title_3' class="panelName"><a href='#' onclick='return toggle(3);'>学科网站专题管理</a></div>
    <div id='Menu_3' class="panelItems" style='display:none'>
      <ul>
        <li><a href="createspecialsubject.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>创建专题</a></li>
        <li><a href="managespecialsubject.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>管理专题</a></li>
        <li><a href="${SiteUrl}jython/showNewSpecialSubject.py?id=${subject.subjectId}" target='main' onclick='ac(this)'>候选专题</a></li>       
      </ul>
    </div>
</#if>
</div>
<script type='text/javascript'>
function toggle(num) {
  var elem = document.getElementById('Menu_' + num);
  if (elem != null)
    elem.style.display = (elem.style.display == 'none') ? '' : 'none';
    
  elem = document.getElementById('Title_' + num);
  if (elem != null)
    elem.className = (elem.className == 'panelName') ? 'panelName_cur' : 'panelName';
  return false;
}
function ac(a) {
  var lis = document.getElementsByTagName('li');
  for (var i = 0; i < lis.length; ++i) {
    if (lis[i].className != '') {
      lis[i].className = '';
      var children = lis[i].children;
      for (var j = 0; j < children.length; ++j)
        if (children[j].tagName.toUpperCase() == 'A')
          children[j].className = '';
    }
  }
  var li = a.parentNode;
  if (li)
    li.className = 'active';
  a.className = 'abold';
}
</script>

</body>
</html>