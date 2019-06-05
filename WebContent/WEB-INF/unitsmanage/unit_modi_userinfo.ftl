<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <title></title>
  <link href="${SiteUrl}css/manage.css" rel="stylesheet" type="text/css" />
  <script>
  function doPost(arg)
  {
  	document.forms[0].cmd.value=arg;
  	document.forms[0].submit();
  }
  </script>  
  <script src="${SiteUrl}js/jitar/core.js"></script>
</head>
<body>
<form method="post">
<table class="listTable" cellspacing="1" cellpadding="0">
  <tr>
    <td align="right" width="20%">
      <b>用户登录名：</b>
    </td>
    <td>
      ${user.loginName!?html}
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>个人头像：</b>
    </td>
    <td align="left">
     <table cellspacing="6">
     <tr>
     <td>
    <img id="icon_image" src="${SSOServerUrl +'upload/'+user.userIcon!"images/default.gif"}" onerror="this.src='${SiteUrl}images/default.gif';" 
            width="64" height="64" />
            </td>
            <td>
<!--            
  <#if icon_list?? >
      <select onchange="icon_selected(this)">
        <option value="">选择一个系统头像</option>
        <option value="origin">(原头像)</option>
      <#list icon_list as icon>
        <option value="${icon}">${Util.fileName(icon)}</option>
      </#list>
      </select>
  </#if>
-->  
      <input type="hidden" name="userIconOld" value="${user.userIcon!}" />
      <input type="hidden" name="userIcon" value="${user.userIcon!}" />
<script>
function icon_selected(sel) {
  var src = sel.options[sel.selectedIndex].value;
  if (src == null || src == "") return;
  if (src == "origin")
    src = document.forms[0].userIconOld.value;
    
  var image = document.getElementById("icon_image");
  image.src = "${SiteUrl}" + src;
  document.forms[0].userIcon.value = src;
}
</script>
 </td>
  </tr>
  </table>
    </td>
  </tr>
        <tr>
          <td align="right">
            <b>身份：</b>
          </td>
          <td>
            <input type="radio" name="role" value="3" <#if user.positionId == 3>checked</#if> />教师
			<input type="radio" name="role" value="5" <#if user.positionId == 5>checked</#if> />学生
            <input type="radio" name="role" value="4" <#if user.positionId == 4>checked</#if> />教育局职工
            <input type="radio" name="role" value="2" <#if user.positionId == 2>checked</#if> />机构管理员
            <input type="radio" name="role" value="1" <#if user.positionId == 1>checked</#if> />系统管理员
          </td>
        </tr>  
		<tr>
			<td align="right">
				<strong>真实姓名：</strong>
	    	</td>
			<td>
				<input type="text" name="trueName" maxLength="25" value="${user.trueName!?html}" />
			</td>
		</tr>
		<tr>
			<td align="right">
				<strong>昵称：</strong>
			</td>
			<td>
				<input type="text" name="nickName" maxLength="25" value="${user.nickName!?html}" />
			</td>
		</tr>
		<tr>  
			<td align="right">
				<strong>电子邮件：</strong>
			</td>
			<td>
				<input type="text" name="email" size="50" maxLength="125" value="${user.email!?html}" />
			</td>
		</tr>
		<tr>
			<td align="right">
				<strong>身份证号码：</strong>
			</td>
			<td>
				<input type="text" name="IDCard" size="25" maxLength="18" value="${user.idCard!?html}" />
			</td>
		</tr>
  <tr>
    <td align="right">
      <b>QQ号码：</b>
    </td>
    <td>
      <input type="text" name="QQ" size="18" maxLength="125" value="${user.qq!?html}" />
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>工作室名称：</b>
    </td>
    <td>
      <input type="text" name="blogName" size="50" maxLength="125" value="${user.blogName!?html}" />
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>工作室标签：</b>
    </td>
    <td>
      <input type="text" name="userTags" size="50" value="${user.userTags!?html}" /> 以 ',' 逗号分隔.
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>工作室介绍：</b>
    </td>
    <td>
      <textarea name="blogIntroduce" cols="49" rows="5" >${user.blogIntroduce!?html}</textarea>
      <br />最多填写256个汉字.
    </td>
  </tr>
  <tr>
    <td align="right">
      <b>性别：</b>
    </td>
    <td>
      <input type="radio" name="gender" value="1" ${(user.gender == 1)?string('checked','')} />男
      <input type="radio" name="gender" value="0" ${(user.gender == 0)?string('checked','')} />女
    </td>
  </tr>
  <!--
  <tr>
    <td align="right">
      <b>学段/学科/分类：</b>
    </td>
    <td>
      <select name="gradeId"  onchange='grade_changed(this)'>
        <option value="">所属年级</option>
      <#if grade_list??>
        <#list grade_list as grade>
          <option value="${grade.gradeId}" <#if grade.gradeId==(user.gradeId!0)>selected</#if>>
            <#if grade.isGrade>
              ${grade.gradeName!?html}
            <#else>
              &nbsp;&nbsp;${grade.gradeName!?html}
            </#if>
          </option>
        </#list>
      </#if>
      </select>
 
      <select name="subjectId">
        <option value="">所属学科</option>
        <#if subject_list?? >
          <#list subject_list as msubj>
            <option value="${msubj.msubjId}" ${(msubj.msubjId == (user.subjectId!0))?string('selected', '')}>
            ${msubj.msubjName!?html}
            </option>
          </#list>
        </#if>
      </select>
            <span id='subject_loading' style='display:none'>
              <img src='images/loading.gif' align='absmiddle' 
                hsapce='3' />正在加载学科信息...
            </span>


      <select name="categoryId">
        <option value="">工作室分类</option>
        <#if syscate_tree??>
            <#list syscate_tree.all as c>
            <option value="${c.id}" ${(c.categoryId==(user.categoryId!0))?string('selected','')}>
                ${c.treeFlag2} ${c.name!?html}
            </option>
            </#list>
        </#if>
      </select>
    </td>
  </tr>
  -->
