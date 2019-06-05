<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${user.blogName!?html}</title>
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
    <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'skin1'}/skin.css" />
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
    <#include '../user_script.ftl' >
    <script src='${SiteUrl}js/jitar/core.js'></script>
    <script src='${SiteUrl}js/jitar/lang.js'></script>
    <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
    <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
  </head> 
  <body>
  <#-- 无功能按钮的子页面 -->
  <#include ('childpage.ftl') >
  <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
  <div id='header'>
    <div id='blog_name'><span>${user.blogName!?html}</span></div>
  </div>
  <#include ('../../layout/layout_2.ftl') >
  <div id='placerholder1' title='我参与的活动' style='display:none;padding:1px;'>
        <#if action_list??>
         <table border='0' width='100%' cellspacing='1' class='table1'>
         <thead>
         <tr>
         <td style='width:100%'>活动标题</td>
         <td><nobr>总人数</nobr></td>
         <td>类型</td>
         <td>创建时间</td>
         <td>活动开始时间</td>
         </tr>
         </thead>
         <tbody>
         <#list action_list as a>
         <tr>
         <td><a href='${SiteUrl}showAction.action?actionId=${a.actionId}'>${a.title!?html}</a></td>
        <td><nobr><#if a.userLimit == 0 >不限<#else>${a.userLimit}</#if></nobr></td>
         <td><nobr><span><#if a.ownerType == 'user' >个人<#elseif a.ownerType == 'group' >群组<#elseif a.ownerType == 'preparecourse' >集备<#elseif a.ownerType == 'subject' >学科<#else>未知</#if>活动</span></nobr></td>
         <td><nobr>${a.createDate}</nobr></td>
         <td><nobr>${a.startDateTime}</nobr></td>
         </tr>
         </#list>
         </tbody>
         </table>
        </#if>    
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
<script>
App.start();
</script>
<div id="subMenuDiv"></div>
<script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
<#-- 原来这里是 include loginui.ftl  -->
<script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script> 
  </body>
</html>





 