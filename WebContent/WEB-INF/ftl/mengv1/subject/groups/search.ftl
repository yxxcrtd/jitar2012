<div>
  <div class='q_t_l'>   
  <form method='get' style='text-align:center;'>
  <table align='center' border='0'>
  <tr>
  <td>
  <nobr>
  <#if subject??>
    <input type='hidden' name='subjectId' value='${subject.metaSubject.msubjId}' />
  <#else>
    <input type='hidden' name='subjectId' value='' />
  </#if>  
    <input type='hidden' name='gradeId' value='${gradeId}' /> 
     关键字：<input class='s_input' name='k' style='width:auto;' />
  <#if group_cates?? >
    分类：<select name='categoryId'>
      <option value=''>选择分类</option>
    <#list group_cates.all as c >
      <option value='${c.categoryId}'>${c.treeFlag2 + c.name}</option>
    </#list>
    </select>        
  </#if>
   </nobr>
  </td>
  <td><input type='image' src='${SiteThemeUrl}b_s.gif' /></td>
   </tr>
   </table>
  </form>
  </div>
  <div class='q_t_r'>
    <a href='manage/group.action?cmd=create'><img src='${SiteThemeUrl}cq.jpg' border='0' /></a>
  </div>
</div>
