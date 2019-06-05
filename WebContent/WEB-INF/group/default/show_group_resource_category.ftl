<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="expires" content="0" />
	<title>${group.groupTitle!?html}</title>
	<link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
	<link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
	<!-- 布局模板 -->
	<link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
	<!-- ToolTip -->
	<link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
	<script language='javascript'>
    var JITAR_ROOT = '${SiteUrl}';
    //var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。    
  <#if loginUser?? >
  	var visitor = { id: ${loginUser.userId}, name: '${loginUser.loginName!?js_string}', nickName: '${loginUser.nickName!?js_string}', role: '${visitor_role!"guest"}' };
  <#else>
  	var visitor = { id: null, name: null, nickName: null, role: 'guest' };
  </#if>
    var user = { };
    var group = {
      id: ${group.groupId}, name: '${group.groupName!?js_string}', title: '${group.groupTitle!?js_string}'
    };
    var page_ctxt = {
      owner: group,
      isSystemPage: <#if page.isSystemPage??>${page.isSystemPage?string('true', 'false')}<#else>false</#if>,
      pages: [{id: ${page.pageId}, title:'${page.title!?js_string}' }],
      widgets: [
        {id: "_group_rescate_1", page:${page.pageId}, column:2, title:'', module:'placeholder', ico:'', data:{} },
        <#list widget_list as widget>
          
          {id: ${widget.id}, page:${widget.pageId}, column:${widget.columnIndex}, 
            title:'${widget.title?js_string}', module:'${widget.module}', ico:'', 
            data:{ ${widget.data!} } }<#if widget_has_next>, </#if>
        </#list>
      ]
    };
	</script>
	<script src='${SiteUrl}js/jitar/core.js'></script>
	<script src='${SiteUrl}js/jitar/lang.js'></script>
	<script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
	<script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
	<script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
</head>
<body>
	<#include ('func.ftl') >
	<div id = 'progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
	<div id='header'>
		<div id='blog_name'><span>${group.groupTitle!?html}</span></div>
	</div>
	
  <div title="${category.name!?html}资源列表" id="_group_rescate_1" style='display:none;'>
	<#if resource_list??>
		<ul>
		<#list resource_list as r>
			<li style='margin: 5px 0 5px 0'><a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}' target='_blank'><img 
			  src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' hspace='2' 
			  />${r.title!?html}</a> [<a href='${SiteUrl}go.action?loginName=${r.loginName}' target='_blank'>${r.nickName}</a>] [${r.createDate?string('yyyy-MM-dd')}]</li>
		</#list>
		</ul>
		<#include '../../ftl/inc/pager.ftl'>
	</#if>
	</div>
	
	<#-- 调用页面指定的布局模式 -->
	<#include ('../../layout/layout_' + (page.layoutId!'1') + '.ftl') >
	
	<div id='page_footer'></div>
	<script>App.start();</script>
	<div id="subMenuDiv"></div>
	<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
	<#-- 原来这里是 include loginui.ftl  -->
	<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>
