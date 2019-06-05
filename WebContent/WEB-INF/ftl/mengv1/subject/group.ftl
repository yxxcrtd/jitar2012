<div class='tab'>
  <div id="xzz_" class='tab2'>
    <label class='titlecolor'>协作组</label>            
    <div class="cur" onmouseover="TabUtil.changeTab('xzz_',0)"><a href='subjectGroups.py?type=new&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最新</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('xzz_',1)"><a href='subjectGroups.py?type=hot&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>最热</span></a></div>
    <div class="" onmouseover="TabUtil.changeTab('xzz_',2)"><a href='subjectGroups.py?type=rcmd&subjectId=${subject.metaSubject.msubjId}&gradeId=${gradeId!}'><span>推荐</span></a></div>
    <div class=""></div>
  </div>
  
  <div class='tab_content' style='overflow:hidden;height:220px;'>
    <!-- 最新协作组 -->
    <div id="xzz_0"  style="display: block">
  <#if new_group_list?? >
    <#list new_group_list as g >            
      <div>
      	<span class='border_img'>
        <a href='${SiteUrl}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}'  border='0' width='64' height='64' /></a></span>
        <div style='font-weight:bold;'><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></div>
        <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
        <div>简介：${g.groupIntroduce!}</div>
      </div>
      <#if g_has_next >
        <div style='height:4px;font-size:0;clear:both;'></div>
      </#if>
    </#list>
  </#if>
    </div>
    
    <!-- 热门协作组 -->
	  <div id="xzz_1"  style="display: none">
	<#if hot_group_list?? >
	  <#list hot_group_list as g >           
      <div>
      	<span class='border_img'>
        <a href='${SiteUrl}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' border='0' width='64' height='64' /></a></span>
        <div style='font-weight:bold;'><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></div>
        <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
        <div>简介：${g.groupIntroduce!}</div>
      </div>
      <#if g_has_next >
        <div style='height:4px;font-size:0;clear:both;'></div>
      </#if>
    </#list>
  </#if>
	  </div>
	  
    <!-- 推荐协作组 -->
	  <div id="xzz_2"  style="display: none">
  <#if rcmd_group_list?? >
    <#list rcmd_group_list as g >           
      <div>
      	<span class='border_img'>
      	<a href='${SiteUrl}go.action?groupId=${g.groupId}'><img src='${Util.url(g.groupIcon!'images/group_default.gif')}' border='0' width='64' height='64' /></a></span>
        <div style='font-weight:bold;'><a href='${SiteUrl}go.action?groupId=${g.groupId}'>${g.groupTitle!?html}</a></div>
        <div>创建时间：${g.createDate?string('yyyy-MM-dd')}</div>
        <div>简介：${g.groupIntroduce!}</div>
      </div>
      <#if g_has_next >
       	<div style='height:4px;font-size:0;clear:both;'></div>
      </#if>
    </#list>
  </#if>
	  </div>
	  
	  <div id="xzz_3"  style="display: none"> 
	  </div>
  </div>
</div>
