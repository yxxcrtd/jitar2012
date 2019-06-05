<ul>
	<li class="listContTitle">
	    <div class="listContTitleBg">
	        <span class="listW1">课题</span>
	        <span class="listW2">授课人</span>
	        <span class="listW3">学科/学段</span>
	        <span class="listW4">授课时间</span>
	    </div>
	</li>
	<#if plan_list??>
	    <#list plan_list as c>
	          <#if (c_index%2)==0>
	             <li>
	                <span class="listW1"><a target="_blank" href="${SiteUrl}evaluations.action?cmd=content&evaluationPlanId=${c.evaluationPlanId}">${c.evaluationCaption!?html}</a></span>
	                <span class="listW2"><a target="_blank" href='${SiteUrl}evaluations.action?cmd=content&evaluationPlanId=${c.evaluationPlanId}'>${c.teacherName!?html}</a></span>
	                <span class="listW3">${c.msubjName!}/${c.gradeName!}</span>
	                <span class="listW4">${c.teachDate?string("yyyy-MM-dd")}</span>
	            </li>
	          <#else>
	              <li class="liBg">
	                <span class="listW1"><a target="_blank" href="${SiteUrl}evaluations.action?cmd=content&evaluationPlanId=${c.evaluationPlanId}">${c.evaluationCaption!?html}</a></span>
	                <span class="listW2"><a target="_blank" href='${SiteUrl}evaluations.action?cmd=content&evaluationPlanId=${c.evaluationPlanId}'>${c.teacherName!?html}</a></span>
	                <span class="listW3">${c.msubjName!}/${c.gradeName!}</span>
	                <span class="listW4">${c.teachDate?string("yyyy-MM-dd")}</span>
	               </li>
	          </#if>
	    </#list>
	</#if>
</ul>
<!--页码导航-->
<div id="listPage_clearfix" class="listPage clearfix">${HtmlPager}</div>
