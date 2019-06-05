<div style="padding:5px;padding-top:0;background:#b3c3f8">
  <div><img src="images/group/g1.jpg" /></div>
  <div style="background:#FFF;border:solid 1px #758acf;padding:2px;text-align:left;overflow:auto;height:auto;">
  
  <script type="text/javascript">
  d = new dTree("d");
  d.add(0,-1,"<b>全部协作组分类</b>","");
<#if group_cate??>
   <#list group_cate.root as c>
   <#if c.parentId??>
    d.add(${c.id},${c.parentId},"${c.name}","showCategory.action?categoryId=${c.id}");
    <#else>
    d.add(${c.id},0,"${c.name}","showCategory.action?categoryId=${c.id}");
    </#if>
    </#list>
 </#if>
  document.write(d);
  d.openAll();
</script>
 
  </div>
</div>
