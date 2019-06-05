<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>${prepareCourse.title?html}</title>
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
  <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'default'}/skin.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_2.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <#include ('common_script.ftl') >
  <script src='${SiteUrl}js/jitar/core.js'></script>
  <script src='${SiteUrl}js/jitar/lang.js'></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/Drag.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/tooltip.js"></script>
  <script type="text/javascript" src="${SiteUrl}js/jitar/login.js"></script>
</head> 
<body>
    <#include 'func.ftl' >
    <div id='progressBar' style='position:absolute;width:200px;left:600px;top:8px;border:1px solid red;display:none;background:yellow;float:right;z-index:100'>正在加载……</div>
    <div id='header'>
      <div id='blog_name'><span>${prepareCourse.title!?html}</span></div>
    </div>
    <div id='nav'><#include 'navbar.ftl' ></div>
    <#include '/WEB-INF/layout/layout_2.ftl' >
    <div id='placerholder1' title='首次编写个案，请设置一种个案内容类型' style='display:none;padding:1px;'>
    <form method='post'>
    <input type="hidden" name="cmd" value="contentype" />
	<label><input type="radio" name="contentype" value="1" />在线 HTML 编辑</label><br/><br/>
	<label><input type="radio" name="contentype" value="2" />直接从网站打开 Word 2003 编辑，Flash 显示</label><br/><br/>
	<label><input type="radio" name="contentype" value="3" />直接从网站打开 Word 2007 编辑，Flash 显示</label><br/><br/>
	<label><input type="radio" name="contentype" value="4" />直接从网站打开 Word 2010 编辑，Flash 显示</label><br/><br/>
	<label><input type="radio" name="contentype" value="100" />从网站下载文件，编辑完成后再上传(支持 .doc、.docx、ppt、pptx文件）</label>
	<div style='text-align:right'>
	<input type='submit' value='保存设置' />
	</div>
	</form>
    </div>
    <div id='page_footer'></div>
    <script>App.start();</script>
    <div id="subMenuDiv"></div>
    <script src="${SiteUrl}css/tooltip/tooltip_html.js" type="text/javascript"></script>
    <#-- 原来这里是 include loginui.ftl  -->
    <script type="text/javascript" src="${SiteUrl}js/jitar/msgtip.js"></script>
</body>
</html>