<div class='tab' style='min-height:180px'>
	<div id="tres_" class='tab2'>
		<label class='tab_label_1'><img src='${SiteUrl}theme/subject/${subject.themeName?default('theme1')}/j.gif' />&nbsp;${webpart.displayName!?html}</label>
		<div class="cur" onmouseover="TabUtil.changeTab('tres_',0)"><a href='${SubjectRootUrl}py/resource.py?subjectId=${subject.metaSubject.msubjId}&gradeId=${subject.metaGrade.gradeId}&amp;type=new&unitId=${unitId!}'>最新资源</a></div>
		<div class="" onmouseover="TabUtil.changeTab('tres_',1)"><a href='${SubjectRootUrl}py/resource.py?subjectId=${subject.metaSubject.msubjId}&gradeId=${subject.metaGrade.gradeId}&amp;type=hot&unitId=${unitId!}'>热门资源</a></div>
		<div class="" onmouseover="TabUtil.changeTab('tres_',2)"><a href='${SubjectRootUrl}py/resource.py?subjectId=${subject.metaSubject.msubjId}&gradeId=${subject.metaGrade.gradeId}&amp;type=rcmd&unitId=${unitId!}'>推荐资源</a></div>
		<div class="" style='font-size:0;'></div>
	</div>
  <div style='padding:4px;' class='tab_content'>
  <div id="tres_0" style="display: block;">
  <!-- 最新资源 -->
  <@res_item new_resource_list />
  </div>
  
  <div id="tres_1" style="display: none;">
  <!-- 热门资源 -->
  <@res_item hot_resource_list />
  </div>
  
  <div id="tres_2" style="display: none;">
    <!-- 推荐资源 -->
	<@res_item rcmd_resource_list />
   </div>
   <div id="tres_3" style="display: none;"></div>
 </div>
</div>
<#macro res_item rlist>
<#if rlist??>
  <table border="0" cellspacing='0' class='res_tab'>
  <thead>
    <tr style='font-weight:bold;'>
    <td style='width:100%;'>标题</td>
  	<td style='padding:0 10px;'><nobr>下载数</nobr></td>
  	<td style='padding:0 10px;'><nobr>上传者</nobr></td>
  	<td style='padding:0 10px;'><nobr>日期</nobr></td>
    </tr>
    </thead>
    <tbody>				
    <#list rlist as r>
    <tr>
      <td style='width:100%;'><a href='${SiteUrl}showResource.py?resourceId=${r.resourceId}'><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> ${Util.getCountedWords(r.title!?html,20)}</a></td>
      <td align='right' style='padding:0 10px;'><nobr>${r.downloadCount?default('&nbsp;')}</nobr></td>
      <td style='padding:0 10px;'><nobr><a href='${SiteUrl}go.action?loginName=${r.loginName}' target='_blank' title='${r.nickName!?html}'>${Util.getCountedWords(r.nickName!?html,4)}</a></nobr></td>
      <td style='padding:0 10px;'><nobr>${r.createDate?string('MM-dd')}</nobr></td>
    </tr>
    </#list>
  </tbody>
  </table>
</#if>
</#macro>