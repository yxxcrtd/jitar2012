<#if usercate_tree??>
<select id="cate_${wid}" style="width:100%">
<option value="">请选择一个个人视频分类</option>
<#list usercate_tree.all as c> 
 <option value="${c.id}"<#if oldCateId == c.id > selected='selected'</#if>><#if c.treeFlag2??>${c.treeFlag2 + c.name?html}<#else>${c.name?html}</#if></option>
</#list>
</select>
<div style="text-align:left">显示条数：<input id="count_${wid}" style="width:50%" value="${count!}" /></div>
<div style="text-align:left">模块标题：<input id="title_${wid}" style="width:50%" value="${title!?html}" /></div>
<#else>
请先创建个人视频分类
</#if>