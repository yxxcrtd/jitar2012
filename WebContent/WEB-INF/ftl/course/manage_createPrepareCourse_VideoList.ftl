<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>文章管理</title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="../../css/msgbox.css" />
  <script type='text/javascript' src='../../js/msgbox.js'></script>
  <script type='text/javascript' src='../../js/core.js'></script>
  
</head>
<body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='manage_pc.py?prepareCourseId=${prepareCourse.prepareCourseId}'>【${prepareCourse.title}】管理首页</a>
  &gt;&gt; <span>视频管理</span> 
</div>

<form name='theForm' id='theForm' method='post'>
<table class='listTable' cellspacing='1'>
    <thead>
      <tr>
        <th width='17'></th>
        <th  width='20%'>视频</th> 
        <th>视频描述</th>
        <th width='120'>操作</th>
      </tr>
    </thead>
    <tbody>
    <#list pcv_list as pcv>
	  <#if (pcv.trueName??)><#assign userName = pcv.trueName >
	  <#elseif (pcv.nickName??)><#assign userName = pcv.nickName >
	  <#elseif (pcv.loginName??)><#assign userName = pcv.loginName >
	  </#if>
      <tr>
        <td><input type='checkbox' name='vId' value='${pcv.videoId}' /></td>
		    <td>
			<a href="${SiteUrl}manage/video.action?cmd=show&videoId=${pcv.videoId}" target="_blank"><img src="${pcv.flvThumbNailHref!}" /></a>
		    </td>
		    <td valign="top">
				视频标题：${pcv.videoTitle}<br/>
				发布者：${userName}<br/>
				发布时间：${pcv.createDate}<br/>			    	
		    </td>
        <td>
           <a href='#' onclick='doPost2(document.theForm2,1,${pcv.videoId});return false;'>删除该视频</a>
        </td>
        </tr>
  </#list>
  </tbody>
</table>
<div class='pager'>
<#include 'pager.ftl'>
</div>
<div class='funcButton'>
  <input type='hidden' name='cmd' value='' />
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
  <input type='button' class='button' value='删除视频' onclick='doPost(this.form,1)' />
  <input class='button' type='button' value='加分' onclick="doScore('addscore')" />
  <input class='button' type='button' value='罚分' onclick="doScore('minusscore')" />  
  
</div>


<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>
<#-- 加分对话框 -->
<div id='MessageAddScoreTip' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='${SiteUrl}images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">视频加分</div>
  </div>
  <div style='padding:10px;'>
    <table style='width:300px' border='0'>
      <tr>
        <td style='width:60px'>加分分值:</td>
        <td>
          <select name="add_score">
            <option value="0">0分</option>
            <option value="1" selected>1分</option>
            <option value="2">2分</option>
            <option value="3">3分</option>
            <option value="4">4分</option>
            <option value="5">5分</option>
            <option value="6">6分</option>
            <option value="7">7分</option>
            <option value="8">8分</option>
            <option value="9">9分</option>
            <option value="10">10分</option>
          </select>
        </td>
        </tr>
        <tr>
        <td style="vertical-align:middle">加分原因:<br/>(百字以内)</td>
        <td>
          <textarea style="width:90%;height:80px" name="add_score_reason" maxlength="100"></textarea>
        </td>
      </tr>
    </table>
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='addscoreDo();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>

<#-- 减分对话框 -->
<div id='MessageMinusScoreTip' class='message_frame' onkeydown='close_dialog(event)'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='${SiteUrl}images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">视频罚分</div>
  </div>
  <div style='padding:10px;'>
    <h4>注意：视频罚分处理将删除该视频</h4>
    <table style='width:300px' border='0'>
      <tr>
        <td style='width:60px'>罚分分值:</td>
        <td>
          <select name="minus_score">
            <option value="0">0分</option>
            <option value="-1" selected>1分</option>
            <option value="-2">2分</option>
            <option value="-3">3分</option>
            <option value="-4">4分</option>
            <option value="-5">5分</option>
            <option value="-6">6分</option>
            <option value="-7">7分</option>
            <option value="-8">8分</option>
            <option value="-9">9分</option>
            <option value="-10">10分</option>
          </select>
        </td>
      </tr>
       <tr> 
        <td>罚分原因:<br/>(百字以内)</td>
        <td>
          <textarea style="width:90%;height:80px" name="minus_score_reason" maxlength="100"></textarea>
        </td>
      </tr>
    </table>
  </div> 
  <div style='text-align:center;clear:both;padding:10px'>
    <input type='button' class='button' value = ' 确  定 ' onclick='minusscoreDo();' />
    <input type='button' class='button' value = ' 取  消 ' onclick="return MessageBox.Close();" />
  </div>
</div>

</form>
<form name='theForm2' method='post'>
<input type='hidden' name='cmd' value='' />
<input type='hidden' name='vId' value='' />
</form>
<script>
var checkedStatus=true;
function select_all() {
  var ids = document.getElementsByName('vId');
  for(i = 0;i<ids.length;i++)
  {
    ids[i].checked = checkedStatus
  }
  checkedStatus =!checkedStatus  
}
function doPost2(oF,m,id)
{
    if(m ==1)
    {
        if (confirm('确认删除视频吗?')==false)
        return;
    }
    oF.cmd.value=m;
    oF.vId.value=id;
    oF.submit();
}

function doPost(oF,m)
{
    if(m ==1)
    {
    
    if(!hasSelectdItem())
    {
     alert("请选择要删除的视频。");
     return;
    }
    
        if (confirm('确认删除视频吗?')==false)
        return;
    }
    oF.cmd.value=m;
    oF.submit();
}
function hasSelectdItem()
{
 var ids = document.getElementsByName('vId');
  for(i = 0;i<ids.length;i++)
  {
    if(ids[i].checked) return true;
  }
  return false;
}
 function doScore(arg){
        //addscore  minusscore
    if(!hasSelectdItem())
    {
     alert("请选择视频。")
     return;
    }
    if(arg=="addscore"){
        //加分
        MessageBox.Show('MessageAddScoreTip'); 
        return;
    }
    if(arg=="minusscore"){
        //罚分
        MessageBox.Show('MessageMinusScoreTip'); 
        return;
    }
  }
  function addscoreDo(){
    if(!hasSelectdItem())
    {
     alert("请选择要加分的视频。")
     return;
    }
    document.getElementById('theForm').cmd.value="addscore";
    document.getElementById('theForm').submit();
      
  }
  function minusscoreDo(){
    if(!hasSelectdItem())
    {
     alert("请选择要罚分的视频。")
     return;
    }
    document.getElementById('theForm').cmd.value="minusscore";
    document.getElementById('theForm').submit();
  }
</script>
</body>
</html>
