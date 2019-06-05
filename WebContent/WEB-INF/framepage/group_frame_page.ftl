<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${group.groupTitle!?html}</title>
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/common/common.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/skin/${page.skin!'skin1'}/skin.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/layout/layout_2.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}css/tooltip/tooltip.css' />
<link rel='stylesheet' type='text/css' href='${SiteUrl}js/datepicker/calendar.css' />
<script language='javascript'>
var ContainerObject = {'type':'group','guid':'${group.groupGuid}'};
var JITAR_ROOT = '${SiteUrl}';
var USERMGR_ROOT = '';    
<#if loginUser?? >
var visitor = { id: ${loginUser.userId}, name: '${loginUser.loginName!?js_string}', nickName: '${loginUser.nickName!?js_string}', role: '${visitor_role!"guest"}' };
<#else>
var visitor = { id: null, name: null, nickName: null, role: 'guest' };
</#if>
var user = { };
var group = {id: ${group.groupId}, name: '${group.groupName!?js_string}', title: '${group.groupTitle!?js_string}'};
var page_ctxt = { owner: group, isSystemPage: true,
pages: [{id: ${page.pageId}, title:'${page.title!?js_string}' }],
widgets: [{'id': '1', 'pageId':0, 'column':1,'title':'协作组信息','module':'group_info', 'ico':'', 'data':''},
{'id': 'placerholder1', 'pageId':0, 'column':2,'title':'','module':'placeholder', 'ico':'', 'data':''}]
};
</script>
<script type="text/javascript" src='${SiteUrl}js/jitar/core.js'></script>
<script type='text/javascript' src='${SiteUrl}jython/get_plugin_module.py'></script>
<script type="text/javascript" src='${SiteUrl}js/jitar/lang.js'></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
<script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
<script type='text/javascript' src='${SiteUrl}js/jitar/pager.js'></script>
<script type='text/javascript' src='${SiteUrl}js/datepicker/calendar.js'></script>
</head>
<body>
<#include ('group_funcbar.ftl') >
<div id = 'progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
<div id='header'>
  <div id='blog_name'><span>${group.groupTitle!?html}</span></div>
</div>

<#include ('/WEB-INF/layout/layout_2.ftl') >
<div id='placerholder1' title='[placeholder_title]' style='display:none'>
[placeholder_content]
</div>
<div id='page_footer'></div>
<script type='text/javascript'>
App.start();
</script>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<#-- 原来这里是 include ('/WEB-INF/group/default/loginui.ftl')  -->
<script type='text/javascript' src='${SiteUrl}js/jitar/msgtip.js' type='text/javascript'></script>
</body>
</html>