<!doctype html>
<html>
<head>
	<title>${yang!}</title>
<style>
html,body{padding:2px;margin:0;}
</style>
</head>
<body>
<center>
<#if error??>
<div style="padding-top:20px" id="_error_msg"><span style='color:red;font-weight:bold;'>${error!}</span></div>
<script>
window.onload=function()
{
  window.parent.document.getElementById("showWindowFrame").innerHTML = document.getElementById("_error_msg").innerHTML;
}
</script>
<#else>
<#if swf??>
<#if orginIsSwf??>
<embed src='${swf}' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width="100%" height="480"></embed>
<br/><a href="${swf}" target="_blank">打开新浏览器窗口播放</a>
<#else>
<#if showWaiting?? && showWaiting == "">
<div id="__info">正在进行第0次检查文件是否转换完成……</div>
<script src='${SiteUrl}js/jitar/core.js' type="text/javascript"></script>
<script>
var tryCount = 0;
var timer = null;
function checkFile()
{  
  tryCount++;
  document.getElementById("__info").innerHTML = "正在进行第" + tryCount + "次检查文件是否转换完成……";
  //检查文件是否存在 
  if(tryCount > 15){
   clearInterval(timer);
   document.getElementById("__info").innerHTML = "在给定的时间内没有检测到文件转换成功，请手动刷新本页看看。";
   return;
  }
  var url = "${swf}?tmp=" + Math.random();
  new Ajax.Request(url, { 
      method: 'head',
      onSuccess:function(xhr){
      if(xhr.status==200)
      {
        clearInterval(timer);
        document.getElementById("__info").innerHTML = "转换成功，正在加载结果。";
        window.setTimeout('window.location.href="${SiteUrl}jython/previewResource.py?resource=${resourceId}&showWaiting=0"',2000);    
      } 
      }
    }
);
} 
timer = setInterval("checkFile()",3000);
</script>
<#else>

</#if>
</#if>
<#else>
无预览内容
</#if>

</#if>
</center>
</body>
</html>