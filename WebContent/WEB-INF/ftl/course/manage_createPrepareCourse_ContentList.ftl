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
  &gt;&gt; <span>个案管理</span> 
</div>

<form name='theForm' id='theForm' method='post'>
<table class='listTable' cellspacing='1'>
    <thead>
      <tr>
        <th width='17'></th>
        <th>成员个案</th> 
        <th width='10%'>精华</th>
        <th width='240'>操作</th>
      </tr>
    </thead>
    <tbody>
    <#list user_list as member>
      <tr>
        <td><input type='checkbox' name='userId' value='${member.userId}' <#if member.bestState == true>isBest="isBest"<#else>isBest=""</#if> /></td>
        <td>
          <a href='${SiteUrl}go.action?loginName=${member.loginName}' target='_blank'>${member.nickName!}</a>的<a href='${SiteUrl}p/${prepareCourse.prepareCourseId}/0/py/show_preparecourse_user_content.py?userId=${member.userId}' target='_blank'>个案</a>
          <#if (member.bestState??)><#if (member.bestState==true)><img border="0" src="${SiteUrl}manage/images/ico_best.gif"/></#if></#if>
          </td>
        <td>
         	<#if member.bestState == true><font color='red'>精华</font>
         	</#if>&nbsp;
        </td>
        <td>
	                
        <a href='#' onclick='doPost2(document.theForm2,8,${member.userId});return false;'>删除个案</a>
        <#if member.bestState == true>
        	<a href='#' onclick='doPost2(document.theForm2,10,${member.userId});return false;'>取消精华</a>
        <#else>
        	<a href='#' onclick='doPost2(document.theForm2,9,${member.userId});return false;'>设置精华</a>
        </#if>
	        
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
  <input type='button' class='button' value='删除个案' onclick='doPost(this.form,8)' />
  <input type='button' class='button' value='设置精华' onclick='doPost(this.form,9)' />
  <input type='button' class='button' value='取消精华' onclick='doPost(this.form,10)' /> 
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
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">个案加分</div>
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
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">个案罚分</div>
  </div>
  <div style='padding:10px;'>
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
<input type='hidden' name='userId' value='' />
</form>
<script>
var checkedStatus=true;
function select_all() {
  var ids = document.getElementsByName('userId');
  for(i = 0;i<ids.length;i++)
  {
    ids[i].checked = checkedStatus
  }
  checkedStatus =!checkedStatus  
}
function doPost2(oF,m,userid)
{
    if(m ==8)
    {
        if (confirm('确认删除个案吗?')==false)
        return;
    }
    oF.cmd.value=m;
    oF.userId.value=userid;
    oF.submit();
}

function doPost(oF,m)
{
	if(m==8||m==9)
	{	
		if(!hasSelectdItem())
		{
		 alert("请选择一个用户个案。");
		 return;
		}	
	}
	
	if(m==10)
	{	
		if(!hasSelectdBestItem())
		{
		 alert("请选择一个用户精华个案。");
		 return;
		}	
	}
	
	
    if(m ==8)
    {
        if (confirm('确认删除个案吗?')==false)
        return;
    }
    oF.cmd.value=m;
    oF.submit();
}
function hasSelectdItem()
{
 var ids = document.getElementsByName('userId');
  for(i = 0;i<ids.length;i++)
  {
    if(ids[i].checked) return true;
  }
  return false;
}

function hasSelectdBestItem()
{
 var ids = document.getElementsByName('userId');
  for(i = 0;i<ids.length;i++)
  {
    var isbest = ids[i].getAttribute("isBest");
    if(ids[i].checked && isbest == "isBest") return true;
  }
  return false;
}
 function doScore(arg){
        //addscore  minusscore
    if(!hasSelectdItem())
    {
     alert("请选择个案。")
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
     alert("请选择要加分的个案。")
     return;
    }
    document.getElementById('theForm').cmd.value="addscore";
    document.getElementById('theForm').submit();
      
  }
  function minusscoreDo(){
    if(!hasSelectdItem())
    {
     alert("请选择要罚分的个案。")
     return;
    }
    document.getElementById('theForm').cmd.value="minusscore";
    document.getElementById('theForm').submit();
  }
</script>
</body>
</html>
