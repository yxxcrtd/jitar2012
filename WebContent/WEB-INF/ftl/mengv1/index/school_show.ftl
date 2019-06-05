<!--活跃机构 Start-->
        <div class="rightWidth agencyWrap border mt3">
            <h3 class="h3Head textIn">
               <!--<a href="javascript:;" class="more">更多</a>-->
       		         活跃机构
            </h3>
            <#if school_link?? >
	            <div class="agency">
	            <#assign sch = 0>
	                <ul class="ulList">
	                   <#list school_link as sch>
	                    <#if (3 > sch_index)>
                          <li><span class="numIcon numRed">${sch_index + 1}</span><a href="go.action?unitName=${sch.unitName}">${sch.unitTitle!?html}</a></li>
                        <#else> 
                          <li><span class="numIcon">${sch_index + 1}</span><a href="go.action?unitName=${sch.unitName}">${Util.getCountedWords(sch.unitTitle!?html,14)}</a></li>
                        </#if>
                       </#list>
	                </ul>
	            </div>
            </#if>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
        </div>
<!--活跃机构 End-->