<!--搜索 Start-->
      <div class="secRightW border">     
            <div class="secSearch">
                <b>课题</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <input id="evaluKey" type="text" class="secSearchInput" value="" placeholder="关键词" />
                </div>
                <b>授课人</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <input id="evalpass" type="text" class="secSearchInput" value="" placeholder="授课人" />
                </div>
                <b>学段</b>
                <div class="secSearchBg secSearchBgW1">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <input type="text" class="secSearchText" value="" placeholder="全部学段" id="secInput2" />
                    <#if grade_list??>
                    <div class="secSearchSelectWrap" id="secSelectWrap2">
                        <ul class="secSearchSelect">
                            <li value='0'><a href="javascript:void(0);">全部学段</a></li>
					         <#list grade_list as grade > 
					           <li value='${grade.gradeId!}'><a href="javascript:void(0);">${grade.isGrade?string(grade.gradeName!?html, '&nbsp;&nbsp;' + grade.gradeName!?html) }</a></li>
					         </#list>
                        </ul>
                    </div>
                   </#if>
                </div>
                <b>学科</b>
                <div class="secSearchBg secSearchBgW2">
                    <span class="secSearchBgR"></span>
                    <span class="arrow4 secSearchBgArrow"></span>
                    <input type="text" class="secSearchText" value="" placeholder="全部学科" id="secInput3" />
                    <#if subject_list?? >
	                    <div class="secSearchSelectWrap" id="secSelectWrap3">
	                        <ul class="secSearchSelect">
	                        <li value="0"><a href="javascript:void(0);">全部学科</a></li>
	                          <#list subject_list as subj > 
	                            <li value="${subj.msubjId!}"><a href="javascript:void(0);">${subj.msubjName}</a></li>
	                          </#list>
	                        </ul>
	                    </div>
                    </#if>
                </div>
                <!--onclick="searchEval('${type}',${SiteUrl})"-->
                <b><a id = "evalSearch"  href="javascript:void(0);" class="secSearchBtn">搜索</a></b>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize6" /></div>
        </div>
        
        <!--
        <script>
			 $(".secSearchBtn").bind("click",function(){ 
				  var k=$("#evaluKey").val();
				  var evalpass = $('#evalpass').val();
				  var gradeId='undefined'!=typeof($("#secInput2").attr("gradeId"))?$("#secInput2").attr("gradeId"):-1;
				  var subjectId='undefined'!=typeof($("#secInput3").attr("subjectId"))?$("#secInput3").attr("subjectId"):-1;
				  var url='evaluations.action?type=search&k='+k+'&kperson='+evalpass+'&gradeId='+gradeId+'&subjectId='+subjectId+'';
				  window.location.href=url;
			  }).bind('mouseover',function(){
			     $(this).css('cursor','pointer');
			  });
	   <script>-->
	   
       <!--搜索 End-->