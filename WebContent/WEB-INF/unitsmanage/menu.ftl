<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage/left.css" rel="stylesheet" type="text/css" />
</head>
<body>    
<div class="panel">
<#if unit.parentId!=0>
<#if unitAdmin == "1" || unitUserAdmin == "1">
    <div id='Title_1' class="panelName_cur"><a href='#' onclick='return toggle(1);'>机构网站系统管理</a></div>
    <div id='Menu_1' class="panelItems" style='display:'>    
      <ul>
      <#if unitAdmin == "1" >
        <li><a href="unit_info.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>机构信息管理</a></li>
        <#if UnitUrlPattern??>
        <li><a href="${UnitUrlPattern.replace('{unitName}',unit.unitName)}?preview=1" target='_blank' onclick='ac(this)'>机构网站布局管理</a></li>
        <#else>
        <li><a href="${SiteUrl}d/${unit.unitName}/?preview=1" target='_blank' onclick='ac(this)'>机构网站布局管理</a></li>
        </#if>        
        <li><a href="unit_tmpl.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>机构网站模板管理</a></li>
        <li><a href="unit_skin.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>机构网站样式管理</a></li>
        <li><a href="unit_sitenav.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>定制机构网站导航</a></li>
        <li><a href="unit_sys.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>系统模块管理</a></li>
        <li><a href="unit_custorm.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>自定义模块管理</a></li>
        <li><a href="unit_subject.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">机构学科管理</a></li>
        </#if>
        <li><a href="unit_user.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>用户管理</a> | <a href="unit_user_deleted.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>待删除</a></li>
        <#if unitAdmin == "1" >
        <li><a href="add_news.py?type=0&amp;unitId=${unit.unitId}" target='main' onclick='ac(this)'>发布新闻、公告、动态</a></li>
        <li><a href="unit_news.py?type=1&amp;unitId=${unit.unitId}" target='main' onclick='ac(this)'>新闻、公告、动态管理</a></li>
        <li><a href="unit_links.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">友情链接管理</a></li>
        <li><a href="${SiteUrl}manage/admin_stat.action?cmd=init&from=unit&unitId=${unit.unitId}" target="main" onClick="ac(this)">自定义个人统计</a></li>
        <li><a href="${SiteUrl}manage/admin_stat_unit.action?cmd=init&from=unit&unitId=${unit.unitId}" target="main" onClick="ac(this)">自定义机构统计</a></li>
        <li><a href="reStatUnitIndex.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">更新机构首页统计</a></li>
        <li><a href="gen_index_html.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">重新生成单位首页</a></li>
        <#if bklist??>
        <li><a href="count_history_article_unit.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">重新统计历史文章</a></li>
        </#if>
        <#--
        <li><a href="clear_sitenav_cache.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>清空导航缓存数据</a></li>
        -->
        </#if>
       </ul>
   </div>
</#if>
</#if>
<#if unitContentAdmin == "1">
   <div id='Title_2' class="panelName"><a href='#' onclick='return toggle(2);'>机构网站内容管理</a></div>
    <div id='Menu_2' class="panelItems" style='display:block'>
      <ul>
        <li><a href="unit_owner_article.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>本机构文章管理</a></li>
        <#if unit.hasChild>
        <li><a href="unit_push_article.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>下级推送文章管理</a></li>
        </#if>
        <li><a href="unit_owner_resource.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>本机构资源管理</a></li>
        <#if unit.hasChild>
        <li><a href="unit_push_resource.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>下级推送资源管理</a></li>
        </#if>
        <li><a href="unit_comment.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">评论管理</a></li>
        <#if unit.parentId!=0>
        <#--
        <li><a href="unit_resource.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>资源管理</a></li>
        -->
        <li><a href="unit_image.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">图片管理</a></li>        
        <li><a href="unit_video.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">视频管理</a></li>
        <li><a href="contentspace_list.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>自定义分类管理</a></li>
        <li><a href="unit_contentspace_article_list.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>自定义分类文章管理</a></li>
        <li><a href="${SiteUrl}mod/vote/manage_list.action?guid=${unit.unitGuid}&type=unit&show=nohead" target="main" onClick="ac(this)">调查投票管理</a></li>
        <li><a href="clear_cache.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">清空机构页面缓存</a></li>
        <li><a href="gen_index_html.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">重新生成单位首页</a></li>
        </#if>
        <#if bklist??>
        <hr/>
        <#list bklist as bakyear>        
        <li><a href="admin_history_article_unit.py?unitId=${unit.unitId}&backYear=${bakyear.backYear}" target="main" onClick="ac(this)">管理${bakyear.backYear}年历史文章</a></li>
        </#list>
        </#if>
      </ul>
    </div>
</#if>

<#if unit.hasChild || (unitAdmin == "1" && canAddChildUnit??) >
<div id='Title_3' class="panelName"><a href='#' onclick='return toggle(3);'>下级机构</a></div>
<div id='Menu_3' class="panelItems" style='display:none'>
  <ul>
  <#if unitAdmin == "1" && canAddChildUnit??>
    <li><a href="unit_child.py?unitId=${unit.unitId}" target="main" onClick="ac(this)">下级机构管理</a></li>
  </#if>
  <#if unit.hasChild>
	  <#if unitAdmin == "1" || unitUserAdmin == "1">
	    <li><a href="unit_down_user.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>下级全部用户管理</a></li>
	  </#if>
	  <#if unitAdmin == "1" || unitContentAdmin == "1">
	    <li><a href="unit_down_article.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>下级全部文章管理</a></li>
	    <li><a href="unit_down_resource.py?unitId=${unit.unitId}" target='main' onclick='ac(this)'>下级全部资源管理</a></li>
	  </#if>
  </#if>
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