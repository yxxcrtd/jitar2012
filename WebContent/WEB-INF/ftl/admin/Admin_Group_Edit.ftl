<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css">
</head>
<body>
<#if group.groupId != 0>
  <#include 'group_title.ftl' >
	<div class='pos'>
	  您现在的位置： <a href='group.py?cmd=home&amp;groupId=${group.groupId}'>协作组管理首页</a>
	  &gt;&gt; <span>协作组信息</span>
	</div>
	<#if !(canManage??)><#assign canManage = false></#if> 
	<br/>
<#else >
  <h2>创建协作组</h2>
  <#assign canManage = true>
</#if>

<form name='groupForm' method='post' action='group.action'>
  <input type='hidden' name='cmd' value='save' />
  <input type='hidden' name='groupId' value='${group.groupId}' />
<table class='listTable' cellspacing='1'>
  <tbody>
    <tr>
      <td class='title' width='25%' align='right' valign='top'><b>协作组中文标题：</b></td>
      <td>
        <input type='text' name='groupTitle' value='${group.groupTitle!?html}' size='32' />
        <font color='red'>*</font> 协作组的中文标题, 不能和别的协作组重复.
      </td>
    </tr>
    <tr>
      <td class='title' align='right' valign='top'><b>协作组英文名：</b></td>
      <td>
      <#if group.groupId == 0>
        <input type='text' name='groupName' value='${group.groupName!?html}' size='32' maxlength='32' />
        <font color='red'>*</font> 协作组的英文名字，也用于访问群组时候作为路径，4-16个字符，可以是英文、数字、下划线，第一个必须是英文.
         协作组创建之后名字就不能更改了, 请仔细考虑好名字并填写.
      <#else >
        <input type='text' name='groupName' value='${group.groupName!?html}' disabled='disabled' />
          (创建之后不能修改协作组英文名字)
      </#if>
      </td>
    </tr>
    <tr>
      <td class='title' align='right' valign='top'><b>协作组图标：</b></td>
      <td>
      <#if default_icons?? >
        <select onchange='javascript:change_icon(this)'>
          <option value=''>选择系统图标</option>
        <#list default_icons as ic >
          <option value='${ic}'>${Util.fileName(ic)}</option>
        </#list>
        </select>
      </#if>
        <img id='group_icon' src='${Util.url(group.groupIcon!"images/group_default.gif")}' width='64' height='64' border='0' />
        <input type='hidden' name='groupIcon' value='${group.groupIcon!}' />
      </td>
    </tr>
    <tr>
      <td  align='right' valign='top'><b>学科学段：</b></td>
      <td>
        <select name="subjectId">
          <option value=''>选择学科</option>
      <#if subject_list?? >
        <#list subject_list as subject>
          <option value='${subject.msubjId}' ${(subject.msubjId == (group.subjectId!0))?string('selected', '')} >${subject.msubjName!?html}</option>
        </#list>
      </#if>
        </select>
				<select name="gradeId">
				  <option value='0'>选择学段</option>
			<#if grade_list?? >
				<#list grade_list as grade>
					<option value="${grade.gradeId}" ${(grade.gradeId == (group.gradeId!0))?string('selected', '') } >
						<#if grade.isGrade>${grade.gradeName!?html}<#else>&nbsp; &nbsp;${grade.gradeName!?html}</#if>
					</option>
				</#list>
			</#if>
				</select>
      </td>
    </tr>
    <tr>
      <td class='title'  align='right' valign='top'><b>协作组类别：</b></td>
      <td>
        <select name="categoryId" id="categoryId">
          <option value=''>选择协作组分类</option>
      <#if syscate_tree?? >
        <#list syscate_tree.all as category>
          <option value='${category.id}' ${(category.id == (group.categoryId!0))?string('selected', '')}>${category.treeFlag2} ${category.name}</option>
        </#list>
      </#if>
        </select>
      </td>
    </tr>
    <tr>
      <td class='title'  align='right' valign='top'><b>协作组标签：</b></td>
      <td>
        <input type='text' name='groupTags' value='${group.groupTags!?html}' size='32' />
          (多个标签请以逗号分隔)
      </td>
    </tr>
    <tr>
      <td class='title'  align='right' valign='top'><b>协作组加入条件：</b></td>
      <td>
         <input type='radio' name='joinLimit' value='0' ${(group.joinLimit == 0)?string('checked', '')} /> 任意加入  
         <input type='radio' name='joinLimit' value='1' ${(group.joinLimit == 1)?string('checked', '')} /> 申请加入 
         <input type='radio' name='joinLimit' value='2' ${(group.joinLimit == 2)?string('checked', '')} /> 仅可邀请 
      </td>
    </tr>
    <tr>
      <td class='title'  align='right' valign='top'><b>协作组说明(<font color='red'>* 至少${Util.JitarConst.MIN_GROUP_DESC}个字</font>)：</b><br/>正确、清晰的说明有助于用户找到您的协作组，也有助于管理员的审核.</td>
      <td><textarea rows='4' cols='60' name='groupIntroduce'>${group.groupIntroduce!?html}</textarea></td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <td></td>
      <td>
      <#if canManage>
        <input type='submit' class='button' value='${(group.groupId == 0)?string("  创 建  ", "  修 改  ")}' />
      </#if>
        <input type='button' class='button' value=' 返 回 ' onclick='window.history.back();' />
      </td>
    </tr>
  </tfoot>
</table>
</form>

<script>
function change_icon(sel) {
  var icon = sel.options[sel.selectedIndex].value;
  if (icon == null || icon == '') return;
  
  var img = document.getElementById('group_icon');
  img.src = '../' + icon;
  
  document.groupForm.groupIcon.value = icon;
}
</script>

<div>
<!-- 放一些说明在这里 -->
</div>

</body>
</html>
