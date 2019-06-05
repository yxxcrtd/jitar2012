<!--活跃机构 Start-->
<div class="rightWidth agencyWrap border mt3">
    <h3 class="h3Head textIn">
	        周活跃机构
    </h3>
      <div class="agency">
      
      <#if school_list?? >
      <#assign sch = 0>
          <ul class="ulList">
             <#list school_list as sch>
              <#if (3 &gt; sch_index)>
                  <li><span class="numIcon numRed">${sch_index + 1}</span><a href="go.action?unitName=${sch.unitName}" title="${sch.unitTitle!}">${Util.getCountedWords(sch.unitTitle!?html,14)}</a></li>
                <#else> 
                  <li><span class="numIcon">${sch_index + 1}</span><a href="go.action?unitName=${sch.unitName}" title="${sch.unitTitle!}">${Util.getCountedWords(sch.unitTitle!?html,14)}</a></li>
                </#if>
               </#list>
          </ul>
    </#if>
      </div>
    <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
</div>
<!--活跃机构 End-->