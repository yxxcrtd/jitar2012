
<!--搜索 Start-->
<div class="secRightW border">     
    <div class="secSearch">
        <b>关键字</b>
        <div class="secSearchBg secSearchBgW1">
            <span class="secSearchBgR"></span>
            <input id="k" name="k" type="text" class="secSearchInput" value="${k!''}" placeholder="关键词" />
        </div>
        <b>学段</b>
        <div class="secSearchBg secSearchBgW1">
            <span class="secSearchBgR"></span>
            <span class="arrow4 secSearchBgArrow"></span>
            <input type="text" gradeId="0" class="secSearchText" value="" placeholder="全部学段" id="secInput1"  readonly="readonly"/>
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
        <div class="secSearchBg secSearchBgW1">
            <span class="secSearchBgR"></span>
            <span class="arrow4 secSearchBgArrow"></span>
            <input type="text" subjectId="0" class="secSearchText" value="" placeholder="全部学科" id="secInput2"  readonly="readonly"/>
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
        <div class="secSearchBg secSearchBgW2">
            <span class="secSearchBgR"></span>
            <span class="arrow4 secSearchBgArrow"></span>
            <input categoryId="" type="text" class="secSearchText" value="" placeholder="全部分类" id="secInput3"  readonly="readonly"/>
            <div class="secSearchSelectWrap" id="secSelectWrap3">
                <ul class="secSearchSelect">
                    <li><a href="javascript:void(0);"  categoryId="0">全部分类</a></li>
                    <#if res_cate?? >
                      <#list res_cate.all as c >
                        <li><a href="javascript:void(0)" categoryId="${c.categoryId}">${c.treeFlag2 + c.name}</a></li>
                      </#list>
                    </#if>                    
                </ul>
            </div>
        </div>
        <b><a href="javascript:;" class="secSearchBtn">搜索</a></b>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
</div>
<script type="text/javascript">
function resetSearchData(k,gradeId,gradeName,subjectId,subjectName,categoryId,categoryName){
    //alert(k+","+gradeId+","+gradeName+","+subjectId+","+subjectName+","+categoryId+","+categoryName);
    $("#k").val(k);
    $("#secInput1").attr("gradeId",gradeId);
    $("#secInput1").val(gradeName);
    $("#secInput2").attr("subjectId",subjectId);
    $("#secInput2").val(subjectName);
    $("#secInput3").attr("categoryId",categoryId);
    $("#secInput3").val(categoryName);
}    
</script>
<!--搜索 End-->