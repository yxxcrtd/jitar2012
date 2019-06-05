<div>
  <!-- 左边导航，样式参见 http://192.168.3.6:8080/Groups/resources.py?categoryId=51&type=new  -->
  <div class='top_outer_div'>
    <div class='top_inner_div'></div>
  </div>
  <div class='outer_div'>
    <#if categoryId?? >
    	<#if (subject??)>
      	<div class='inner_div'><a href='?subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&type=${type!}'>所有类型</a></div>
      	<#else>
      	<div class='inner_div'><a href='?gradeId=${gradeId!}&type=${type!}'>所有类型</a></div>
      	</#if>
    <#else>     
      <div class='current_outer_outer_div'>
        <div class='current_outer_div'>
        	<#if (subject??)>
          		<div class='current_inner_div'><a href='?subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&type=${type!}'>所有类型</a></div>
          	<#else>
          		<div class='current_inner_div'><a href='?gradeId=${gradeId!}&type=${type!}'>所有类型</a></div>
          	</#if>	
        </div>      
      </div>
    </#if>
  </div>  
    
  <#if res_cates?? >
    <#list res_cates.root as c >
      <#if categoryId?? && categoryId == c.categoryId>
      <div class='current_outer_outer_div'>
        <div class='current_outer_div'>
        	<#if (subject??)>
          		<div class='current_inner_div'><a href='?subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&categoryId=${c.categoryId}&amp;type=${type!}'>${c.name!?html}</a></div>
          	<#else>
          		<div class='current_inner_div'><a href='?gradeId=${gradeId!}&categoryId=${c.categoryId}&amp;type=${type!}'>${c.name!?html}</a></div>
          	</#if>	
        </div>      
      </div>
      <#else >
      <div class='outer_div'>
      	<#if (subject??)>
        <div class='inner_div'><a href='?subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}&categoryId=${c.categoryId}&amp;type=${type!}'>${c.name}</a></div>
        <#else>
        <div class='inner_div'><a href='?gradeId=${gradeId!}&categoryId=${c.categoryId}&amp;type=${type!}'>${c.name}</a></div>
        </#if>     
      </div>
      </#if>
    </#list>
  </#if>
  <div class='bottom_outer_div'>
    <div class='bottom_inner_div'></div>
  </div>
</div>
