<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="shortcut icon" href="${SiteUrl}images/favicon.ico" />
  <link rel="stylesheet" type="text/css" href="${SiteThemeUrl}article.css" />
  <style>
	html,body{padding:0;margin:0;border:0;width:auto;}
  </style>
  <script src='js/jitar/core.js'></script>
  <script>
  function resizeIframe()
  {
  	h = Math.max(document.body.offsetHeight,document.documentElement.offsetHeight);
  	window.parent.document.getElementById("_right").style.height = h + 20 + "px";
  }
  </script>
 </head>
<body onload="resizeIframe()">
<#if plan_list??>
<div style="font-weight:bold;color:#f00">正在进行中的评课活动<span id="_dot"></span></div>
<script>
var dot = [".","..","...","....",".....",".....","......"];
var dot_index = 0;
window.setInterval(function showDotting(){
if(dot_index >= dot.length) dot_index = 0;
document.getElementById("_dot").innerHTML = dot[dot_index++];
},200);
</script>
<#list plan_list as ev>
<div class='res1_c' style="padding:0 2px 6px 0">
<table>
<tr><th>${ev.evaluationYear}年${(ev.evaluationSemester==0)?string("上学期","下学期")}第${ev.evaluationTimes}次评课</th></tr>
<tr><td>开始时间：${ev.startDate?string("yyyy年M月d日H点")}</td></tr>
<tr><td>结束时间：${ev.endDate?string("yyyy年M月d日H点")}</td></tr>
</table>
<div><a href="evaluation_content_edit.py?evaluationPlanId=${ev.evaluationPlanId}" target="_top"><strong>点击这里开始评课</strong></a></div>
</div>
<div style="height:8px;"></div>
</#list>
<div style="padding:10px 0;color:#f00"><a href="evaluation_show_user.py" target="_top">我参与的评课</a></div>
<#else>
<div style="padding:0 10px">当前没有评课活动。</div>
<div style="padding:10px;color:#f00"><a href="evaluation_show_user.py" target="_top">我参与的评课</a></div>
</#if>
</body>
</html>