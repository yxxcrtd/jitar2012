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
      isSystemPage: true,
      pages: [{id: ${page.pageId}, title:'${page.title!?js_string}' }],
      widgets: [
        {id: "placerholder1", page:${page.pageId}, column:2, title:'', module:'placeholder', ico:'', data:{} },
        <#list widget_list as widget>          
          {id: '${widget.id}', page:${widget.pageId}, column:${widget.columnIndex}, 
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
    
	<div id='placerholder1' title='显示组内备课计划详细内容' style='display:none;padding:1px;'>
<#if prepareCoursePlan?? >
	<table style='width:100%'>
	<tr>
	<td class='fontbold' style='width:68px;'>计划标题：</td><td>${prepareCoursePlan.title?html}</td>
	</tr>
	<tr>
	<td class='fontbold'>开始时间：</td><td>${prepareCoursePlan.startDate?string('yyyy-MM-dd')}</td>
	</tr>
	<tr>
	<td class='fontbold'>结束时间：</td><td>${prepareCoursePlan.endDate?string('yyyy-MM-dd')}</td>
	</tr>
	<tr>
	<td class='fontbold'>参与人员：</td><td>组内所有成员</td>
	</tr>
	<tr>
	<td class='fontbold' style='vertical-align:top'>计划内容：</td><td>${prepareCoursePlan.planContent}</td>
	</tr>
	</table>
	<h3>当前计划下的所有集备</h3>
	<#if pc_list??>
	<table style='width:100%' cellspacing='2' border='0'>
	<tr>
	  <th>课题</th><th><nobr>主备人</nobr></th><th>开始时间</th><th>结束时间</th>
	</tr>
	<#list pc_list as pc>
	<#assign u = Util.userById(pc.leaderId)>
	 <tr>
	  <td style='width:100%' class='list_icon'><a href='${SiteUrl}p/${pc.prepareCourseId}/0/'>${pc.title?html}</a></td>
	  <td><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}'>${u.trueName}</a></nobr></td>
	  <td><nobr>${pc.startDate?string('yyyy/MM/dd')}</nobr></td>
	  <td><nobr>${pc.endDate?string('yyyy/MM/dd')}</nobr></td>
	</tr>
	</#list>
	</table>
	
	<#include ('/WEB-INF/user/default/paper.ftl')>
	
	<#else>
	当前备课计划下没有集备。
	</#if>
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