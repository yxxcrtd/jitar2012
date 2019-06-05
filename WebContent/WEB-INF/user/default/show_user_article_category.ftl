<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${user.blogName!?html} - ${category.name!?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'skin1'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/htmlpager.css" />
  <#include '../user_script.ftl' >
  <script type="text/javascript">
  var category = {id: ${category.categoryId}, name: '${category.name!?js_string}' };
  </script>
  <script type="text/javascript" src='${SiteUrl}js/jitar/core.js'></script>
  <script type="text/javascript" src='${SiteUrl}js/jitar/lang.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/htmlpager.js"></script>
</head> 
<body>

  <#-- 无功能按钮的子页面 -->
  <#include 'childpage.ftl' >
  <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
  <div id='header'>
	<div id='blog_name'><span>${user.blogName!?html}</span></div>
  </div>
  <div id='navbar'><#include 'navbar.ftl' ></div>    
  <#-- 调用页面指定的布局模式 布局2 -->
  <div id='wraper'>
	<table id='container' border='0' cellpadding='0' cellspacing='0'>
	<tr>
	<td id='column_1' class='col'><div class="dm"></div></td>
	<td id='column_2' class='col'>

<div id="webpart_4" class="widgetWindow">
<div class="widgetFrame">
<table class="widgetTable" border="0" cellpadding="0" cellspacing="0">
<thead><tr><td class="widgetHead h_lt"></td><td class="widgetHead h_mt"><div class="widgetHeader"><div class="ico"><img class="mod_icon" src="${SiteUrl}images/pixel.gif" height="16" width="16" border="0"></div><div id="webpart_4_h" class="title"><#if title??><script>document.write(unescape("${title}"));</script><#else>分类 ${category.name} 下的所有文章</#if></div></div></td><td class="widgetHead h_rt"></td></tr></thead><tbody><tr class="widgetEditor" style="display: none;"><td class="widgetEdit e_lt"></td><td class="widgetEdit e_mt"></td><td class="widgetEdit e_rt"></td></tr><tr><td class="widgetContent c_lt"></td><td class="widgetContent c_mt">
<div id="webpart_4_c" class="widgetContent">
正在加载……
</div>
<div id="__pager" class="pagination"></div>
<script type="text/javascript">
	function getContent(pageNo)
	{
	 var url = "${UserSiteUrl}py/user_category_article_list.py?cid=${cateId}&page=";
	 url = url + pageNo + "&seed=" + (new Date()).valueOf();
	 new Ajax.Request(url, { 
	      method: 'get',
	      onSuccess: function(xhr) {
	       document.getElementById("webpart_4_c").innerHTML = xhr.responseText;
	      },
	      onException: function(xhr){
	      document.getElementById("webpart_4_c").innerHTML = xhr.responseText;
	      },
	      onFailure: function(){
	        document.getElementById("webpart_4_c").innerHTML = xhr.responseText;
	      }
	    }
	  );
	}
	
	function pageInit()
	{
	 var p = new Pager("__pager",${pageCount},3,getContent);
	 p.build();
	 getContent(1);
	}
	
	var oldOnload = window.onload;
	if (typeof(window.onload) != "function")
	{
	 window.onload = pageInit;
	}
	else
	{
	 window.onload = function()
	 {
	  oldOnload();
	  pageInit();
	 }
	}
	</script>

</div></div>
</td><td class="widgetContent c_rt"></td></tr><tr><td class="widgetFoot f_lt"></td><td class="widgetFoot f_mt"></td><td class="widgetFoot f_rt"></td></tr></tbody><tfoot></tfoot></table></div></div>



</td>
	</tr>
	</table>
</div>
  <script type="text/javascript">
    App.start();
  </script>
  <div id="subMenuDiv"></div>
<#-- 原来这里是 include loginui.ftl  -->
  <script type="text/javascript" src="${SiteUrl}css/tooltip/tooltip_html.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>