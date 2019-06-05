<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="../css/manage/left.css" rel="stylesheet" type="text/css" />
  <script>
  if (window.top == window) {
    // 如果自己在顶部窗口打开, 显示是返回链接哪里不正确, 此时去打开 'index.html'.
    window.top.location = 'index.html';
  }
  try {
    // 别在 'main' 里面打开, 那样很难看.
    if (window.top != window) {
      if (window.top.frames['main'] == window)
        window.top.location = 'index.html';
    }
  } catch(e) {
  }
  </script>
</head>
<body>      
  <div class="panel">
<div id='Title_1' class="panelName_cur"><a href='#' onclick='return toggle(1);'>常用</a></div>
<div id='Menu_1' class="panelItems" style='display:'>
  <ul>
    <li><a href="article.action?cmd=input" target='main' onclick='ac(this)'>发表文章</a></li>
    <li><a href="article.action?cmd=list" target='main' onclick='ac(this)'>文章管理</a></li>
    <li><a href="resource.action?cmd=upload" target='main' onclick='ac(this)'>上传资源</a></li>
    <#--
    <li><a href="resource.action?cmd=bk&typeid=1" target='main' onclick='ac(this)'>备课(导学案)</a></li>
    <li><a href="resource.action?cmd=bk&typeid=2" target='main' onclick='ac(this)'>备课(教案)</a></li>
    -->
    <li><a href="resource.action?cmd=list" target='main' onclick='ac(this)'>资源管理</a></li>
    <li><a href="video.action?cmd=upload" target="main" onClick="ac(this)">上传视频</a></li>
    <li><a href="video.action?cmd=list" target="main" onClick="ac(this)">视频管理</a></li>
    <li><a href="photo.action?cmd=upload" target='main' onclick='ac(this)'>发布照片</a></li>
    <li><a href="message.action?cmd=inbox" target='main' onclick='ac(this)'>我的短消息</a></li>
    <li><a href="${SiteUrl}Favorite.action?cmd=list" target='main' onclick='ac(this)'>我的收藏夹</a></li>
    <li><a href="user.action?cmd=profile" target='main' onclick='ac(this)'>修改个人信息</a></li>
    <li><a href="user.action?cmd=subjectgrade" target='main' onclick='ac(this)'>修改学段/学科信息</a></li>
    <li><a href="userPlacard.py?cmd=list" target='main' onclick='ac(this)'>个人公告</a></li>
    <li><a href="usefulltools.py" target='main' onclick='ac(this)'>实用教学工具下载</a></li>
    <li><a href="${SiteUrl}help.action" target='_blank' onclick='ac(this)'>网站使用帮助</a></li>
  <#if canManage=="true" >
    <li><a href="admin.py" target='_top'>系统后台管理</a></li>
  </#if>
  </ul>
</div>

<#if aclist?? && (aclist?size>0)>
<div id='Title_111' class="panelName"><a href='#' onclick='return toggle(111);'>特定栏目管理</a></div>
<div id='Menu_111' class="panelItems" style='display:none'>
  <ul>  
  <#list aclist as ac>
  <li><a href="column_news_list.py?cmd=list&columnId=${ac.objectId}" target='main' onclick='ac(this)'>${ac.objectTitle}</a></li>
  </#list>
  <hr/>
  <li><a href="clearall_cache.py?cachetype=index" target='main' onclick='ac(this)'>清空页面缓存</a></li>
  </ul>
</div>
</#if>
  
<div id='Title_11' class="panelName"><a href='#' onclick='return toggle(11);'>个人空间管理</a></div>
<div id='Menu_11' class="panelItems" style='display:none'>
  <ul>
    <#--<li><a href="${SiteUrl}${loginUser.loginName}" target='_blank'>个人空间首页管理</a></li>-->
    <li><a href="user_widget_delete.py" target='main' onclick='ac(this)'>删除个人空间模块</a></li>
    <li><a href="user_customskin.py" target='main' onclick='ac(this)'>自定义空间页面样式</a></li>
    <li><a href="user_clear_cache.py" target='main' onclick='ac(this)'>清空个人空间缓存</a></li>
    <#--<li><a href="user_html.py" target='main' onclick='ac(this)'>重新生成静态页面</a></li>-->
  </ul>
</div>

<div id='Title_2' class="panelName"><a href='#' onclick='return toggle(2);'>文章管理</a></div>
<div id='Menu_2' class="panelItems" style='display:none'>
  <ul>
    <li><a href="article.action?cmd=input" target='main'>发表文章</a></li>
    <li><a href="article.action?cmd=list" target='main'>文章管理</a></li>
    <li><a href="usercate.action?cmd=list" target='main'>个人文章分类</a></li>
    <li><a href="article.action?cmd=comment_list" target='main'>我得到的评论</a></li>
    <li><a href="article.action?cmd=comment_list_my" target='main'>我发表的评论</a></li>
  </ul>
</div>

<div id='Title_3' class="panelName"><a href='#' onclick='return toggle(3);'>资源管理</a></div>
<div id='Menu_3' class="panelItems" style='display:none'>
  <ul>
    <li><a href="resource.action?cmd=upload" target='main'>上传资源</a></li>
    <li><a href="resource.action?cmd=list" target='main'>资源管理</a></li>
    <li><a href="rescate.action?cmd=list" target='main'>个人资源分类</a></li>
    <li><a href="resource.action?cmd=comment_list" target='main'>我得到的评论</a></li>
    <li><a href="resource.action?cmd=comment_list_my" target='main'>我发表的评论</a></li>
  </ul>
