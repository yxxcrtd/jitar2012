<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>管理</title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="${SiteUrl}css/msgbox.css" />
  <script type='text/javascript' src='${SiteUrl}js/msgbox.js'></script>
  
</head>
<body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='manage_pc.py?prepareCourseId=${prepareCourse.prepareCourseId}'>【${prepareCourse.title}】管理首页</a>
  &gt;&gt; <span>资源管理</span> 
</div>

<form name='theForm' id='theForm' method='post'>
<table class='listTable' cellspacing="1">
  <thead>
  <tr>
    <th width='32'>&nbsp;</th>
    <th width='35%'>标题</th>
    <th>流程</th>
    <th>资源类型</th>
    <th>上传人</th>
    <th>上传时间</th>   
    <th>下载/评论</th>
  </tr>
  </thead>
  <tbody>
<#list resource_list as r>
  <#assign u = Util.userById(r.userId) >
  <tr>
    <td><input type='checkbox' name='prepareCourseResourceId' value='${r.prepareCourseResourceId}' /></td>
    <td>
    <img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' hspace='3' />
    <a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}' target='_blank'>${r.title!?html}</a></td>
    <td>${r.pcsTitle}</td>    
    <td>
    <#if r.sysCateId?? >
    <#assign sysCate = Util.subjectById(r.sysCateId) >
    ${sysCate.msubjName!?html}
    </#if>
    </td>     
    <td><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName}</a></td>
    <td>${r.createDate?string('MM-dd hh:mm')}</td>    
    <td align='right'>${r.downloadCount}/${r.commentCount}</td>
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
  <input type='button' class='button' value=' 移 除 ' onclick='delete_article(this.form)' />
  选择目标流程：<select name='stageId'>
  <#if stage_list??>
  <#list stage_list as s>
  <option value='${s.prepareCourseStageId}'>${s.title}</option>
  </#list>
  </#if>
  </select>
  <input type='button' class='button' value='执行流程转移' onclick='move_article(this.form)' />
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
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">资源加分</div>
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
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">资源罚分</div>
  </div>
  <div style='padding:10px;'>
    <h4>注意：资源罚分处理将删除该资源</h4>
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
<script>
var checkedStatus=true;
function select_all() {
  var ids = document.getElementsByName('prepareCourseResourceId');
  for(i = 0;i<ids.length;i++)
  {
    ids[i].checked = checkedStatus
  }
  checkedStatus =!checkedStatus  
}

function delete_article(oForm)
{
	if(!hasSelectdItem())
	{
	 alert("请选择要删除的资源。");
	 return;
	}
    if(confirm('你真的要删除吗?'))
    {
        oForm.cmd.value="delete"
        oForm.submit()
    }
}

function move_article(oForm)
{
	if(!hasSelectdItem())
	{
	 alert("请选择要转移的资源。");
	 return;
	}
	
   if(confirm('你真的要转移到【' + oForm.stageId.options[oForm.stageId.selectedIndex].text + '】吗?'))
    {
        oForm.cmd.value="move"
        oForm.submit()
    }  
}

function hasSelectdItem()
{
 var ids = document.getElementsByName('prepareCourseResourceId');
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
     alert("请选择资源。")
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
     alert("请选择要加分的资源。")
     return;
    }
    document.getElementById('theForm').cmd.value="addscore";
    document.getElementById('theForm').submit();
      
  }
  function minusscoreDo(){
    if(!hasSelectdItem())
    {
     alert("请选择要罚分的资源。")
     return;
    }
    document.getElementById('theForm').cmd.value="minusscore";
    document.getElementById('theForm').submit();
  }
  
</script>
</body>
</html>
