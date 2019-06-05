<#function ShowAccessControlLevel accessControlLevel>
<#if accessControlLevel == 1000>
 <#return "系统管理员">
<#elseif accessControlLevel == 900>
 <#return "系统用户管理员">
<#elseif accessControlLevel == 901>
 <#return "系统内容管理员">
<#elseif accessControlLevel == 100>
 <#return "机构系统管理员">
<#elseif accessControlLevel == 101>
 <#return "机构用户管理员">
<#elseif accessControlLevel == 102>
 <#return "机构内容管理员">
<#elseif accessControlLevel == 200>
 <#return "学科系统管理员">
<#elseif accessControlLevel == 201>
 <#return "学科用户管理员">
<#elseif accessControlLevel == 202>
 <#return "学科内容管理员">
<#elseif accessControlLevel == 300>
 <#return "元学科内容管理员">
<#else>
 <#return "未定义的管理员">
</#if>
</#function>