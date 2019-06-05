<#macro group_state state>
	<#if state == 0><span>正常</span>
	<#elseif state == 1><font color='red'>未审核</font>
	<#elseif state == 2><font color='red'>锁定</font>
	<#elseif state == 3><font color='red'>已删除</font>
	<#elseif state == 4><font color='gray'>隐藏</font>
	<#else><font color='red'>未知</font>
	</#if>
</#macro>

<#macro member_state state>
  <#if state == 0><span>正常</span>
  <#elseif state == 1><font color='red'>待审核</font>
  <#elseif state == 2><font color='red'>已删除</font>
  <#elseif state == 3><font color='red'>被锁定</font>
  <#elseif state == 4><font color='green'>邀请加入</font>
  <#else><font color='red'>未知</font>
  </#if>
</#macro>

<#macro member_role role>
  <#if role == 1000><font color='blue'>管理员</font>
  <#elseif role == 800><font color='blue'>副管理员</font>
  <#elseif role == 500><font color='green'>资深组员</font>
  <#elseif role == 0><span>组员</span>
  <#else><span>未知(${role})</span>
  </#if>
</#macro>

<#macro list_tag tags>
  <#list Util.tagToList(tags!) as t>
    <a href='${SiteUrl}showTag.action?tagName=${t!?url("UTF-8")}' target='_blank'>${t!?html}</a><#if t_has_next>，</#if>
   </#list>
</#macro>
