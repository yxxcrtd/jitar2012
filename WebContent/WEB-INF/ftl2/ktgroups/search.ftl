<script>
$(function(){
  
  //搜索绑定 
  /*
  $(".secSearchBtn").bind("click",function(){ 
  k=$("#k").val();;
  gradeId=$("#secInput1").attr("gradeId");
  subjectId=$("#secInput2").attr("subjectId");
  searchtype=$("#secInput3").attr("searchtype");
  window.location.href="ktgroups.action?k=" + encodeURIComponent(k) + "&searchtype=" + searchtype + "&gradeId=" + gradeId + "&subjectId=" + subjectId;
  });
  */
});

    function searchSubmit(){
        $("#searchForm_subjectId").val($("#secInput2").attr("subjectId"));
        $("#searchForm_gradeId").val($("#secInput1").attr("gradeId"));
        $("#searchForm_searchtype").val($("#secInput3").attr("searchtype"));
        $("#searchForm").submit();
    }
</script>
<!--搜索 Start-->
<div class="secRightW border">     
    <div class="secSearch">
        <form method='POST' name="searchForm" style="display:inline" id="searchForm">
        <input type="hidden" id="searchForm_gradeId" name="gradeId" value="${gradeId!}"/>
        <input type="hidden" id="searchForm_subjectId" name="subjectId" value="${subjectId!}"/>
        <input type="hidden" id="searchForm_searchtype" name="searchtype" value="${searchtype!}"/>
        <b>关键字</b>
        <div class="secSearchBg secSearchBgW1">
            <span class="secSearchBgR"></span>
            <input type="text" id="k" name="k" class="secSearchInput" value="${k!?html}" placeholder="关键词" />
        </div>
        <b>学段</b>
        <#assign gradeName = "">
        <#if grade_list??>
            <#list grade_list as g>
                <#if g.gradeId==(gradeId!0)>
                    <#assign gradeName = g.gradeName>            
                    <#break>
                </#if>
            </#list>
        </#if>         
        <div class="secSearchBg secSearchBgW1">
            <span class="secSearchBgR"></span>
            <span class="arrow4 secSearchBgArrow"></span>
            <input type="text" gradeId="${gradeId!0}" class="secSearchText" value="${gradeName}" placeholder="全部学段" id="secInput1"  readonly="readonly"/>
            <div class="secSearchSelectWrap" id="secSelectWrap1">
              <ul class="secSearchSelect">
                <li value="0"><a href="javascript:void(0);">全部学段</a></li>
                <#if grade_list??>
                <#list grade_list as g>
                  <li value="${g.gradeId}"><a href="javascript:void(0);">${g.isGrade?string(g.gradeName!?html, '&nbsp;&nbsp;' + g.gradeName!?html)}</a></li>
                </#list>
                </#if>
              </ul>
            </div>
        </div>
        <b>学科</b>

      <#assign subjectName = "">
        <#if subject_list??>
            <#list subject_list as s>
                <#if s.msubjId==(subjectId!0)>
                    <#assign subjectName = s.msubjName>            
                    <#break>
                </#if>
            </#list>
        </#if>                           
        <div class="secSearchBg secSearchBgW1">
            <span class="secSearchBgR"></span>
            <span class="arrow4 secSearchBgArrow"></span>
            <input type="text" subjectId="${subjectId!0}" class="secSearchText" value="${subjectName}" placeholder="全部学科" id="secInput2"  readonly="readonly"/>
            <div class="secSearchSelectWrap" id="secSelectWrap2">
              <ul class="secSearchSelect">
              <li value="0"><a href="javascript:void(0);">全部学科</a></li>
              <#if subject_list??>
                <#list subject_list as s>
                  <li value="${s.msubjId}"><a href="javascript:void(0);">${s.msubjName!}</a></li>
                </#list>
              </#if>
              </ul>
            </div>
        </div>
        <b>分类</b>
        <#assign searchtypeName = "">
        <#if searchtype??>
            <#if searchtype == "ktname">
                <#assign searchtypeName = "课题名称">
            <#else>
                <#if searchtype == "ktperson">
                    <#assign searchtypeName = "负责人">
                </#if>
            </#if>
        </#if>
        <div class="secSearchBg secSearchBgW2">
            <span class="secSearchBgR"></span>
            <span class="arrow4 secSearchBgArrow"></span>
            <input type="text" searchtype="${searchtype!}" class="secSearchText" value="${searchtypeName}" placeholder="搜索类型" id="secInput3" readonly="readonly" />
            <div class="secSearchSelectWrap" id="secSelectWrap3">
                <ul class="secSearchSelect">
                    <li searchtype="ktname"><a href="javascript:void(0);">课题名称</a></li>
                    <li searchtype="ktperson"><a href="javascript:void(0);">负责人</a></li>
                </ul>
            </div>
        </div>
        <b><a href="javascript:;" class="secSearchBtn" onclick="searchSubmit();">搜索</a></b>
        </form>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
</div>
<!--搜索 End-->