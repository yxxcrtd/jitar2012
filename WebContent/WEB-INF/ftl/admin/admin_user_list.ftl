<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="../css/manage.css" />
<link rel="stylesheet" type="text/css" href="../css/msgbox.css" />
<script type="text/javascript" src='js/admin_user.js'></script>
<script type="text/javascript" src="../js/msgbox.js"></script>
<script type="text/javascript" src="../js/jitar/core.js"></script>
    <script type="text/javascript">
    function reset_pwd() {
        listform = document.forms["list_form"];
        if (hasChecked(listform) == false) {
            window.alert("没有选择要重置新密码的用户！");
            return false;
        }
        MessageBox.Show('MessageTip');
    }
    function upd_pwd() {
    vform = document.forms["list_form"];
        if (vform.reset_password.value == "") {
            window.alert("请为选择的用户填写重置的新密码！");
            vform.reset_password.focus();
            return false;
        }
        if (vform.reset_password.value.length < 6 || vform.reset_password.value.length > 20) {
            window.alert("密码的长度必须在6-20位之间！");
            vform.reset_password.focus();
            return false;
        }
        fs = document.getElementsByName('userId');
        var myuid = "";
        for (i = 0; i < fs.length; i++) {
            if (fs[i].checked) {
                myuid += fs[i].value + ","
            }
        }
        postData = "myUserId=" + myuid + "&cmd=reset_password&reset_password=" + vform.reset_password.value;
        url = "user.action";
        new Ajax.Request(url, { 
        method: 'post',
        parameters: postData,
        onSuccess: function(xhr) {
        //document.getElementById("x").innerHTML = (xhr.responseText)
        if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == "OK") {
            window.alert("    操作成功！");
        } else {
            window.alert("    操作失败！");
        }
        }, onFailure:function(xhr){alert('出现意外(如：服务器关闭，数据库连接异常，断电等)，操作失败！' + xhr.responseText);             
        }
        });
        MessageBox.Close();
    }

    function open_meetings(list_form) {
        if (false == hasChecked(list_form)) {
            alert("没有选择用户！");
            return false;
        } else {
            if (false == confirm("您确定要开启选择用户的个人会议室？")) {
                return false;
            }
        }
        opt();
    }
    
    function close_meetings(list_form) {
        if (false == hasChecked(list_form)) {
            alert("没有选择用户！");
            return false;
        } else {
            if (false == confirm("您确定要关闭选择用户的个人会议室？")) {
                return false;
            }
        }
        opt();
    }
    
    function opt() {
        uids = document.getElementsByName("userId");
        var uid = "";
        for (i = 0; i < uids.length; i++) {
            if (uids[i].checked) {
                uid += uids[i].value + ","
            }
        }
        postData = "cmd=opt&uid=" + uid;
        url = "meetings.action";
        new Ajax.Request(url, {
            method: 'post',
            parameters: postData,
            onSuccess: function(xhr) {
                if(xhr.responseText.replace(/(^\s*)|(\s*$)/g, "") == "OK") {
                    window.alert("    操作成功！");
                } else {
                    window.alert("    操作失败！");
                }
            },
            onFailure:function(xhr){alert('出现意外(如：服务器关闭，数据库连接异常，断电等)，操作失败！' + xhr.responseText);             
            }
        });
    }
    
    function doPost(x)
    {
     document.list_form.cmd.value=x;
     document.list_form.submit();
    }
    </script>
</head>

<body onKeyDown="close_dialog(event);">
<h2>${typeName!'用户'} 管理</h2>
<!-- 搜索表单 -->
<form name="search_form" action="?" method="get">
<div class='search'>
    <input type="hidden" name="cmd" value="list" />
    <input type='hidden' name='typeId' value='${typeId!0}' />
    关键字：<input type='text' name="k" value='${k!?html}' style="width: 100px;" onfocus="this.select();" onmouseover="this.focus();" />
    <#if !f??><#assign f = 'name'></#if>
    <select name='f' style='width:70px'>
      <option value='name' desc='loginName, nickName, trueName'>用户名</option>
      <option value='email' ${(f == 'email')?string('selected','')}>邮件地址</option>
      <option value='tags' ${(f == 'tags')?string('selected','')} desc='tags'>工作室标签</option>
      <option value='intro' ${(f == 'intro')?string('selected','')} desc='blogName, blogIntroduce'>工作室简介</option>
      <option value='unitTitle' ${(f == 'unitTitle')?string('selected','')} desc='unitTitle'>所属机构</option>
    </select>
<select name="subjectId">
 <option value=''>所属学科</option>
<#if subject_list?? >
<#list subject_list as subj>
    <option value="${subj.msubjId}" ${(subj.msubjId==(subjectId!0))?string('selected','')}>
        ${subj.msubjName!?html}
    </option>
