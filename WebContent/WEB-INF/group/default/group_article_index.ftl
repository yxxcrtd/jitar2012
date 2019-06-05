<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${article.title!}</title>
     <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
   <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/skin1/skin.css" />
   <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
   <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />   
    <script>
        var JITAR_ROOT = '${SiteUrl}';  <#--站点根路径-->
        //var USERMGR_ROOT = '{UserMgrClientUrl}'; 此处变量应该可以不用了，不用再改了。
        var article = {
          id: ${article.id},
          title: '${article.title!?js_string}'
        };
        var user = {
          id: 1, 
          name: 'admin'
        };
        var page_ctxt = {
          owner: user,
          pages : [ {id: 2, title: 'admin 的首页'} ],
          widgets: [
        <#list widget_list as widget>
          {id: ${widget.id}, page:${widget.pageId}, column:${widget.columnIndex}, 
            title:'${widget.title?js_string}', module:'${widget.module}', ico:'', 
            data:{} } 
          <#if widget_has_next>,</#if>
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
  <div id='toolbar'>
    <div class='innertoolbar'>
    <a href='${SiteUrl}'>首页</a> | 
    <a href='${SiteUrl}register.action'>注册帐户</a> | 
    <a href='${SiteUrl}login.jsp' donclick='LoginUI.showLogin();return false;'>通行证登录</a></div>
    <div id='innerToolbar'></div>
  </div>
  <#-- put article content in page, which is used by '文章内容' widget -->
  <div id='_article_content_preload' style='display:none;'>
     ${article.articleContent!}
  </div>
  <div id = 'progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
  <div id='header'></div>
  <#include ('../../layout/layout_2.ftl')>
    <script>
      App.start();
    </script>
    
  <div id="subMenuDiv"></div>
  <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
  <#-- 原来这里是 include loginui.ftl  -->
  <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>    
  </body>
</html>
