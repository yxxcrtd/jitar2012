<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>标签列表</title>
  <meta http-equiv="expires" content="0" />
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/common/common.css" />
    <link id='skin' rel="stylesheet" type="text/css" href="${SiteUrl}css/skin/${page.skin!'skin1'}/skin.css" />
    <!-- 布局模板 -->
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/layout/layout_${(page.layoutId!'1')}.css" />
    <!-- ToolTip -->
    <link rel="stylesheet" type="text/css" href="${SiteUrl}css/tooltip/tooltip.css" />
  <script>
    var JITAR_ROOT = '${SiteUrl}';  <#-- 站点根路径 -->
    var page_ctxt = {
      owner : null,
      pages : [{id: ${page.pageId}, title: '${page.title!}', layoutId: ${page.layoutId!0}}],
      widgets: [ 
    <#list widget_list as widget>
      {id: ${widget.id}, page:${widget.pageId}, column:${widget.columnIndex}, 
        title:'${widget.title?js_string}', module:'${widget.module}', ico:'', 
        data:{ ${widget.data!} } } 
      <#if widget_has_next>,</#if>
    </#list>
      ]
    };
  </script>
  <script src='${SiteUrl}js/jitar/core.jsp'></script>
  <script src='${SiteUrl}js/jitar/lang.js'></script>
</head>
<body>
<h2>标签列表</h2>

<#include '../../layout/layout_1.ftl'>

<script>
App.start();
</script>

<h2>DEBUG</h2>
<li>$page = ${page}
<li>$widget_list = ${widget_list}

</body>
</html>
