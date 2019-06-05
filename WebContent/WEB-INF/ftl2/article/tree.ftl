<#macro buildTree cate childs>
 <#if childs?? && childs?size &gt; 0>
 <#if cate.parentPath == "/"><ul class="leftNavS"><#else><ul></#if>
   <#list childs as child>
     <li><#if child.childCategoryList?? && child.childCategoryList?size &gt; 0><span class="leftNavIcon"></span></#if><span class="liFolder"></span><a href="javascript:void(0)" class="leftNavText" categoryId="${child.id}">${child.name}</a>
       <#if child.childCategoryList?? && child.childCategoryList?size &gt; 0>
         <@buildTree cate=child childs=child.childCategoryList />
       </#if>
     </li>
   </#list>
 </ul>
 </#if>
</#macro>

<!--左侧导航 Start-->
<div class="secLeftW border">
 <h3 class="h3Head textIn">文章分类</h3>
 <div class="leftNav clearfix">
 <#if trees?? >
   <#list trees as c >
   <h4 class="leftNavF"><#if c.childCategoryList?? && c.childCategoryList?size &gt; 0><span class="leftNavIcon"></span></#if><span class="folder"></span><a href="javascript:void(0)" categoryId="${c.id}">${c.name}</a></h4>    
   <#if c.childCategoryList?? && c.childCategoryList?size &gt; 0>
     <@buildTree cate=c childs=c.childCategoryList />
   </#if>
   </#list>
</#if>
   </div>
   <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<!--左侧导航 End-->