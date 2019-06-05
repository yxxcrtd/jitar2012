<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>协作组列表</title>
  <link rel="stylesheet" type="text/css" href="../css/manage.css" />   
  <script type="text/javascript" src='js/admin_group.js'></script>
</head>
<body>
<#include 'admin_header.ftl' >
<#if !(type??)><#assign type = ''></#if> 
  <h2>${typeTitle!'协作组'}管理</h2>

<div class='pager'>
<form name='searchForm' action='?' method='get'>
  <input type='hidden' name='cmd' value='list' />
  <input type='hidden' name='type' value="${type!?html}" />
  关键字：<input type='text' name='k' value="${k!?html}" size='12' />
  <select name='sc'>
    <option value=''>所属分类</option>
<#if group_categories??>
  <#list group_categories.all as c >
    <option value='${c.categoryId}' ${((sc!0) == c.categoryId)?string('selected', '') }>${c.treeFlag2} ${c.name!?html}</option> 
  </#list>
</#if>
  </select>
  <select name='subj'>
    <option value=''>所属学科</option>
<#if subject_list??>
  <#list subject_list as su >
    <option value='${su.msubjId}' ${(su.msubjId == subj!0)?string('selected', '')}>
    	${su.msubjName!?html}
    </option>
  </#list>
</#if>
  </select>
<select name="gradeId">
        <option value=''>所属学段</option>
<#if grade_list?? >
        <#list grade_list as grade>
            <option value="${grade.gradeId}" <#if grade.gradeId==(gradeId!0)>selected</#if>>
                <#if grade.isGrade>
                  ${grade.gradeName!?html}
                <#else>
                  &nbsp;&nbsp;${grade.gradeName!?html}
                </#if>
              </option>
        </#list>
</#if>
    </select>
  <input type='submit' class='button' value=' 查询 ' />
</form>
</div>

<form name='theForm' action='?' method='post'>
  <input type='hidden' name='cmd' value='list' />