</#list>
</#if>
</select>
    
<select name="gradeId">
 <option value=''>所属学段</option>
<#if grade_list?? >
<#list grade_list as grade>
<#if grade.isGrade>
  <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
  <#if grade.isGrade>
    ${grade.gradeName!?html}
  <#else>
    &nbsp;&nbsp;${grade.gradeName!?html}
  </#if>
</option>
</#if>
</#list>
</#if>
</select>    
<input type="submit" class="button" value=" 检  索 " />
</div>
</form>

<!-- 用户列表表单 -->
<form name="list_form" action="?" method="post">
  <input type="hidden" name="cmd" value='list' />
  <input type="hidden" name="set_to" value="" />
  <#include 'user_list_core.ftl' >
<div class='funcButton'>
  <input class="button" id="selAll" name="sel_All" onclick="on_checkAll(list_form, 1)" type="button" value=" 全 选 ">
  <#if typeId?? && typeId == 1 && platformType?? && platformType == '2'>
  <input type="button" class="button" onclick="doPost('push')" value="设为推送">
  <input type="button" class="button" onclick="doPost('unpush')" value="取消推送">
  </#if>
  <#if typeId??>
  <input type="button" class="button" onclick="unsetTo(list_form, '-${typeId!0}')" value=" 取消${typeName!?html} ">
  </#if>
<#if cmdtype=='deleted'>
<#else>
<#if usermgr3??>
    <#if usermgr3==1>
        <input class="button" id="DelAll" name="Del_All" onclick="delSel(list_form)" type="button" value=" 删 除 ">
    </#if>
 <#else>
    <input class="button" id="DelAll" name="Del_All" onclick="delSel(list_form)" type="button" value=" 删 除 ">
</#if>
</#if>
      <input class="button" id="auditAll" name="audit_all" onclick="auditSel(list_form)" type="button" value=" 审 核 ">
<#if usermgr3??>
    <#if usermgr3==1>
      <input class="button" id="lockAll" name="lock_all" onclick="lockSel(list_form)" type="button" value=" 锁 定 ">
      <input class="button" id="renewAll" name="renew_all" onclick="renewSel(list_form)" type="button" value="恢复正常">
    </#if>
 <#else>
      <input class="button" id="lockAll" name="lock_all" onclick="lockSel(list_form)" type="button" value=" 锁 定 ">
      <input class="button" id="renewAll" name="renew_all" onclick="renewSel(list_form)" type="button" value="恢复正常">
</#if>
      
  <select onChange="setToSelect(list_form, this)">
  <option value="">执行设置</option>
  <optgroup label='设置为'>
    <#if userTypeList??>
    <#list userTypeList as ut>
    <#if typeId??>
    <#if ut.typeId != typeId>
      <option value="${ut.typeId}">设置为${ut.typeName!?html}</option>
    </#if>
    <#else>
    <option value="${ut.typeId}">设置为${ut.typeName!?html}</option>
    </#if>
    </#list>
    </#if>
  </optgroup>
  <optgroup label='取消设置'>
  <#if userTypeList??>
    <#list userTypeList as ut>
        <option value="-${ut.typeId}">取消设置为${ut.typeName!?html}</option>
    </#list>
    </#if>
</optgroup>
</select>
<#if usermgr3??>
    <#if usermgr3==1>
        <input class="button" type="button" value="重置密码" onClick="reset_pwd();" />
    </#if>
 <#else>
   <input class="button" type="button" value="重置密码" onClick="reset_pwd();" />
 </#if>
          
<#if ("0" != Util.isOpenMeetings())>
<input class="button" id="openmeetings1" name="open_meeting" onclick="openSel(list_form)" type="button" value="开启个人会议">
<input class="button" id="openmeetings2" name="close_meeting" onclick="closeSel(list_form)" type="button" value="关闭个人会议">
</#if>
</div>

<div id="blockUI" onClick="return false" onMousedown="return false" onMousemove="return false" onMouseup="return false" onDblclick="return false">&nbsp;</div>
<div id="MessageTip" class="hidden">
    <div class="boxHead">
        <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src="../images/dele.gif" /></div>
        <div class="boxTitle" onmousedown="MessageBox.dragStart(event)">为选择的用户重新设置新密码：</div>
    </div>
    <div style="padding: 10px; text-align: center;">
        <div style="line-height: 20px;"><br /></div>
          新密码：<input type="text" name="reset_password" />
        <input type="button" class="button" value=" 确  定 " onClick="upd_pwd();" />
        <div style="line-height: 20px;"><br /></div>
    </div>
</div>
</form>
</body>
</html>
