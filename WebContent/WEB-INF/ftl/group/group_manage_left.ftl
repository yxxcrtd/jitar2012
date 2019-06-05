<#if isKtGroup=="1">
    <#assign grpName="课题组"> 
    <#assign grpShowName="课题">
<#elseif isKtGroup=="2">
    <#assign grpName="备课组"> 
    <#assign grpShowName="小组">
<#else>
    <#assign grpName="协作组">
    <#assign grpShowName="小组">
</#if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>${grpName}管理</title>
  <link href="../css/manage/left.css" rel="stylesheet" type="text/css" />
 </head>
 <body>
<#assign can_manage = (group_member?? && group_member.status == 0 && group_member.groupRole >= 800) >
<div class="panel">
    <div class="panelName"><a href="${SiteUrl}manage/" target='_top'>返回个人面板</a></div>
    <div id='Title_1' class="panelName_cur"><a href='#' onclick='return toggle(1);'>${grpName}信息</a></div>
    <div id='Menu_1' class="panelItems" style='display:'>
      <ul>            
        <#if isKtGroup=="1">
        <li><a href='groupBbs.action?cmd=topic_list&amp;groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>课题研讨</a></li>
        <#else>
        <li><a href='groupBbs.action?cmd=topic_list&amp;groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>${grpShowName}论坛</a></li>
        </#if>    
        <li><a href='groupArticle.action?cmd=list&amp;groupId=${group.groupId}&returnPage=group' 
            target='main' onclick='ac(this)'>${grpShowName}文章</a></li>
        <li><a href='groupResource.action?cmd=list&amp;groupId=${group.groupId}&returnPage=group' 
            target='main' onclick='ac(this)'>${grpShowName}资源</a></li>
        <li><a href='groupPhoto.action?cmd=list&amp;groupId=${group.groupId}&returnPage=group' 
            target='main' onclick='ac(this)'>${grpShowName}图片</a></li>
        <li><a href='groupVideo.action?cmd=list&amp;groupId=${group.groupId}&returnPage=group' 
            target='main' onclick='ac(this)'>${grpShowName}视频</a></li>
        <li><a href='groupLeaveword.action?cmd=list&amp;groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>${grpShowName}留言</a></li>
        <li><a href='groupPlacard.action?cmd=list&amp;groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>${grpShowName}公告</a></li>
        <li><a href='groupMember.action?cmd=list&amp;groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>${grpShowName}成员</a></li>
        <li><a href='groupLink.action?cmd=listLink&amp;groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>小组链接</a></li>
        <#if isKtGroup=="2">
        <#if !(group_member.groupRole < 800)>
        <li><a href='course/group_course_plan_new_change.py?groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>制定集备计划</a></li>
        <li><a href='course/group_course_plan_manage.py?groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>管理集备计划</a></li>
        </#if>
        </#if>
      </ul>
    </div>  
    
  <#if can_manage >
    <div id='Title_2' class="panelName"><a href='#' onclick='return toggle(2);'>${grpName}管理</a></div>
    <div id='Menu_2' class="panelItems" style='display:none'>
      <ul>
      <#if isKtGroup=="1" && group.parentId==0>
        <li><a href='group_childs.py?cmd=list&parentId=${group.groupId}' 
            target='main' onclick='ac(this)'>子课题管理</a></li>      
      </#if>
      <#if isKtGroup=="1">
        <li><a href='group_ktusers.py?cmd=list&groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>课题负责人</a></li>      
      </#if>  
        <li><a href='group.action?cmd=edit_group&amp;groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>${grpName}信息</a></li>
        <li><a href='group_resource_category.py?cmd=list&groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>管理资源分类</a></li>
        <li><a href='group_article_category.py?cmd=list&groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>管理文章分类</a></li>
        <li><a href='group_photo_category.py?cmd=list&groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>管理图片分类</a></li>
        <li><a href='group_video_category.py?cmd=list&groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>管理视频分类</a></li>
        <li><a href='${SiteUrl}manage/group/group_data_query.py?groupId=${group.groupId}&guid=${guid}&cmd=init' 
            target='main' onclick='ac(this)'>统计查询</a></li>
        <li><a href='group.action?cmd=stat&groupId=${group.groupId}' 
            target='main' onclick='ac(this)'>更新统计</a></li>
      </ul>
    </div>
  </#if>    
  </div>
</div>
  
<script >
function toggle(num) {
  for (var i = 0; i < 3; ++i) {
    var elem = document.getElementById('Menu_' + i);
    if (elem != null) {
      elem.style.display = (i == num) ? '' : 'none'; 
    }
    elem = document.getElementById('Title_' + i);
    if (elem != null) {
      elem.className = (i == num) ? 'panelName_cur' : 'panelName';
    }
  }
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
  var li = a.parentElement;
  if (li)
    li.className = 'active';
  a.className = 'abold';
}
</script>
  
 </body>
</html>