</div>

<div id="Title_0" class="panelName">
	<a href="#" onClick="return toggle(0);">视频管理</a>
</div>
<div id="Menu_0" class="panelItems" style="display:none">
	<ul>
		<li><a href="video.action?cmd=upload" target="main">上传视频</a></li>
		<li><a href="video.action?cmd=list" target="main">视频管理</a></li>
		<li><a href="videocate.action?cmd=list" target='main'>个人视频分类</a></li>
		<li><a href="video.action?cmd=comment_list" target="main">我得到的评论</a></li>
		<li><a href="video.action?cmd=comment_list_my" target="main">我发表的评论</a></li>
	</ul>
</div>

<div id='Title_4' class="panelName"><a href='#' onclick='return toggle(4);'>协作组管理</a></div>
<div id='Menu_4' class="panelItems" style='display:none'>
  <ul>
    <li><a href="group.action?cmd=create" target='main'>创建协作组</a></li>
    <li><a href="group.py?cmd=list" target='main'>我创建的协作组</a></li>
    <hr/>
    <li><a href="group.py?cmd=list_join" target='main'>我加入的协作组</a></li>
    <li><a href="group.py?cmd=my_invite" target='main'>我发出的邀请</a></li>
    <li><a href="group.py?cmd=invite_me" target='main'>我收到的邀请</a></li>
    <li><a href="group.py?cmd=my_joinreq" target='main'>我发出的申请</a></li>
    <li><a href="group.py?cmd=req_me" target='main'>我收到的申请</a></li>
  </ul>
</div>


<div id='Title_5' class="panelName"><a href='#' onclick='return toggle(5);'>活动管理</a></div>
<div id='Menu_5' class="panelItems" style='display:none'>
  <ul>
    <li><a href="action/actionlist.py?type=owner" target='main'>我创建的活动</a></li>
    <li><a href="action/myaction.py" target='main'>我参与的活动</a></li>
  </ul>
</div>

<div id='Title_6' class="panelName"><a href='#' onclick='return toggle(6);'>集备管理</a></div>
<div id='Menu_6' class="panelItems" style='display:none'>
  <ul>
  <#--
    <li><a href="course/createPreCourse.py" target='_blank'>发起备课</a></li>
    -->
    <li><a href="course/mycourse.py" target='main'>我发起的备课</a></li>
    <li><a href="course/myjoinedcourse.py" target='main'>我参与的备课</a></li>
    <li><a href="course/private_course_comment.py" target='main'>评论管理</a></li>
  </ul>
</div>

<div id='Title_7' class="panelName"><a href='#' onclick='return toggle(7);'>消息留言</a></div>
<div id='Menu_7' class="panelItems" style='display:none'>
  <ul>
    <li><a href="message.py?cmd=write" target='main'>写短消息</a></li>
    <li><a href="message.py?cmd=inbox" target='main'>短消息收件箱</a></li>
    <li><a href="message.py?cmd=outbox" target='main'>短消息发件箱</a></li>
    <li><a href="message.py?cmd=trash" target='main'>短消息回收站</a></li>
    <hr/>
    <li><a href="leaveword.action?cmd=list" target='main'>留言管理</a></li>
  </ul>
</div>

<div id='Title_9' class="panelName"><a href='#' onclick='return toggle(9);'>相册管理</a></div>
<div id='Menu_9' class="panelItems" style='display:none'>
  <ul>
    <li><a href="photo.action?cmd=upload" target='main'>上传照片</a></li>
    <li><a href="photo.action?cmd=list" target='main'>相册管理</a></li>
    <li><a href="photostaple.action?cmd=list" target='main'>相册分类</a></li>
    <li><a href="photo_comment.py" target='main'>相册评论</a></li>
  </ul>
</div>

<#if plugin_list??>
 <div id='Title_112' class="panelName"><a href='#' onclick='return toggle(112);'>插件对象管理</a></div>
 <div id='Menu_112' class="panelItems" style='display:none'>
 <ul>
 <#list plugin_list as plugin >
   <li><a href="${SiteUrl}mod/${plugin.pluginName}/my_created_object.py" target='main'>我的${plugin.pluginTitle}</a></li>
 </#list>
 </ul>
</div>
</#if>   

<div id='Title_10' class="panelName"><a href='#' onclick='return toggle(10);'>个人信息</a></div>
<div id='Menu_10' class="panelItems" style='display:none'>
  <ul>
     <#if passportURL=="">
         <li><a href="user.action?cmd=updpwd" target='main'>修改密码</a></li>
         <li><a href="user.action?cmd=question" target='main'>修改问题答案</a></li>
     </#if>
     <li><a href="user.action?cmd=stat" target='main'>更新统计</a></li>
     <li><a href="user.action?cmd=stat_historyarticle" target='main'>重新统计历史文章数</a></li>
  </ul>
</div>

<div id='Title_20121213' class="panelName"><a href='#' onclick='return toggle(20121213);'>好友</a></div>
<div id='Menu_20121213' class="panelItems" style='display:none'>
  <ul>
    <li><a href="friend.action?cmd=list" target='main'>我的好友</a></li>
    <li><a href="friend.action?cmd=list_black" target='main'>黑名单</a></li>
  </ul>
</div>

<div class="panelName"><a href="${SiteUrl}" target='_top'>返回首页</a></div>
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
