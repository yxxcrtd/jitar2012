<!--左侧导航 Start-->
<div class="secLeftW border">
 <h3 class="h3Head textIn">资源分类</h3>
 <div class="leftNav clearfix">${outHtml}</div>
 <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize5" /></div>
</div>
<script>
$(document).ready(function(){
$(".leftNav a").each(function(){
$(this).bind("click",function(){
 //alert("metaSubjectId=" + $(this).attr("metaSubjectId") + ",metaGradeId=" + $(this).attr("metaGradeId"));
 //document.location.href="resources.action?type=${type}&subjectId=" + $(this).attr("metaSubjectId") + "&gradeId=" + $(this).attr("metaGradeId");
 var subId = $(this).attr("metaSubjectId");
 var subName = $(this).attr("metaSubjectName");
 var gradeId = $(this).attr("metaGradeId");
 var gradeName = $(this).attr("metaGradeName");
 getResourceList(subId,gradeId); 
 resetSearchData("",gradeId,gradeName,subId,subName,"","");
})
})
});
</script>
<!--左侧导航 End-->