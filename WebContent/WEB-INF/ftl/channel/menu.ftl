<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage/left.css" rel="stylesheet" type="text/css" />
</head>
<body>
<#if admin_type == "admin">
	<div class="panel">
	    <div id='Title_1' class="panelName_cur"><a href="channel.action?cmd=list" target='main'>频道管理</a></div>

	<#if channel_list??>
	<#list channel_list as c>
	<div id='Title_${c_index+2}' class="panelName"><a href='#' onclick='return toggle(${c_index+2});'>${c.title!?html}</a></div>
	 <div id='Menu_${c_index+2}' class="panelItems" style='display:none'>
	 <ul>	 	
	 	<li><a href="channel.action?cmd=edit&channelId=${c.channelId}" target="main" onClick="ac(this)">模板管理</a></li>
	 	<li><a href="channel.action?cmd=skins&channelId=${c.channelId}" target="main" onClick="ac(this)">样式管理</a></li>
	 	<li><a href="channel.action?cmd=modulelist&channelId=${c.channelId}" target="main" onClick="ac(this)">模块管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=channel_article_${c.channelId}" target="main" onClick="ac(this)">文章分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=channel_resource_${c.channelId}" target="main" onClick="ac(this)">资源分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=channel_photo_${c.channelId}" target="main" onClick="ac(this)">图片分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=channel_video_${c.channelId}" target="main" onClick="ac(this)">视频分类管理</a></li>	 	
	 	<li><a href="channel.action?cmd=bulletins&channelId=${c.channelId}" target="main" onClick="ac(this)">公告管理</a></li>
	 	<li><a href="channel.action?cmd=navlist&channelId=${c.channelId}" target="main" onClick="ac(this)">频道导航</a></li>
	 	<li><a href="channeluser.action?channelId=${c.channelId}" target="main" onClick="ac(this)">用户管理</a></li>
	  	<li><a href="channelarticle.action?channelId=${c.channelId}" target="main" onClick="ac(this)">文章管理</a></li>
	  	<li><a href="channelresource.action?channelId=${c.channelId}" target="main" onClick="ac(this)">资源管理</a></li>
	  	<li><a href="channelphoto.action?channelId=${c.channelId}" target="main" onClick="ac(this)">图片管理</a></li>
	  	<li><a href="channelvideo.action?channelId=${c.channelId}" target="main" onClick="ac(this)">视频管理</a></li>	  
	  	<li><a href="channeluserstat.action?channelId=${c.channelId}" target="main" onClick="ac(this)">频道个人统计</a></li>
        <li><a href="channelunitstat.action?channelId=${c.channelId}" target="main" onClick="ac(this)">频道机构统计</a></li>
        <li><a href="${SiteUrl}channel/channel.action?channelId=${c.channelId}" target="_blank" onClick="ac(this)">浏览频道</a></li>
	  </ul>
	 </div>
	</#list>
	</#if>
<#elseif admin_type=="channeladmin">
  <#if channel_list??>
	<#list channel_list as c>	
	<div id='Title_${c_index+1}' class="panelName"><a href='#' onclick='return toggle(${c_index+1});'>${c.title!?html}</a></div>
	 <div id='Menu_${c_index+1}' class="panelItems" style='display:none'>
	 <ul>
	 <#-- 判断对该频道的管理权限是什么 -->
	<#assign x = Util.getAccessControlListByUserAndObject(loginUser.userId,11,c.channelId)>
	<#if x != "">		
	 	<li><a href="channel.action?cmd=edit&channelId=${c.channelId}" target="main" onClick="ac(this)">模板管理</a></li>
	 	<li><a href="channel.action?cmd=skins&channelId=${c.channelId}" target="main" onClick="ac(this)">样式管理</a></li>
	 	<li><a href="channel.action?cmd=modulelist&channelId=${c.channelId}" target="main" onClick="ac(this)">模块管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=famous_article_${c.channelId}" target="main" onClick="ac(this)">文章分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=famous_resource_${c.channelId}" target="main" onClick="ac(this)">资源分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=famous_photo_${c.channelId}" target="main" onClick="ac(this)">图片分类管理</a></li>
	 	<li><a href="channelcate.action?channelId=${c.channelId}&cmd=list&type=famous_video_${c.channelId}" target="main" onClick="ac(this)">视频分类管理</a></li>	 	
	 	<li><a href="channel.action?cmd=bulletins&channelId=${c.channelId}" target="main" onClick="ac(this)">公告管理</a></li>
	 	<li><a href="channel.action?cmd=navlist&channelId=${c.channelId}" target="main" onClick="ac(this)">频道导航</a></li>
	 	<li><a href="channeluser.action?channelId=${c.channelId}" target="main" onClick="ac(this)">用户管理</a></li>
	  	<li><a href="channelarticle.action?channelId=${c.channelId}" target="main" onClick="ac(this)">文章管理</a></li>
	  	<li><a href="channelresource.action?channelId=${c.channelId}" target="main" onClick="ac(this)">资源管理</a></li>
	  	<li><a href="channelphoto.action?channelId=${c.channelId}" target="main" onClick="ac(this)">图片管理</a></li>
	  	<li><a href="channelvideo.action?channelId=${c.channelId}" target="main" onClick="ac(this)">视频管理</a></li>	  
	  	<li><a href="channeluserstat.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">频道个人统计</a></li>
        <li><a href="channelunitstat.action?channelId=${channel.channelId}" target="main" onClick="ac(this)">频道机构统计</a></li>
        <li><a href="${SiteUrl}channel/channel.action?channelId=${channel.channelId}" target="_blank" onClick="ac(this)">浏览频道</a></li>	
	  <#else>
	  	<#assign x = Util.getAccessControlListByUserAndObject(loginUser.userId,12,c.channelId)>
	    <#if x != "">
	  	  <li><a href="channeluser.action?cmd=list&channelId=${c.channelId}" target="main" onClick="ac(this)">用户管理</a></li>
	  	</#if>	  	
	  	<#assign x = Util.getAccessControlListByUserAndObject(loginUser.userId,13,c.channelId)>
		<#if x != "">
		    <li><a href="channelarticle.action?channelId=${c.channelId}" target="main" onClick="ac(this)">文章管理</a></li>
		  	<li><a href="channelresource.action?channelId=${c.channelId}" target="main" onClick="ac(this)">资源管理</a></li>
		  	<li><a href="channelphoto.action?channelId=${c.channelId}" target="main" onClick="ac(this)">图片管理</a></li>
		  	<li><a href="channelvideo.action?channelId=${c.channelId}" target="main" onClick="ac(this)">视频管理</a></li>
		</#if>
	  </#if>
	  </ul>
	 </div>
	</#list>
	</#if>
  </div>
</#if>

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