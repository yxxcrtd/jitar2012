<div class="specialWrap clearfix">
    <div class="special border">
        <h3 class="h3Head textIn"><a href="morespecialSubject.action" class="more">更多</a>教研专题</h3>
        
          <div class="specialCont">
          <#if special_subject_list?? >
              <ul class="ulList">
                 <#list special_subject_list as sl>
                  <li><em class="emDate">${sl.createDate?string('MM/dd')}</em><a href="specialSubject.action?specialSubjectId=${sl.specialSubjectId}" title="${sl.title!}">${Util.getCountedWords(sl.title!html,14)}</a></li> 
                 </#list> 
              </ul>   
        </#if> 
          </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize3" /></div>
    </div>
    
    <div class="newClass border">
        <h3 class="h3Head textIn"><a href="prepareCourse.action" class="more">更多</a>集体备课</h3>
        <div class="newClassCont">
        <#if course_list??>
            <ul class="ulList">
               <#list course_list as c>		
                <li><em class="emDate">${c.createDate?string('MM/dd')}</em><a href="p/${c.prepareCourseId}/0/" title="${c.title!}">${Util.getCountedWords(c.title!html,14)}</a></li>
               </#list> 
            </ul>    
       </#if>
        </div>
        <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize4" /></div>
    </div>
</div>