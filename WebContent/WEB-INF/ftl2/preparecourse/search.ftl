<!--搜索 Start-->
<div class="secRightW border">     
    <div class="secSearch">
        <b>关键字</b>
        <div class="secSearchBg secSearchBgW1" style="width:250px;">
            <span class="secSearchBgR"></span>
            <input id="k" type="text" class="secSearchInput" value="" placeholder="关键词" style="width:240px;" />
        </div>
        <b>学段</b>
        <div class="secSearchBg secSearchBgW1">
            <span class="secSearchBgR"></span>
            <span class="arrow4 secSearchBgArrow"></span>
            <input type="text" gradeId="0" class="secSearchText" value="" placeholder="全部学段" id="secInput1" readonly="readonly" />
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
            <input type="text" subjectId="0" class="secSearchText" value="" placeholder="所有学科" id="secInput2" readonly="readonly" />
            <div class="secSearchSelectWrap" id="secSelectWrap2">
             <ul class="secSearchSelect">             
              <#if subject_list??>
                <#list subject_list as s>
                  <li value="${s.msubjId}"><a href="javascript:void(0);">${s.msubjName!}</a></li>
                </#list>
              </#if>
             </ul>
            </div>
        </div>
        
        <b><a href="javascript:void(0);" class="secSearchBtn">搜索</a></b>
    </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
</div>
<!--搜索 End-->
