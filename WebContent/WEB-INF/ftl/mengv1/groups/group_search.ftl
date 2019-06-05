<div style="padding:10px;text-align:center;">
  <form method="get" action="groupList.action" style="margin:0">
  <!-- 选择分类 -->
  <#if group_cate??>
    <select name="categoryId" style="width:180px;">
      <option value=''>全部分类</option>
    <#list group_cate.all as c>
      <option value='${c.categoryId}'>${c.treeFlag2} ${c.name!?html}</option>
    </#list>
    </select>
  </#if>
 
    <input name="k" style="width:180px"/>
    <input type="submit" value="查找协作组" style="width:180px"/>
  </form>
</div>
