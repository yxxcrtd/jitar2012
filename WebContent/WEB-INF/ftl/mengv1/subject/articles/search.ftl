<div class='orange_border' style='padding:4px;'>
<form action='' method='get' style='text-align:center;'>
  <input type='hidden' name='subjectId' value='${subject.metaSubject.msubjId}' />
  <input type='hidden' name='gradeId' value='${gradeId!}' />
  关键字：<input class='s_input2' name='k' value="${k!?html}" />
  选择类型：<select name='type'>
            <#if !(type??)><#assign type="new" ></#if>
            <option value='new'${(type == 'new')?string(' selected="selected"','')}>最新发布</option>
            <option value='rcmd'${(type == 'rcmd')?string(' selected="selected"','')}>编辑推荐</option>
            <option value='hot'${(type == 'hot')?string(' selected="selected"','')}>最高人气</option>
            <option value='cmt'${(type == 'cmt')?string(' selected="selected"','')}>评论最多</option>
          </select> 
<#if blog_cates?? >
  选择分类：<select name='categoryId'>
            <option value=''>所有分类</option>          
          <#list blog_cates.all as c >
            <#if categoryId?? >
	            <#if c.categoryId == categoryId >
	               <option value='${c.categoryId}' selected='selected'>${c.treeFlag2 + c.name}</option>
	            <#else>
	               <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
	            </#if>
            <#else>
                <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
            </#if>
            
          </#list>
          </select>
</#if>
  <input type='image' src='${SiteThemeUrl}b_s.gif' align='absmiddle' />
</form>
</div>
