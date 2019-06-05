<#-- 用户列表核心部分 -->
<#if !(cfg_showEmail)??>
  <#assign cfg_showEmail = 'false'>
</#if>
<#assign is = Util.isOpenMeetings()>

<table class="listTable" cellspacing="1">
  <thead>
      <tr>
          <th width="6%">用户ID</th>
          <th width="8%">用户头像</th>
          <th width="15%" style="padding-left: 12px;">登录名<br />真实姓名</th>
          <th><#if (cfg_showEmail == "true")>电子邮件<br/></#if>注册时间</th>
          <th>学段/学科</th>
          <th>机构</th>
    		  <th>角色</th>
    		  <th>头衔</th>
    		  <th>状态</th>
	      <th>操作</th>
      </tr>
  </thead>
  <tbody>
  <#if userList?size == 0>
      <tr>
          <td colSpan="9" style="color: #FF0000; font-weight: bold; text-align: center; padding: 10px;">
              &nbsp;没有符合条件的用户信息！&nbsp;
          </td>
      </tr>
  </#if>
  
  <#list userList as user>
      <tr>
          <td align="center">
              <input type="checkbox" id="userId" name="userId" value="${user.userId}" /><br/>${user.userId}
          </td>
          <td align="center">
              <a href="${SiteUrl}go.action?loginName=${user.loginName!}" target="_blank"><span style="border:1px solid #ececec;"><img src="${SSOServerUrl +'upload/'+ (user.userIcon!'')}" onerror="this.src='${SiteUrl}images/default.gif'" width="50" border="0" title="${user.blogName!}" /></span></a>
          </td>
          <td>
          	<div style="padding:2px 0"><a href="${SiteUrl}go.action?loginName=${user.loginName!}" title="点击访问该工作室" target="_blank">${user.loginName!}</a></div>
          	<#if user.trueName??><div style="padding:2px 0">${user.trueName!?html}</div></#if>
          </td>      
          <td>
          <nobr>
            <#if (cfg_showEmail == "true")><#if user.email?? && user.email!=""><div style='padding:2px 0'><a href='mailto:${user.email?html}'>${user.email!?html}</a></div></#if></#if>
            <#if user.idCard?? && user.idCard!=""><div style='padding:2px 0'>身份证:${user.idCard!?html}</div></#if>
  				<#if user.qq?? && user.qq != "" ><div style='padding:2px 0'>QQ:<a href="tencent://Message/?Uin=${user.qq!?html}&websitName=ss&Menu=yes">${user.qq!?html}</a></div></#if>
            <#if user.createDate??><div style='padding:2px 0'>${user.createDate?string('yyyy-MM-dd HH:mm:ss')}</div></#if>
          </nobr>
          </td>
          <td>
          <nobr>
          <#assign usgList = Util.getSubjectGradeListByUserId(user.userId)>
			<#if usgList?? && (usgList?size> 0) >
			<#list usgList as usg>
			<#if usg.gradeId??>${Util.gradeById(usg.gradeId).gradeName!?html}<#else>未设置</#if>/<#if usg.subjectId??>${Util.subjectById(usg.subjectId).msubjName!?html}<#else>未设置</#if>
			<#if usg_has_next><br/></#if>
			</#list>
			</#if>
          </nobr>
        </td>
        <td>
        	<nobr><#if user.unitTitle??>${user.unitTitle?html}</#if></nobr>
        </td>
		<td align="center">
			<nobr>
				<#if user.positionId == 1><font style="color: #FF0000; font-weight: bold;">系统管理员</font>
				<#elseif user.positionId == 2><font style="color: #00FF00; font-weight: bold;">机构管理员</font>
				<#elseif user.positionId == 3><font style="color: #0000FF; font-weight: bold;">教师</font>
				<#elseif user.positionId == 4><font style="color: #00FFFF; font-weight: bold;">教育局职工</font>
				<#elseif user.positionId == 5><font style="color: #FF00FF; font-weight: bold;">学生</font>
				<#else>未知
				</#if>
			</nobr>
		</td>
		<td align="center">
			<nobr>
				<#if user.userType??>
				<#assign showTypeName = Util.typeIdToName(user.userType) >
    				<#if showTypeName??>
    				    <#list showTypeName?split("/") as x>
    				    <#if (x?length) &gt; 0 ><div style='padding:2px 0'>${x}</div></#if>
                        </#list> 
    				</#if>
				</#if>
			</nobr>
		</td>
	    <td align="center">
	    	<nobr>
	      	<#if user.userStatus == 0>正常
	            <#elseif user.userStatus == 1><font style="color: #FF0000; font-weight: bold;">待审核</font>
	            <#elseif user.userStatus == 3><font style="color: #FF0000; font-weight: bold;">已锁定</font>
	            <#elseif user.userStatus == 2><font style="color: #FF0000; font-weight: bold;">已删除</font>
	            <#else><font style="color: #FF0000; font-weight: bold;">未知状态</font>
	        </#if>
	        <#if platformType?? && platformType == '2'>
	        <br/>
	        <#if user.pushState??>
	        <#if user.pushState == 1>已推送</#if>
	        <#if user.pushState == 2><span style='color:#f00'>待推送</span></#if>
	        </#if>
	        </#if>
	     </nobr>
		</td>
		<td style="text-align: center">
		  <nobr>
	        <a href="?cmd=edit&userId=${user.userId}">修改</a>
<#if usermgr3??>
    <#if usermgr3==1>	        
	        <#if type == "deleted" || (user.userStatus == 2)>
	            <a href="crash_user.py?userId=${user.userId}" onclick="return confirm('确定要 彻底删除 当前用户吗？');">彻底删除</a>
	        <#else>
	            <a href="?cmd=delete&userId=${user.userId}" onclick="return confirm('确定要删除当前用户吗？');">删除</a>
	        </#if>
	</#if>
<#else>	  
            <#if type == "deleted" || (user.userStatus == 2)>
                <a href="crash_user.py?userId=${user.userId}" onclick="return confirm('确定要 彻底删除 当前用户吗？');">彻底删除</a>
            <#else>
                <a href="?cmd=delete&userId=${user.userId}" onclick="return confirm('确定要删除当前用户吗？');">删除</a>
            </#if>
</#if>      
	    </a>
	    </nobr>
		<#if ("0" != Util.isOpenMeetings())>
			<#if (1 == Util.isVideoUser(user.userId))>
				<br/><br/>
				<a href="${Util.isOpenMeetings()}&amp;user=${user.loginName!}" target="_blank"><font style="color: #FF00FF; font-weight: bold;">进入个人会议</font></a>
			</#if>
		</#if>
	  </td>
	</tr>
</#list>
</tbody>
</table>

<div class="pager">
  <#if pager??>
      <#include "../inc/pager.ftl">
  </#if>
</div>