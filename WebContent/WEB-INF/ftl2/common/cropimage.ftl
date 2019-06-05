<!doctype html>
<html>
 <head>
  <meta charset="utf-8">
  <title>图片裁切工具</title>
  <script src="${ContextPath}js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script>
  function setCookie(cookieName,cookieValue) {
    var expire = new Date(2059,1,1);
    document.cookie = cookieName + "=" + escape(cookieValue) + ";expires=" + expire.toGMTString();
  };
  
  function getCookie(c_name){
    if (document.cookie.length>0)
    {
    c_start=document.cookie.indexOf(c_name + "=");
    if (c_start!=-1){ 
      c_start=c_start + c_name.length+1; 
      c_end=document.cookie.indexOf(";",c_start);
      if (c_end==-1) c_end=document.cookie.length;
      return unescape(document.cookie.substring(c_start,c_end));
      } 
    }
    return "";
  }
  </script>
</head>
<body>
<h2>请输入开始的photoId和结束的photoId值，将裁切此区间的旧图片。开始值必须小于结束值。</h2>
<div style="padding:20px 10px">
开始的 photoId 值：<input id="startId" value="0" /><br/>
结束的 photoId 值：<input id="endId" value="0" /><br/>
<input id="btn" type="button" onclick="startCrop()" value="开始裁切" />
</div>
<table border="0" style="width:1000px;">
<tr>
<td style="border:1px solid green;">
<div id="processBarPic" style="height:20px;background:green;float:left"><span style="font-size:0px">&nbsp;</span></div>
</td>
</tr>
<tr style="text-align:center">
<td id="processBar">
0%
</td>
</tr>
</table>
<br/><br/>
<table border=1 cellpadding="6">
<tr>
<td>已经完成的photoId：</td><td id="finishedId"></td>
</tr>
<tr>
<td>当前正在进行转换的photoId：</td><td id="runningId"></td>
</tr>
<tr>
<td>剩余记录数：</td><td id="leftId"></td>
</tr>
<tr>
<td>执行状态：</td><td id="stateId">转换未进行。</td>
</tr>
<tr>
<td>失败（重试）次数：</td><td id="failId"></td>
</tr>
</table>
<div id="info"></div>
<script>
var startPhotoId = 0;
var endPhotoId = 0;
var step = 50; //每次处理10个Id
var sId = 0;
var eId = 0;
var isStop = false;
var failCount = 0;

//开始执行
function startCrop(){
  //第一次执行，设置开始结束的id，继续执行的话，需要保留执行过的状态。  
  if($("#btn").val() == "开始裁切")
  {
    startPhotoId = parseInt($("#startId").val(), 10);
    endPhotoId = parseInt($("#endId").val(), 10);
    if(isNaN(startPhotoId) || isNaN(endPhotoId)){
     alert("请输入正确的数字值。")
     return;
    }
    if(startPhotoId > endPhotoId ){
      alert("开始值不能大于结束值.")
      return;
    }
    
    if(startPhotoId < 1 || endPhotoId < 1){
      alert("必须输入大于0的值");
      return;
    }
    sId = endPhotoId - step;
    eId = endPhotoId;
    $("#leftId").html(endPhotoId);
  }
  if($("#btn").val() == "开始裁切" || $("#btn").val() == "继续执行"){
    isStop = false;
    $("#stateId").html("转换正在进行中……");
    $("#btn").val("暂停执行");
  }
  else
  {
    isStop = true;
    $("#stateId").html("执行已暂停。");
    $("#btn").val("继续执行");
    return;
  }
  setCookie("start", startPhotoId);
  process();
}

function process(){
  if(sId < startPhotoId){
    sId = startPhotoId-1;
  }
  if(eId < startPhotoId){
    $("#processBarPic").width("100%")
    $("#processBar").html("完成！");
    $("#stateId").html("恭喜你，转换完成！！！！");
    $("#runningId").html("");
    $("#leftId").html("0");
    $("#btn").attr("disabled", true);
    $("#btn").val("开始裁切");
    rturn;
  }
      
  //显示状态
  $("#runningId").html((sId+1) + " 至 " + eId);
  showBar();
  if(isStop){
    return;
  }
  //为了显示的需要，采用倒序的方法执行，也就是先转换最新发布的图片。
  $.ajax({
    url:"imgCut.action?startPhotoId=" + startPhotoId + "&endPhotoId=" + eId + "&step=" + step,
    cache: false
    }).done(function(){
      setCookie("end", (sId+1));
      $("#finishedId").html((sId+1) + " 至 " + endPhotoId);
      $("#leftId").html(Math.max(sId + 1 - startPhotoId, 0 ));
      eId = sId;
      sId = eId - step;
      process();
  }).fail(function(){
    $("#failId").html(++failCount);
    process();
  });
}

function showBar(){
$("#info").html()
  //防止被0除
  if(endPhotoId == startPhotoId)
  {
    $("#processBarPic").width("50%")
    $("#processBar").html("50%");
  }
  else{
    var percent = Math.floor((endPhotoId-sId)*100/(endPhotoId-startPhotoId));
    if(percent > 100){
      percent = 100;
    }
    $("#processBarPic").width(percent + "%")
    $("#processBar").html(percent + "%");
  }
}


$(function(){
  var cookieStartData = getCookie("start")
  var cookieEndData = getCookie("end")
  if(cookieStartData != "" && isNaN(parseInt(cookieStartData, 10)) == false){
    $("#startId").val(cookieStartData)
  }
  if(cookieEndData != "" && isNaN(parseInt(cookieEndData, 10)) == false){
    $("#endId").val(cookieEndData)
  }
});

</script>
</body>
</html>