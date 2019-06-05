<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/index/dtree.css" />
  <script type="text/javascript" src="${SiteUrl}js/jitar/dtree.js"></script>
 </head>
 <body>
 <#if columnList??>
<script type="text/javascript">
  var JITAR_ROOT = "${SiteUrl}" 
  d = new dTree("d");
  <#list columnList as c>
  d.add(${c.columnId},-1,"${c.columnName}","?cmd=right&columnId=${c.columnId}","${c.columnName}",'rightmain');
  </#list>
  document.write(d);
  //d.openAll();
</script>
<#else>
无栏目管理
</#if>
</body>
</html>