</table>
      
<table border="0" width="98%">
 <tr>
   <td width="30%">&nbsp;</td>
   <td width="60%">
    <input type="submit" class="button" value="  修  改  " />&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" class="button" value=" 取 消 " onClick="window.history.go(-1);" />
   </td>
 </tr>
</table>
      
</form>

<script>
function grade_changed(sel)
{
  // 得到所选学科.
  var gradeId = sel.options[sel.selectedIndex].value;
  var subject_sel = document.profile_form.subjectId;

  //if (gradeId == null || gradeId == '' || gradeId == 0) {
  //  clear_options(subject_sel);
  //  add_option(subject_sel, '', '选择学科');
  //  return;
  //} 
  subject_sel.disabled = true;
  var img = document.getElementById('subject_loading');
  img.style.display = '';
  
  // 用 AJAX 请求该区县下的机构, 并填充到 unitId select 中.
  url = '${SiteUrl}manage/admin_subject.py?cmd=subject_options&gradeId=' + gradeId + '&tmp=' + Math.random();
  new Ajax.Request(url, {
    method: 'get',
    onSuccess: function(xport) { 
        var options = eval(xport.responseText);
        clear_options(subject_sel);
        add_option(subject_sel, '', '选择学科');
        for (var i = 0; i < options.length; ++i)
          add_option(subject_sel, options[i][0], options[i][1]);
        img.style.display = 'none';
        subject_sel.disabled = false;
      }
  });
}
function clear_options(sel) {
  sel.options.length = 0;
}
function add_option(sel, val, text) {
  opt = document.createElement("OPTION");
  opt.value = val;
  opt.text = text;
  if (val == "${user.unitId!}")
    opt.selected = true;
  sel.options.add(opt);
}
</script>
</body>
</html>
