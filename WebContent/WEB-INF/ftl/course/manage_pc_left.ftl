<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
  <link href="../../css/manage/left.css" rel="stylesheet" type="text/css" />
 </head>
<body>
  <div class="panel">
    <div class="panelName"><a href="${SiteUrl}manage/" target='_top'>返回个人面板</a></div>
    <div id='Title_1' class="panelName_cur"><a href='#' onclick='return toggle(1);'>备课信息</a></div>
    <div id='Menu_1' class="panelItems" style='display:'>
      <ul>
        <#--<li><a href='manage_createPrepareCourse.py' target='main' onclick='ac(this)'>发起备课</a></li>-->
        <li><a href='manage_createPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>备课信息</a></li>       
        <li><a href='manage_prepareCourseContentType.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>备课共案类型</a></li>
        <li><a href='manage_relatedPrepareCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>相关集备</a></li>
        <li><a href='manage_createPrepareCourse_article.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>文章管理</a></li>
        <li><a href='manage_createPrepareCourse_resource.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>资源管理</a></li>
        <li><a href='manage_createPrepareCourse_video.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>视频管理</a></li>
        <li><a href='manage_createPrepareCourse_topic.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>讨论管理</a></li>
        <li><a href='manage_createPreCourse3.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>流程管理</a></li>
        <li><a href='manage_createPrepareCourse_action.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>活动管理</a></li>
        <li><a href='manage_createPrepareCourse_member.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>成员管理</a></li>
        <li><a href='manage_createPrepareCourse_ContentList.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>个案管理</a></li>
        <li><a href='manage_createPrepareCourse_reply.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>回复管理</a></li>
        <li><a href='manage_createPrepareCourse_commoncourse.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>共案编辑历史</a></li>
        <li><a href='manage_createPrepareCourse_private_comment.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>个案评论管理</a></li>
        <li><a href='manage_createPrepareCourse_common_comment.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>共案评论管理</a></li>
        <li><a href='manage_customskin.py?prepareCourseId=${prepareCourse.prepareCourseId}' target='main' onclick='ac(this)'>自定样式</a></li>
        <#if group?? >
        <li><a href='${SiteUrl}g/${group.groupName}' target='_top' onclick='ac(this)'>返回协作组</a></li>
        </#if>
      </ul>
    </div>    
  </div>
</div>
<script>
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
