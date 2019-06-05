<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml'>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<title>${user.blogName!?html}</title>
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/common/common.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/skin/${page.skin!'skin1'}/skin.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/layout/layout_2.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/tooltip/tooltip.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}js/datepicker/calendar.css' />
<script type='text/javascript'>
//<![CDATA[
var ContainerObject = {'type':'user','guid':'${user.userGuid}'};
var JITAR_ROOT = '${SiteUrl}'; 
var USERMGR_ROOT = '';

<#if loginUser?? >
var visitor = { id: ${loginUser.userId}, name: '${loginUser.loginName!?js_string}', nickName: '${loginUser.nickName!?js_string}', role: '${visitor_role!"guest"}' };
<#else>
var visitor = { id: null, name: null, nickName: null, role: 'guest' };
</#if>
var user = {id: ${user.userId}, name: '${user.loginName!?js_string}', nickName : '${user.nickName!?js_string}'};
var page_ctxt = {
isSystemPage: ${page.isSystemPage?string('true', 'false')},
owner : user,
pages : [{id: ${page.pageId}, title: '${user.blogName!?js_string}', layoutId: ${page.layoutId!0} }],
widgets: [
    {'id': '1', 'pageId':0, 'column':1,'title':'个人档案','module':'profile', 'ico':'', 'data':'${user.userId}'},
    {'id': 'placerholder1', 'pageId':0, 'column':2,'title':'','module':'placeholder', 'ico':'', 'data':''}
]
};
//]]>
</script>

<#include ('/WEB-INF/framepage/user_customskin.ftl') >

<script type='text/javascript' src='${SiteUrl}js/jitar/lang.js'></script>
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
<script type='text/javascript' src='${SiteUrl}jython/get_plugin_module.py'></script>
<script type='text/javascript' src='${SiteUrl}js/jitar/tooltip.js'></script>
<script type='text/javascript' src='${SiteUrl}js/jitar/login.js'></script>
<script type='text/javascript' src='${SiteUrl}js/jitar/pager.js'></script>
<script type='text/javascript' src='${SiteUrl}js/datepicker/calendar.js'></script>
</head>
<body>
<#include ('/WEB-INF/framepage/user_funcbar.ftl') >

<div id='progressBar' style='border: 1px solid red; background: yellow none repeat scroll 0% 0%; position: absolute; width: 200px; left: 600px; top: 8px; display: none; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; float: right; z-index: 100;'>正在加载……</div>
<div id='header'>
  <div id='blog_name'><span>${user.blogName!?html}</span></div>
</div>

<#include ('/WEB-INF/layout/layout_2.ftl') >

<div id='placerholder1' title='[placeholder_title]' xstyle='display:none'>
 [placeholder_content]
</div>
 
<script type='text/javascript'>
App.start();
</script>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<#--include ('/WEB-INF/user/default/loginui.ftl') -->
<script type='text/javascript' src='${SiteUrl}js/jitar/msgtip.js' type='text/javascript'></script>
</body>
</html>