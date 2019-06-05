<#assign grpName="协作组">
<#if isKtGroup??>
    <#if isKtGroup=="1">
        <#assign grpName="课题组"> 
    <#elseif isKtGroup=="2">
        <#assign grpName="备课组"> 
    <#else>
        <#assign grpName="小组">
    </#if>
</#if>

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
<#if isKtGroup=="1">
<div id='placerholder1' title='课题组参加者' style='display:none'>
<#else>    
<div id='placerholder1' title='${grpName}最新成员' style='display:none'>
</#if>
    <table width='100%' border='0'>
<#assign colCount=2>    
<#if ugm_list.size() % colCount == 0>
	<#assign rowCount=ugm_list.size() / colCount>
<#else>
	<#assign rowCount=(ugm_list.size() / colCount) +1>
</#if>
<#list 0..rowCount as iLoop>
  <tr>
    <#list 0..colCount-1 as i>
    <#if ugm_list[iLoop*colCount+i]??>
	<#assign ugm=ugm_list[iLoop*colCount+i]>
	  <#if (ugm.trueName??)><#assign userName = ugm.trueName >
	  <#elseif (ugm.nickName??)><#assign userName = ugm.nickName >
	  <#elseif (ugm.loginName??)><#assign userName = ugm.loginName >
	  </#if>
	  <#if isKtGroup=="1">
        <td width='80'>
            <div class="friendImg">
                <a href="${SiteUrl}go.action?loginName=${ugm.loginName}" onmouseover="ToolTip.showUserCard(event,'${ugm.loginName}','${userName}', '${SSOServerUrl +"upload/"+ugm.userIcon!'images/default.gif'}');">
                 <img class="friendIcon" src="${SSOServerUrl +'upload/'+ugm.userIcon!'images/default.gif'}" 
                   alt='${ugm.nickName!}' width='48' onerror="this.src='${SiteUrl}images/default.gif'" />
                </a> 
                <br/><a href='${SiteUrl}go.action?loginName=${ugm.loginName}' target='_blank'>${ugm.nickName!?html}</a>
                <br/>加入时间：<br />${ugm.joinDate?string("yyyy-MM-dd")}
            </div>
        </td>
        <td>
            <div class="friendInfo">
                 <div class="friend"><b>单位</b>：${ugm.teacherUnit!?html}</div>
                 <div class="friend"><b>专业职务</b>：${ugm.teacherZYZW!?html}</div>
                 <div class="friend"><b>学历</b>：${ugm.teacherXL!?html}</div>
                 <div class="friend"><b>学位</b>：${ugm.teacherXW!?html}</div>
                 <div class="friend"><b>研究专长</b>：${ugm.teacherYJZC!?html}</div>
            </div>
        </td>	  
	  <#else>
    	<td width='54'>
            <div class="friendImg">
                <a href="${SiteUrl}go.action?loginName=${ugm.loginName}" onmouseover="ToolTip.showUserCard(event,'${ugm.loginName}','${userName}', '${SSOServerUrl +"upload/"+ugm.userIcon!'images/default.gif'}');">
                 <img class="friendIcon" src="${SSOServerUrl +'upload/'+ugm.userIcon!'images/default.gif'}" 
                   alt='${ugm.nickName!}' width='48' onerror="this.src='${SiteUrl}images/default.gif'" />
                </a> 
            </div>
        </td>
        <td>
            <div class="friendInfo">
             <div class="friend"><a href='${SiteUrl}go.action?loginName=${ugm.loginName}' target='_blank'>${ugm.nickName!?html}</a></div>
             <div class="friend">加入时间：<br />${ugm.joinDate?string("yyyy-MM-dd")}</div>
            </div>
        </td>
     </#if>   
     <#else>
     	<td>&nbsp;</td>
     	<td>&nbsp;</td>   
     </#if>   
     </#list>   
    </tr>    
 </#list>   
</table>


<div style='text-align:center;padding:6px 0'>
<#-- 显示当前分页对象的分页 (需要pager变量) -->
共 ${pager.totalRows}&nbsp;&nbsp;${pager.itemUnit + pager.itemName}
 <#if pager.currentPage == 1>首页<#else><a href='${pager.firstPageUrl}'>首页</a></#if>
 <#if (pager.currentPage > 1)><a href='${pager.prevPageUrl}'>上一页</a><#else>上一页</#if>
 <#if (pager.currentPage < pager.totalPages)><a href='${pager.nextPageUrl}'>下一页</a><#else>下一页</#if>
 <#if (pager.currentPage != pager.totalPages) && (pager.totalPages != 0)><a href='${pager.lastPageUrl}'>尾页</a><#else>尾页</#if>
 页次：${pager.currentPage}/${pager.totalPages} 页  #{pager.pageSize} ${pager.itemUnit + pager.itemName}/页 
<#if (pager.totalPages > 0)>转到: <select name='page' size='1' onchange="javascript:window.location='${pager.urlPattern}'.replace('{page}', this.options[this.selectedIndex].value);">
 <#list 1..pager.totalPages as i><option value='${i}' <#if i == pager.currentPage>selected</#if> >${i}</option></#list>
</#if>
</select>
</div>
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