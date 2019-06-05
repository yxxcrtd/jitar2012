<div class='blog_search'>
  <form method='get' id='sForm'>
  <input type='hidden' name='categoryId' value='' />    
  <input type='hidden' name='gradeId' value='${gradeId!}' />
  <table align='center' border='0'>
  <tr>
  <td>
<input type='hidden' name='subjectId' value='${subject.metaSubject.msubjId}' />
关键字：<input class='keyword' name='k' value='' /> 
</td><td>
<div class='select_head' id='category_head' onclick='SelectUtil.showDropdown(this,"category",event)' style='width:100px;'>选择工作室分类</div>
<div id='category' class='select_drop'>
<#if blog_cates?? >
      <div class='select_option_blur' onclick='SelectUtil.clickOption(this,"category")' value='' onmouseover='this.className="select_option_hightlight"' onmouseout='this.className="select_option_blur"'><nobr>选择工作室分类</nobr></div>
      <#list blog_cates.all as c >
        <div class='select_option_blur' onclick='SelectUtil.clickOption(this,"category")' value='${c.categoryId}' onmouseover='this.className="select_option_hightlight"' onmouseout='this.className="select_option_blur"'><nobr>${c.treeFlag2 + c.name}</nobr></div>
      </#list>
    </#if>
</div>  
    
    </td><td>
    <input type='image' src='${SiteThemeUrl}b_s.gif' />
    </td>
    </tr>
    </table>
    </form>
</div>