<table class='listTable' cellspacing='1'>
  <thead>
    <tr>
      <th width='6%'>ID</th>
      <th width='30%'>协作组名称</th>
      <th width='20%'>创建人<br/>(创建时间)</th>
      <th width='12%'>学科学段</th>
      <th width='8%'>状态</th>
      <th width='8%'>成员数<br/>访问量</th>
      <th width='10%'>操作</th>
    </tr>
  </thead>
  <tbody>
  <#if group_list?size == 0>
    <tr>
      <td colspan='9' align='center'>
        <div style='margin: 12px;'>没有找到符合条件的协作组</div>
      </td>
    </tr>
  </#if>
  <#foreach group in group_list>
    <tr>
      <td style="text-align: center;">
      	<input type='checkbox' name='groupId' value='${group.groupId}' />
      		${group.groupId}
      	</td>
      <td>
        <table width='100%' border='0'><tr><td width='48'>
          <img src='${Util.url(group.groupIcon!SiteUrl + "images/group_default.gif")}' width='48' height='48' border='0' />
        </td><td align='left' valign='top'>
          <a href='${SiteUrl!}g/${group.groupName}' target='_blank' title='${group.groupIntroduce!?html}'>${group.groupTitle!}</a>
          <#if group.isBestGroup><img src='images/ico_you.gif' align='absmiddle' border='0' hspace='2' 
            title='优秀团队' /></#if><#if group.isRecommend><img src='images/ico_rcmd.gif' align='absmiddle' border='0' title='推荐协作组' /></#if>
          <br/>英文名: ${group.groupName!}
          <br/>标签: [ 
            <#list Util.tagToList(group.groupTags!'') as t>
              <a href='${SiteUrl}showTag.action?tagName=${t?url("UTF-8")}' target='_blank'>${t!?html}</a><#if t_has_next>,</#if>
            </#list> ]
        </td></tr></table>
      </td>
      <td>
        <a href='${SiteUrl}go.action?loginName=${group.loginName}' target='_blank'>${group.nickName}</a>
        <br/><nobr>${group.createDate?string('yyyy-MM-dd HH:mm:ss')}</nobr>
      </td>
      <td style="text-align: center;">
        ${group.XKXDName!?html}
      </td>
      <td style="text-align: center;">
        <#switch group.groupState>
        <#case 0>正常<#break>
        <#case 1><font color='red'>待审核</font><#break>
        <#case 2><font color='red'>锁定</font><#break>
        <#case 3><font color='red'>已删除</font><#break>
        <#case 4><font color='red'>隐藏</font><#break>
        <#default><font color='red'>未知状态</font><#break>
        </#switch>
      </td>
      <td style="text-align: center;">
      ${group.userCount!}
      /
      ${group.visitCount!}
      </td>
      <td style="text-align: center;">
      <#if group.groupState == 1><#-- 待审核 -->
        <a href='?cmd=audit&groupId=${group.groupId}'>审核</a>
      </#if>
      <#if group.groupState == 0><#-- 正常 -->
        <a href='group.action?cmd=edit&groupId=${group.groupId}'>编辑</a>
      </#if>
      <#if group.groupState == 2><#-- 锁定 -->
        <a href='?cmd=unlock&groupId=${group.groupId}'>解锁</a>
      </#if>
      <#if group.groupState == 3><#-- 删除 -->
        <a href='?cmd=recover&groupId=${group.groupId}'>恢复</a>
      <#else>
        <a href='?cmd=delete&groupId=${group.groupId}' onclick='return confirm_delete()'>删除</a>
      </#if>
					<#if ("0" != Util.isOpenMeetings())>
						<#if (1 == Util.IsVideoGroup(group.groupId))>
							<br/><br/>
							<a href="${Util.isOpenMeetings()}&amp;groupid=${group.groupId!}" target="_blank"><font style="color: #FF00FF; font-weight: bold;">进入协作组会议</font></a>
						</#if>
					</#if>
      </td>
    </tr>
  </#foreach>
  </tbody>
</table>

<div class='pager'>
  <#include '../inc/pager.ftl' >  
</div>
<div class='funcButton'>
  <input type='button' class='button' value=' 全 选 ' onclick='select_all();' />
<#if type == 'best'>
  <input type='button' class='button' value=' 取消优秀团队 ' onclick='submit_command("unbest");' />
<#elseif type == 'rcmd'>
  <input type='button' class='button' value=' 取消推荐协作组' onclick='submit_command("unrcmd");' />
</#if>
<#if type == 'deleted'>
  <input type='button' class='button' value=' 恢  复 ' onclick='submit_command("recover");' />
  <input type='button' class='button' value=' 彻底删除 ' onclick='crash_s();' />
<#elseif type == 'locked'>
  <input type='button' class='button' value=' 解除锁定 ' onclick='submit_command("unlock");' />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_s();' />
<#elseif type == 'hided'>
  <input type='button' class='button' value=' 取消隐藏 ' onclick='submit_command("unhide");' />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_s();' />
<#else>
  <input type='button' class='button' value=' 审核通过 ' onclick='audit_s();' />
  <input type="button" class="button" value="开启协作组会议" onclick="open_s();" />
  <input type="button" class="button" value="关闭协作组会议" onclick="close_s();" />
  <input type='button' class='button' value=' 删 除 ' onclick='delete_s();' />
</#if>
  <select name='execmd' onchange='execmd_s(this.options[this.selectedIndex].value);'>
    <option value=''>选择并执行操作</option>
    <optgroup label='审核操作'>
	    <option value='audit'>审核通过</option>
	  </optgroup>
	  <optgroup label='锁定和隐藏'>
	    <option value='lock'>锁定协作组</option>
	    <option value='unlock'>解除锁定协作组</option>
      <option value='hide'>隐藏协作组</option>
      <option value='unhide'>取消隐藏协作组</option>
	  </optgroup>
	  <optgroup label='设置优秀团队和推荐'>
	    <option value='best'>设置为优秀团队</option>
      <option value='unbest'>取消优秀团队</option>
      <option value='rcmd'>设置为推荐协作组</option>
      <option value='unrcmd'>取消推荐协作组</option>
	  </optgroup>
    <optgroup label='删除操作'>
      <option value='delete'>删除协作组</option>
      <option value='recover'>恢复协作组</option>
    </optgroup>
  </select>
</div>
</form>

<#include 'admin_footer.ftl' >
  
</body>
</html>
