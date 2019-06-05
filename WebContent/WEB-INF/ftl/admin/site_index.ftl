<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
 <title>管理</title>
 <link rel="stylesheet" type="text/css" href="../css/manage.css" />
 <script type='text/javascript'>
	function doPost(cmdtype)
	{
	 document.getElementById('oForm').cmd.value = cmdtype;
	 document.getElementById('oForm').submit();
	}
	function setDisplay(s_id,s_display)
	{
	 document.getElementById('oForm2').cmd.value='display';
	 document.getElementById('oForm2').siteId.value = s_id;
	 document.getElementById('oForm2').siteDisplay.value = s_display;
	 document.getElementById('oForm2').submit();
	}
	
	function setZone(pid,zone)
	{
	  document.getElementById('oForm2').cmd.value='zone';
	  document.getElementById('oForm2').siteId.value = pid;
	  document.getElementById('oForm2').siteDisplay.value = zone;
	  document.getElementById('oForm2').submit();
	}
  function checkSelectedItem()
  {
  	var guids = document.getElementsByName("guid");
  	for(var i=0;i<guids.length;i++)
  	{
  	 if(guids[i].checked) return true;
  	}
  	alert("请先选择一个模块。");
  	return false;
  }
 </script>
 <style>
 table.listTable td{padding:6px;}
 .bg{background:#F0F5F6;}
 </style>
</head>
<body>
<form method='post' id='oForm2' style='display:none'>
<input type='hidden' name='cmd' value='display' />
<input type='hidden' name='siteId' value='0' />
<input type='hidden' name='siteDisplay' value='0' />
</form>
<form method='post' id='oForm'>
<input type='hidden' name='cmd' value='' />
<h2>网站首页内容定制 <a href='${ContextPath}index.action?debug=1' target="preview" style='font-size:16px'>预览页面（无缓存）</a></h2>
<table class="listTable" style="width:auto">
<thead>
<tr style='text-align:center;'>
<th>位置</th>
<th>显示方式</th>
<th style='width:17px'><input type='checkbox' onclick='CommonUtil.SelectAll(this,"guid")'/></th>
<th>模块名称</th>
<th>是否显示</th>
<th>显示顺序</th>
<th>边框</th>
<th>高度(px)</th>
<th>宽度(px)</th>
<th>显示条数</th>
<th>每行最大字数</th>
<th>类型</th>
<th>修改</th>
</tr>
</thead>
<#assign tdIndex = 0 />
<#include "site_index_define.ftl" />
<#-- 这样使用的前提是按照zone顺序排序  -->
<#assign lastZone = 0 />
<#list siteItemList as nav>
<tr>
<#if lastZone != nav.moduleZone>
<#assign lastZone = nav.moduleZone />
<#assign tdIndex = tdIndex + 1 />
<#if lastZone &lt; 1 || lastZone &gt; position?size><#assign lastZone = 1 /></#if>
<td rowspan="${webpartZoneCount[lastZone-1]}" style="font-weight:bold;"<#if tdIndex%2 == 0> class="bg"</#if>>${position[lastZone-1]}</td>
<td rowspan="${webpartZoneCount[lastZone-1]}"<#if tdIndex%2 == 0> class="bg"</#if>>
<label><input onclick="setWebpartZoneShowType(${lastZone},0)" name="webpartZoneShowType${lastZone}" type="radio" value="0"<#if webpartZoneShowType[lastZone-1]==0> checked="checked"</#if> />每个模块显示一行</label><br/>
<label><input onclick="setWebpartZoneShowType(${lastZone},1)" name="webpartZoneShowType${lastZone}" type="radio" value="1"<#if webpartZoneShowType[lastZone-1]==1> checked="checked"</#if> />所有模块显示一行</label>
</td>
</#if>

<td<#if tdIndex%2 == 0> class="bg"</#if>>
<#if nav.partType != 100>
<input type='checkbox' name='guid' value='${nav.siteIndexPartId}' />
</#if>
</td>
<td<#if tdIndex%2 == 0> class="bg"</#if>>${nav.moduleName}</td>
<td<#if tdIndex%2 == 0> class="bg"</#if>>
<label>
<input type='radio' name='nav_${nav.siteIndexPartId}' onclick='setDisplay(${nav.siteIndexPartId},1)' <#if nav.moduleDisplay==1>checked='checked'</#if>/>显示
</label>
<label>
<input type='radio' name='nav_${nav.siteIndexPartId}' onclick='setDisplay(${nav.siteIndexPartId},0)' <#if nav.moduleDisplay==0>checked='checked'</#if>/>隐藏
</label>
</td>
<td<#if tdIndex%2 == 0> class="bg"</#if>><input style='width:40px' maxlength='4' name='z_${nav.siteIndexPartId}' value='${nav.moduleOrder}' /><input type='hidden' name='nav_id' value='${nav.siteIndexPartId}' /></td>
<td<#if tdIndex%2 == 0> class="bg"</#if>><#if nav.showBorder==1>显示<#else>隐藏</#if></td>
<td<#if tdIndex%2 == 0> class="bg"</#if>><#if nav.moduleHeight== 0>不限制<#else>${nav.moduleHeight}</#if></td>
<td<#if tdIndex%2 == 0> class="bg"</#if>><#if nav.moduleWidth== 0>不限制<#else>${nav.moduleWidth}</#if></td>
<td<#if tdIndex%2 == 0> class="bg"</#if>>${nav.showCount!0}</td>
<td<#if tdIndex%2 == 0> class="bg"</#if>>${(nav.textLength==0)?string("",nav.textLength)}</td>
<td<#if tdIndex%2 == 0> class="bg"</#if>>
<#if nav.partType == 0>
自定义内容
<#elseif nav.partType == 1>
系统分类模块
<#elseif nav.partType == 2>
<a href='usercate_article.py?contentSpaceId=${nav.sysCateId!}'>自定义分类模块</a>
<#elseif nav.partType == 100>
<a href='usercate_article.py?contentSpaceId=${nav.sysCateId!}'>特殊系统模块</a>
<#else>
未定义的模块类型
</#if>
</td>
<td<#if tdIndex%2 == 0> class="bg"</#if>>
<#if nav.partType == 0>
<a href='site_index_add.py?partId=${nav.siteIndexPartId}'>修改</a>
<#elseif nav.partType == 1>
<a href='site_index_sys_add.py?partId=${nav.siteIndexPartId}'>修改</a>
<#elseif nav.partType == 2>
<a href='site_index_contentspace_add.py?partId=${nav.siteIndexPartId}'>修改</a>
<#else>
不可编辑
</#if>
</td>
</tr>
</#list>
</table>
<div style='padding:4px 0'>
<input type='button' value='删除模块' class='button' onclick='if(checkSelectedItem()){doPost("delete")}' />
<input type='button' value='设为隐藏' class='button' onclick='if(checkSelectedItem()){doPost("hide")}' />
<input type='button' value='设为显示' class='button' onclick='if(checkSelectedItem()){doPost("show")}' />
<input type='button' value='修改顺序' class='button' onclick='doPost("order")' />
<input type='button' value='添加自定义模块' class='button' onclick='window.location.href="site_index_add.py"' />
<input type='button' value='添加系统分类模块' class='button' onclick='window.location.href="site_index_sys_add.py"' />
<input type='button' value='添加自定义分类模块' class='button' onclick='window.location.href="site_index_contentspace_add.py"' />
</div>
<div style="padding:10px 0;color:#f00">重要提示：如果您修改了模块的位置，请重新设置一下“每个模块显示一行”、“所有模块显示一行”这个设置，以确保所有的模块都使用了相同的显示模式命令，此操作过程不能少。如果模块是单独占一行显示，宽度属性设置将被忽略。</div>
</form>
<script type='text/javascript' src='${SiteUrl}js/jitar/core.js'></script>
<script>
function setWebpartZoneShowType(zoneId,showType){
  postData = 'cmd=showType&zoneId=' + zoneId + '&showType=' + showType + '&t=' + (new Date()).getTime();
  url = '${ContextPath}manage/site_index.py?t=' + (new Date()).getTime();
  new Ajax.Request(url, {
    method: 'post',
    parameters: postData,
    onSuccess: function(xport) { 
       alert("设置成功。")
      }
  });
}
</script>
</body>
</html>