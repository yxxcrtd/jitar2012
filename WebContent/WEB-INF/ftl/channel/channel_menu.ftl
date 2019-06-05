<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage/left.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="panel">
  <div id='Title_1' class="panelName_cur"><a href="" target='main' onclick="return false;">频道管理</a></div>
  <div id='Menu_1' class="panelItems">
	 <ul>
	 <#if (AdminType?index_of("SystemSuperAdmin") > -1) || (AdminType?index_of("ChannelSystemAdmin") > -1)>
	 	<li><a href="channel.action?cmd=edit&channelId=${channel.channelId}" target="main" onClick="ac(this)">模板管理</a></li>
	 	<li><a href="channel.action?cmd=skins&channelId=${channel.channelId}" target="main" onClick="ac(this)">样式管理</a></li>
	 	<li><a href="channel.action?cmd=modulelist&channelId=${channel.channelId}" target="main" onClick="ac(this)">模块管理</a></li>
	 	<li><a href="channelcate.action?channelId=${channel.channelId}&cmd=list&type=channel_article_${channel.channelId}" target="main" onClick="ac(this)">文章分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${channel.channelId}&cmd=list&type=channel_resource_${channel.channelId}" target="main" onClick="ac(this)">资源分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${channel.channelId}&cmd=list&type=channel_photo_${channel.channelId}" target="main" onClick="ac(this)">图片分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${channel.channelId}&cmd=list&type=channel_video_${channel.channelId}" target="main" onClick="ac(this)">视频分类管理</a></li>	 	
	 	<li><a href="channel.action?cmd=bulletins&channelId=${channel.channelId}" target="main" onClick="ac(this)">公告管理</a></li>
	 	<li><a href="channel.action?cmd=navlist&channelId=${channel.channelId}" target="main" onClick="ac(this)">频道导航</a></li>
	 </#if>
	 <#if (AdminType?index_of("SystemSuperAdmin") > -1) || (AdminType?index_of("ChannelSystemAdmin") > -1) || (AdminType?index_of("SystemUserAdmin") > -1) || (AdminType?index_of("ChannelUserAdmin") > -1)>
	 	<li><a href="channeluser.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">用户管理</a></li>
	 </#if>
	 <#if (AdminType?index_of("SystemSuperAdmin") > -1) || (AdminType?index_of("ChannelSystemAdmin") > -1) || (AdminType?index_of("SystemContentAdmin") > -1) || (AdminType?index_of("ChannelContentAdmin") > -1)>
	  	<li><a href="channelarticle.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">文章管理</a></li>
	  	<li><a href="channelresource.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">资源管理</a></li>
	  	<li><a href="channelphoto.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">图片管理</a></li>
	  	<li><a href="channelvideo.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">视频管理</a></li>
	 </#if>
	 <li><a href="channeluserstat.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">频道个人统计</a></li>
	 <li><a href="channelunitstat.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">频道机构统计</a></li>
	 <li><a href="${SiteUrl}channel/channel.action?channelId=${channel.channelId}" target="_blank" onClick="ac(this)">浏览频道</a></li>
	 </ul>
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
  var li = a.parentElement;
  if (li)
    li.className = 'active';
  a.className = 'abold';
}
</script>
</body>
</html>