<#if blog_cates?? >
<div class='orange_border'>
  <div class='tree'>
  <#if !(type??)><#assign type="new" ></#if>
    <script type="text/javascript">
    d = new dTree("d");
    d.add(0,-1,"<b>文章分类</b>","subjectArticles.py?subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&type=${type}");
    <#list blog_cates.all as c >
      <#if c.parentId??>
      	  d.add(${c.id},${c.parentId},"${c.name}","subjectArticles.py?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}&categoryId=${c.categoryId}&type=${type}");	
      <#else>
     	  d.add(${c.id},0,"${c.name}","subjectArticles.py?subjectId=${subject.metaSubject.msubjId!}&gradeId=${gradeId!}&categoryId=${c.categoryId}&type=${type}");
      </#if>
      
    </#list>
    document.write(d);
    d.openAll();
  </script>

  </div>
</div>
</#if>