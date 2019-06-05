<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title>文章管理</title>
  <link rel="stylesheet" type="text/css" href="../../css/manage.css" />
  <link rel="stylesheet" type="text/css" href="../../css/msgbox.css" />
  <script type='text/javascript' src='../../js/msgbox.js'></script>
  <script type='text/javascript' src='../../js/core.js'></script>
  	<style type="text/css">
		.watermark {
			color: #999 !important;
		}
		.watermark2 {
			color: #999 !important;
			font-style: italic !important;
		}
		.watermark3 {
			color: #c77 !important;
		}
  	</style>
  
  </head>
<body>
<#include '/WEB-INF/ftl/course/preparecourse_title.ftl' >
<div class='pos'>
  您现在的位置： <a href='manage_pc.py?prepareCourseId=${prepareCourse.prepareCourseId}'>【${prepareCourse.title}】管理首页</a>
  &gt;&gt; <span>成员管理</span> 
</div>
<form name='theForm' id='theForm' method='post' action="?prepareCourseId=${prepareCourse.prepareCourseId}">
<input type="hidden" id="u_id" name="createUserId" value="" />
<div id="searchDiv" style="text-align: right; width: 100%;display:block;">
	关键字：<input type="text" name="userkey" id="userkey" value="${userkey!}">
	<input type="submit" class="button" value=" 查 询 " />
</div>

