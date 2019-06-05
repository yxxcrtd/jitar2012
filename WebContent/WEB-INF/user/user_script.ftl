<script>
  var ContainerObject = {'type':'user','guid':'${user.userGuid}'};
  var JITAR_ROOT = '${SiteUrl}';  <#-- 站点根路径 -->
  //var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。;  <#-- 站点根路径 -->
  var BasePageUrl = window.location.pathname; //只得到文件名部分
<#if UserUrlPattern??>
  var HasDomain = "";
</#if>
<#if loginUser?? >
  var visitor = { 
  	id: ${loginUser.userId}, 
  	name: '${loginUser.loginName!?js_string}', 
  	nickName: '${loginUser.nickName!?js_string}', 
  	role: '${visitor_role!"guest"}' };
<#else>
  var visitor = { id: null, name: null, nickName: null, role: 'guest' };
</#if>
  var user = {  <#-- 用户信息 -->
    id: ${user.userId}, 
    name: '${user.loginName!?js_string}',
    nickName : '${user.nickName!?js_string}'
  };
  var page_ctxt = { <#-- 页面环境 -->
    isSystemPage: ${page.isSystemPage?string('true', 'false')},
    owner : user,
    pages : [{id: ${page.pageId}, title: '${user.blogName!?js_string}', layoutId: ${page.layoutId!0} }],
    widgets: [ 
  <#if widgets??>
   <#list widgets as widget>
    {id: '${widget.id}', page:${widget.pageId}, column:${widget.columnIndex}, 
      title:'${(widget.title)!?js_string}', module:'${widget.module}', ico:'', 
      data:{${(widget.data)!}}} 
    <#if widget_has_next>,</#if>
    </#list>
  </#if>
    ]
  };
</script>
<#-- 自定义界面 -->
<#if (customSkin??) >
<style type='text/css'>
<#if ((customSkin.logo.length() > 0) || (customSkin.logoheight.length() > 0)) >
#header { 
	<#if (customSkin.logo.length() > 0) >
<#if customSkin.logo?substring(0,6)=='/user/'>
background:url('${SiteUrl}${customSkin.logo?substring(1)}') repeat-x top center;
<#else>
background:url('${customSkin.logo!}') repeat-x top center;
</#if>
	</#if>
	<#if (customSkin.logoheight??) && (customSkin.logoheight.length() > 0) >
height:${customSkin.logoheight!}px;
	</#if>
}
</#if>
<#if (customSkin.bgcolor??) && (customSkin.bgcolor.length() > 0) >
html,body{ background:${customSkin.bgcolor!} }
</#if>
<#if (customSkin.titleleft??) && (customSkin.titleleft.length() > 0) >
#blog_name { padding-left:${customSkin.titleleft!}px; }
</#if>
<#if (customSkin.titletop??) && (customSkin.titletop.length() > 0) >
#blog_name { padding-top:${customSkin.titletop!}px; }
</#if>
<#if (customSkin.titledisplay??) && (customSkin.titledisplay.length() > 0) >
#blog_name {display:none;}
</#if>
</style>
</#if>
<link rel="icon" href="${SiteUrl}images/favicon.ico" />
<link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />