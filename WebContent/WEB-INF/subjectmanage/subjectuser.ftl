<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${SiteUrl}css/msgbox.css" />
  
  <script>
  function doPost(arg)
  {
  	document.getElementById("oForm").cmd.value=arg;
  	document.getElementById("oForm").submit();
  }
  </script>
  <script src='${SiteUrl}js/jitar/core.js'></script>
<script type="text/javascript" src="${SiteUrl}/js/msgbox.js"></script>
<script type="text/javascript" src='${SiteUrl}manage/js/admin_user.js'></script>
   <script type="text/javascript">
    function unauditSel(list_form) {
      if (hasChecked(list_form) == false) {
        alert("没有选择任何要操作的用户.");
        return false;
      } else {
        if (confirm("您是否确定设置用户为待审核?") == false) {
          return false;
        }
      }
      list_form.cmd.value = "unaudit";
      list_form.submit();
    }
    function unDelSel(list_form) {
      if (hasChecked(list_form) == false) {
        alert("没有选择任何要操作的用户.");
        return false;
      } else {
        if (confirm("您是否确定设置用户为待删除?") == false) {
          return false;
        }
      }
      list_form.cmd.value = "unDelSel";
      list_form.submit();
    } 
    function removefromsubject(list_form) {
      if (hasChecked(list_form) == false) {
        alert("没有选择任何要操作的用户.");
        return false;
      } else {
        if (confirm("您是否确定将用户从学科中删除?") == false) {
          return false;
        }
      }
      list_form.cmd.value = "removefromsubject";
      list_form.submit();
    }         
   
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
        if (vform.reset_password.value.length < 5 || vform.reset_password.value.length > 20) {
            window.alert("密码的长度必须在5-20位之间！");
            vform.reset_password.focus();
            return false;
        }
        fs = document.getElementsByName('guid');
        var myuid = "";
        for (i = 0; i < fs.length; i++) {
            if (fs[i].checked) {
                myuid += fs[i].value + ","
            }
        }
        postData = "myUserId=" + myuid + "&cmd=reset_password&reset_password=" + vform.reset_password.value;
        url = "${SiteUrl}manage/user.action";
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
    
    </script>  
</head>
<body>
<h2>
学科用户管理
</h2>
<div style='text-align:right;padding:6px 0'>
<form method="GET">
<input name='id' type='hidden' value='${subject.subjectId}' />
请输入关键字：
<input name='k' value="${k!?html}" />
<select name='f'>
 <option value='loginName'<#if f?? && f == 'loginName'> selected='selected'</#if>>用户登录名</option>
 <option value='trueName'<#if f?? && f == 'trueName'> selected='selected'</#if>>用户真实姓名</option>
</select>
<input type="submit" class="button" value=" 检  索 " />
</form>
</div>
<form method='post' name="list_form" id='oForm' style='padding-left:20px'>
<#if user_list??>
<table class="listTable" cellspacing="1">
  <thead>
    <tr>
    <th><input type='checkbox' id='chk' onclick='CommonUtil.SelectAll(this,"guid");' /></th>
    <th style='width:48px;'>头像</th>
    <th>昵称,实名</th>  
    <th>机构</th>
    <th>学段/学科</th>    
    <th>
 	<select name='userStatus' onchange='window.location.href="subjectuser.py?id=${subject.subjectId}&ustate=" + this.options[this.selectedIndex].value'>
 	<option value='-1'>用户状态</option>
 	<option value='0'<#if ustate == '0'> selected='selected'</#if>>正常</option>
 	<option value='1'<#if ustate == '1'> selected='selected'</#if>>待审核</option>
 	<option value='2'<#if ustate == '2'> selected='selected'</#if>>待删除</option>
 	<option value='3'<#if ustate == '3'> selected='selected'</#if>>已锁定</option>
 	</select>
 	</th>
    <th>本学科管理权限</th>
    <th>头衔</th>
    </tr>
  </thead>
<#list user_list as user >
<tr>
<td style='width:17px'><input type='checkbox' name='guid' value='${user.userId}' /></td>
<td><img src="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif';" width="48" height="48" border="0" title="${user.blogName!}" /></td>
<td>
	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.nickName?html}</a><br/>
	<a href='${SiteUrl}go.action?loginName=${user.loginName}' target='_blank'>${user.trueName?html}</a><br/>
	注册日期：${user.createDate?string('yyyy-MM-dd')}
</td>
<td>
<#if user.unitId??>
<#assign unit = Util.unitById(user.unitId)>
<#if unit??>
<a href='${SiteUrl}go.action?unitName=${unit.unitName}' target='_blank'>${unit.unitTitle!?html}</a>
</#if>
</#if>
</td>
<td>
<#assign usgList = Util.getSubjectGradeListByUserId(user.userId)>
<#if usgList?? && (usgList?size> 0) >
<#list usgList as usg>
<#if usg.gradeId??>${Util.gradeById(usg.gradeId).gradeName!?html}<#else>未设置</#if>/<#if usg.subjectId??>${Util.subjectById(usg.subjectId).msubjName!?html}<#else>未设置</#if>
<#if usg_has_next><br/></#if>
</#list>
</#if>
</td>
<td>
<#if user.userStatus == 0>正常
    <#elseif user.userStatus == 1><font color='red'>待审核</font>
    <#elseif user.userStatus == 3><font color='red'>已锁定</font>
    <#elseif user.userStatus == 2><font color='red'>已删除</font>
    <#else ><font color='red'>未知状态</font>
</#if>
</td>
<td>
${Util.getAccessControlListByUserAndObject(user.userId,7,subject.subjectId)}<br/>
${Util.getAccessControlListByUserAndObject(user.userId,8,subject.subjectId)}<br/>
${Util.getAccessControlListByUserAndObject(user.userId,9,subject.subjectId)}
</td>
<td>
<#if user.userType??>
<#assign showTypeName = Util.typeIdToName(user.userType) >
    <#if showTypeName??>
        <#list showTypeName?split("/") as x>
        <#if (x?length) &gt; 0 ><div>${x}</div></#if>
        </#list> 
    </#if>
</#if>	
</td>
</tr>
</#list>
</table>
</#if>
<#if pager??>
<#include "/WEB-INF/ftl/pager.ftl">
</#if>
<div style='padding:6px'>
<input class='button' type='button' value=' 全  选 ' onclick='document.getElementById("chk").click();CommonUtil.SelectAll(document.getElementById("chk"),"guid");' />

<input class='button' id="unDelAll" name="unDel_all" onclick="unDelSel(list_form)"  type='button' value='转为已删除'/>
<input class="button" id="auditAll" name="audit_all" onclick="auditSel(list_form)" type="button" value=" 审 核 ">
<input class="button" id="unAuditAll" name="unaudit_all" onclick="unauditSel(list_form)" type="button" value=" 待审核 ">
<input class="button" type="button" value="重置密码" onClick="reset_pwd();" />
<input class='button' type='button' value='从学科中删除所选用户' onclick="removefromsubject(list_form)" type="button" />
<input type="hidden" name="cmd" value=""/>
<select name='selcmd'>
<option value=''>选择要执行的管理操作</option>
<#if isSystemAdmin == "1">
<option value='user_admin'<#if cmd == 'user_admin'> selected='selected'</#if>>设置为本学科用户管理员权限</option>
<option value='unuser_admin'<#if cmd == 'unuser_admin'> selected='selected'</#if>>取消本学科用户管理员权限</option>
<option value='content_admin'<#if cmd == 'content_admin'> selected='selected'</#if>>设置为本学科内容管理员权限</option>
<option value='uncontent_admin'<#if cmd == 'uncontent_admin'> selected='selected'</#if>>取消本学科内容管理员权限</option>
</#if>
</select>
<input class='button' type='submit' value='执行选择的操作' onclick='if(this.form.selcmd.value==""){alert("请选择一个操作。");return false;}else{this.form.cmd.value=this.form.selcmd.value;}' />
</div>

<div id="blockUI" onClick="return false" onMousedown="return false" onMousemove="return false" onMouseup="return false" onDblclick="return false">&nbsp;</div>
<div id="MessageTip" class="hidden">
    <div class="boxHead">
        <div class="boxCloseButton" onclick="return MessageBox.Close();"><img src="${SiteUrl}images/dele.gif" /></div>
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