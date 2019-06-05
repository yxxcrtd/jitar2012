<div class='tab_outer' style='min-height:226px;'>
  <div id="tres_" class='tab'>
	<label class='tab_label_1'><img src='${SiteUrl}theme/units/${unit.themeName?default('theme1')}/j.gif' />&nbsp;${webpart.displayName}</label>
	<div class="cur" onmouseover="TabUtil.changeTab('tres_',0)"><a href='${UnitRootUrl}resource/unit_resource.py?unitId=${unit.unitId}&amp;type=new'>最新资源</a></div>
	<div class="" onmouseover="TabUtil.changeTab('tres_',1)"><a href='${UnitRootUrl}resource/unit_resource.py?unitId=${unit.unitId}&amp;type=hot'>最热资源</a></div>
	<div class="" onmouseover="TabUtil.changeTab('tres_',2)"><a href='${UnitRootUrl}resource/unit_resource.py?unitId=${unit.unitId}&amp;type=rcmd'>推荐资源</a></div>
	<div class="" style='font-size:0;'></div>
  </div>
  <div style='padding:4px;'>
  <div id="tres_0" class="tres" style="display: block;">
  <!-- 最新资源 -->
  <@res_item new_resource_list />  
  </div>
  
  <div id="tres_1" class="tres" style="display: none;">
  <!-- 热门资源 -->
  <@res_item hot_resource_list />
  </div>
  
  <div id="tres_2" class="tres" style="display: none;">
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
  	<td>标题</td>
  	<td style='padding:0 10px;'><nobr>学科</nobr></td>
  	<td style='padding:0 10px;'><nobr>下载数</nobr></td>
  	<td style='padding:0 10px;'><nobr>上传者</nobr></td>
  	<td style='padding:0 10px;'><nobr>日期</nobr></td>
  	</tr>
  	</thead>
  	<tbody>				
  <#list rlist as r>
  <#assign u = Util.userById(r.userId)>
    <tr>
  	<td style='width:100%;'><a href='${SiteUrl}showResource.action?resourceId=${r.resourceId}'><img src='${Util.iconImage(r.href!)}' border='0' align='absmiddle' /> ${Util.getCountedWords(r.title!?html,16)}</a></td>
  	<td style='padding:0 10px;'><nobr><#if r.subjectId??>${Util.subjectById(r.subjectId!).msubjName!?html}<#else>&nbsp;</#if></nobr></td>
  	<td align='right' style='padding:0 10px;'><nobr>${r.downloadCount?default('&nbsp;')}</nobr></td>
  	<td style='padding:0 10px;'><nobr><a href='${SiteUrl}go.action?userId=${r.userId}' target='_blank'>${u.nickName!?html}</a></nobr></td>
  	<td style='padding:0 10px;'><nobr>${r.createDate?string('MM-dd')}</nobr></td>
  	</tr>
  </#list>
  	</tbody>
  </table>     
</#if>
</#macro>