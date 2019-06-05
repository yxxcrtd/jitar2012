<div class='orange_border bg1' style='padding:4px;'>
<form action='' method='get' style='text-align:center;'>
<#if (subject??)>
  <input type='hidden' name='subjectId' value='${subject.metaSubject.msubjId!}' />
<#else>
  <input type='hidden' name='subjectId' value='' />
</#if>  
  <input type='hidden' name='gradeId' value='${gradeId!}' />
  <input type='hidden' name='type' value="${type!?html}" />
  关键字：<input class='s_input2' name='k' />   
<#if res_cates?? >
  资源类型：<select name='categoryId'>
      <option value=''>所有类型</option>
    <#list res_cates.all as c >
      <#if categoryId?? && categoryId == c.categoryId >
        <option value='${c.categoryId}' selected='selected'>${c.treeFlag2 + c.name}</option>
      <#else>
      <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
      </#if>
    </#list>
    </select>
</#if>
  <input type='image' src='${SiteThemeUrl}b_s.gif' align='absmiddle' />
</form>
</div>
