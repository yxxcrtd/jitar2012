        <div class="secRightW border">     
            <div class="secSearch">
                <form method='POST' name="profile_form" style="display:inline" id="searchForm" action="${ContextPath}groups.action">
                <input type="hidden" id="searchForm_categoryId" name="categoryId" value="${categoryId!}"/>
                <input type="hidden" id="searchForm_gradeId" name="gradeId" value="${gradeId!}"/>
                <input type="hidden" id="searchForm_subjectId" name="subjectId" value="${subjectId!}"/>                                                
                <input type="hidden" name="type" value="${type!}"/>
                <b>关键字</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <input type="text" name="k" id="k" class="secSearchInput" value="${k!}" placeholder="关键词" />
                </div>
                <b>学段</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <#assign gradeName = "">
                    <#if grade_list??>
                        <#list grade_list as g>
                            <#if g.gradeId==(gradeId!0)>
                                <#assign gradeName = g.gradeName>            
                                <#break>
                            </#if>
                        </#list>
                    </#if>                            
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
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <#assign subjectName = "">
                    
                    <#if subject_list??>
                        <#list subject_list as s>
                            <#if s.msubjId==(subjectId!0)>
                                <#assign subjectName = s.msubjName>            
                                <#break>
                            </#if>
                        </#list>
                    </#if>
                    
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
                <div class="secSearchBg secSearchBgW2">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <#assign cateName = "">
                    <#if group_cate?? >
                      <#list group_cate.all as c >
                       <#if categoryId??>
                        <#if c.categoryId==categoryId>
                            <#assign cateName = c.name>            
                            <#break>
                        </#if>
                       </#if>
                      </#list>
                    </#if>                         
                    <input type="text" categoryId="${categoryId!0}" class="secSearchText" value="${cateName}" placeholder="所有分类" id="secInput3"  readonly="readonly"/>
                    <div class="secSearchSelectWrap" id="secSelectWrap3">
                        <ul class="secSearchSelect">
                        <li value="0"><a href="javascript:void(0)">所有分类</a></li>
                    <#if group_cate?? >
                      <#list group_cate.all as c >
                       <#if categoryId??>
                        <#if c.categoryId==categoryId>
                            <li value="${c.categoryId}"><a href="javascript:void(0)">${c.treeFlag2 + c.name}</a></li>
                        <#else>
                            <li value="${c.categoryId}"><a href="javascript:void(0)">${c.treeFlag2 + c.name}</a></li>
                        </#if>
                       <#else>
                            <li value="${c.categoryId}"><a href="javascript:void(0)">${c.treeFlag2 + c.name}</a></li>
                       </#if> 
                      </#list>
                    </#if>                        
                        </ul>
                    </div>
                </div>
                <b><a href="javascript:void(0);" class="secSearchBtn" onclick="searchSubmit();">搜索</a></b>
                </form>
            </div>
            <div class="imgShadow"><img src="skin/default/images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
<script>
    function searchSubmit(){
        $("#searchForm_subjectId").val($("#secInput2").attr("subjectId"));
        $("#searchForm_gradeId").val($("#secInput1").attr("gradeId"));
        $("#searchForm_categoryId").val($("#secInput3").attr("categoryId"));
        $("#searchForm").submit();
    }
</script>        