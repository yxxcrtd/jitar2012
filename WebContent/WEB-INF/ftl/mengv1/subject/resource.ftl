<div class='tab'>
  <div id="res_" class="tab2">
    <label class='titlecolor'>学科资源</label>         
    <div class="cur" onmouseover="TabUtil.changeTab('res_',0)"><a href='subjectResources.py?type=new&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最新</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('res_',1)"><a href='subjectResources.py?type=hot&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最热</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('res_',2)"><a href='subjectResources.py?type=rcmd&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>推荐</span></a></div>
    <div class=""></div>
  </div>
  
  <div class='tab_content' style='overflow:hidden;height:220px;'>
    <!-- 学科最新资源 -->
    <div id="res_0"  style="display: block">
    <#if new_resource_list?? >
	  	<table border='0' width='100%' cellspacing='0'>
	      <#list new_resource_list as r>
	        <#assign u = Util.userById(r.userId) >
	        <tr>
	       	<td class='table_list line1'><nobr>${r.createDate?string('MM-dd')}</nobr></td>
	       	<td width='100%' class='line1'><a href='showResource.action?resourceId=${r.resourceId}' target='_blank'>
              <img src='${Util.iconImage(r.href)}' border='0' align='absmiddle' /> ${r.title!?html}</a></td>
	     	<td align='right' class='line1'><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!}</a></nobr></td>
	        </tr>
	      </#list>
	    </table>
    </#if>
    </div>
    
    <!-- 学科热门资源 -->
    <div id="res_1"  style="display: none">           
    <#if hot_resource_list?? >
	  	<table border='0' width='100%' cellspacing='0'>
	      <#list hot_resource_list as r>
	        <#assign u = Util.userById(r.userId) >
	        <tr>
	       	<td class='table_list line1'><nobr>${r.createDate?string('MM-dd')}</nobr></td>
	       	<td width='100%' class='line1'><a href='showResource.action?resourceId=${r.resourceId}' target='_blank'>
              <img src='${Util.iconImage(r.href)}' border='0' align='absmiddle' /> ${r.title!?html}</a></td>
	     	<td align='right' class='line1'><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!}</a></nobr></td>
	        </tr>
	      </#list>
	    </table>
    </#if>
    </div>
    
    <!-- 学科推荐资源 -->
	  <div id="res_2"  style="display: none">           
    <#if rcmd_resource_list?? >
	  	<table border='0' width='100%' cellspacing='0'>
	      <#list rcmd_resource_list as r>
	        <#assign u = Util.userById(r.userId) >
	        <tr>
	       	<td class='table_list line1'><nobr>${r.createDate?string('MM-dd')}</nobr></td>
	       	<td width='100%' class='line1'><a href='showResource.action?resourceId=${r.resourceId}' target='_blank'>
              <img src='${Util.iconImage(r.href)}' border='0' align='absmiddle' /> ${r.title!?html}</a></td>
	     	<td align='right' class='line1'><nobr><a href='${SiteUrl}go.action?loginName=${u.loginName}' target='_blank'>${u.nickName!}</a></nobr></td>
	        </tr>
	      </#list>
	    </table>
    </#if>
	  </div>
	  <div id="res_3"  style="display: none"> 
	  </div>
  </div>
</div>
