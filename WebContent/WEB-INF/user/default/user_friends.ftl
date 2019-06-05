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
    <script src="${SiteUrl}js/jitar/login.js"></script>
    <script src="${SiteUrl}js/jitar/tooltip.js"></script>
  </head> 
  <body>
  <#-- 无功能按钮的子页面 -->
  <#include ('childpage.ftl') >
  <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
  <div id='header'>
    <div id='blog_name'><span>${user.blogName!?html}</span></div>
  </div>
  <#include ('../../layout/layout_2.ftl') >
  <div id='placerholder1' title='我的好友' style='display:none'>
	  <#if friend_list?? >
	    <table  style='width:600px'>
	    <#list friend_list as u >
      <#if (u.trueName??)><#assign userName = u.trueName >
      <#else><#assign userName = u.loginName >
      </#if>
	    <tr>
	    <#if UserUrlPattern??>
	    <td style='width:36px'>
	        <a href="${UserUrlPattern.replace('{loginName}',u.loginName)}" onmouseover="ToolTip.showUserCard(event,'${u.loginName}','${userName}','${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}','${u.qq?html}');">
	        <img src="${SSOServerUrl +'upload/'+u.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'" width='32' height='32' border='0' alt="${u.trueName?html}" /></a>
	     </td>
	    <td>
	    <a href="${UserUrlPattern.replace('{loginName}',u.loginName)}">${u.trueName!?html}</a>
	    </td>
	    <#else>
	    <td style='width:36px'>
	        <a href="${SiteUrl}${u.loginName}" onmouseover="ToolTip.showUserCard(event,'${u.loginName}','${userName}','${SSOServerUrl +"upload/"+u.userIcon!"images/default.gif"}','${u.qq?html}');">
	        <img src="${SSOServerUrl +'upload/'+u.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif'" width='32' height='32' border='0' alt="${u.trueName?html}" /></a>
	     </td>
	    <td>
	    <a href="${SiteUrl}${u.loginName}">${u.trueName!?html}</a>
	    </td>
	    </#if>
	    
	    <td align='right'>加入时间：${u.addTime!?html}
	    </td>
	    </tr>
	    </#list>
	    </table>
	</#if>
	
	<div style='text-align:center;padding:6px 0'>
	<#include ('paper.ftl') >
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