<table class='listTable' cellspacing='1'>
    <thead>
      <tr>
        <th width='17'></th>
        <th>成员</th> 
        <!--
        <th width='5%'>查看个案</th>
        -->
        <th width='15%'>加入时间</th>
        <th width='20%'>状态/角色</th>
        <th width='240'>操作</th>
      </tr>
    </thead>
    <tbody>
    <#list user_list as member>
      <tr>
        <td><input type='checkbox' name='userId' value='${member.userId}' /></td>
        <td>
          <a href='${SiteUrl}go.action?loginName=${member.loginName}' target='_blank'>${member.nickName!}</a>(${member.loginName!})
        </td>
        <!--
        <td><a href='${SiteUrl}manage/course/showUserCourse.py?prepareCourseId=${prepareCourse.prepareCourseId}&userId=${member.userId}' target='_blank'>查看个案</a></td>
        -->
        <td>${member.joinDate}</td>
        <td>
         <#if member.status == 0>正常
         <#elseif member.status == 1>
         	<font color='red'>待审核</font>
         	<div style='padding:4px;border:1px solid #EEE;'>申请说明：${member.privateContent!?html}</div>
         <#else>
         	未知
         </#if>
         [<#if member.userId == prepareCourse.createUserId || member.userId == prepareCourse.leaderId >
         <#if member.userId == prepareCourse.leaderId ><font color='blue'>主备人</font></#if>
         <#if member.userId == prepareCourse.createUserId ><font color='red'>发起人</font></#if>       
         <#else>普通成员</#if>]
        </td>
        <td>
        <#if member.userId == prepareCourse.leaderId >
        <a href='manage_leader.py?cmd=delete&amp;prepareCourseId=${prepareCourse.prepareCourseId}&amp;leaderId=${member.userId}' onclick="return canDeleteLeader(${prepareCourse.leaderId},${prepareCourse.createUserId})">撤销主备人</a>
        <#else>
        <a href='manage_leader.py?cmd=add&amp;prepareCourseId=${prepareCourse.prepareCourseId}&amp;leaderId=${member.userId}'>设为主备人</a>
        </#if>
        <#if member.userId == prepareCourse.createUserId >
        <a href='' onclick='doPost(document.theForm,6);return false;'>更换发起人</a>
        </#if>
        <#if prepareCourse.lockedUserId == member.userId >
        <input type='hidden' name='unLockUserId' value='${member.userId}' />
        <a href='javascript:void(0)' onclick='doUnLockPost();return false;'>解除备课编辑锁定</a>
        <br/>签出时间：
        ${prepareCourse.lockedDate?string("yyyy-MM-dd HH:mm:ss")}
        </#if>        
        </td>
        </tr>
  </#list>
  </tbody>
</table>
<div id="blockUI" onclick="return false" onmousedown="return false" onmousemove="return false" onmouseup="return false" ondblclick="return false">
  &nbsp;
</div>
<div id='MessageTip' class='message_frame' style='width:600px;height:600px;'>
  <div class='boxHead'>
   <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src='../../images/dele.gif' /></div>
   <div class="boxTitle" onmousedown="MessageBox.dragStart(event)"><img src='../images/dialog.gif' align='absmiddle' hspace='3' />请选择用户成员：</div>
  </div>
  <div style='padding:2px;'>
  	<iframe src="" name="usershow" style="width:100%;height:520px" frameborder="0"></iframe>
  	<div style="text-align:right;padding-top:8px">
    <input type='button' value='设置为创建人' class='button' onclick='doPost(this.form,7)' />
    <input type='button' class='button' value=' 取  消 ' onclick='return MessageBox.Close();' />
    </div> 
  </div> 
</div>

<div class='pager'>
<#include 'pager.ftl'>
</div>

<div class='funcButton'>
  <input type='hidden' name='cmd' value='' />
  <input type='button' class='button' value=' 全 选 ' onclick='select_all()' />
  <input type='button' class='button' value='移除成员' onclick='doPost(this.form,1)' />
  <input type='button' class='button' value='通过审核' onclick='doPost(this.form,2)' />
  <input type='button' class='button' value='取消审核' onclick='doPost(this.form,3)' />  
</div>
<div style='padding:8px;color:#f00;'>说明：1、主备人只能设置一个，如果没有主备人，则创建人为主备人。2、主备人和发起人不能移除，必须先把他们设置为普通成员才能进行移除。</div>
<#if group?? >
<hr/>
<input name='memberId' id='memberId' type='hidden' />
<input id='memberName' style='width:200px' readonly='readonly' /><input type='button' class='button' value='选择用户' onclick='selectGroupUser(${group.groupId})' />
<input type='button' class='button' value='添加成员' onclick='doPost(this.form,4)' />
<br/>注：每次只能添加一位成员。
</#if>
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

function doPost(oF,m)
{
	if(m==4)
	{
		if(oF.memberId.value=="")
		{
		 alert("请选择一个要添加的用户。");
		 return false;
		}
	}
    if(m==1 || m==2 || m==3)
    {
		if(!hasSelectdItem())
		{
		 alert("请选择一个用户。");
		 return;
		}
	}
	
    if(m == 6)
    {
    	window.frames["usershow"].location = "select_course_member.py?prepareCourseId=${prepareCourse.prepareCourseId}";
        MessageBox.Show('MessageTip');
        return;
    }
    if(m == 7)
    {
    	var _uid = oF.createUserId.value;
    	if(_uid == "")
    	{
    	 alert("请选择一个用户。");
    	 return false;
    	}
    }
    oF.cmd.value=m
    oF.submit();
}

function doUnLockPost()
{
   document.theForm.cmd.value='8'
   document.theForm.submit();
}

function selectUser()
{
  url = '${SiteUrl}selectUser.py?showgroup=1&singleuser=0&inputUser_Id=memberId&inputUserName_Id=memberName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');
}

function selectGroupUser(gid)
{
  url = '${SiteUrl}manage/course/get_group_member.py?prepareCourseId=${prepareCourse.prepareCourseId}&groupId=' + gid + '&inputUser_Id=memberId&inputUserName_Id=memberName'
  window.open(url,'_blank','left=100,top=50,height=450,width=550,toolbar=no,menubar=no,scrollbars=yes,resizable=1');	
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

function canDeleteLeader(leaderId,createId)
{
 if(leaderId == createId)
 {
  alert("主备人和发起人是同一人时，不能撤销主备人。");
  return false;
 }
 return true;
}
</script>
</body>
</html>
