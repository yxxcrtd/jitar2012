<ul>
<li class="listContTitle">
  <div class="listContTitleBg">
      <span class="listWr1" style="width:330px">备课名称</span>
      <span class="listWr2" style="width:60px">主备人</span>
      <span class="listWr3  style="width:80px">开始时间</span>
      <span class="listWr4" style="width:80px">结束时间</span>
      <span class="listWr5" style="width:40px">个案数</span>
      <span class="listWr6" style="width:80px">共案编辑数</span>
  </div>
</li>
<#if course_list?? && course_list?size &gt; 0 >
 <#list course_list as pc>
  <li<#if pc_index % 2 == 1> class="liBg"</#if>>
      <span class="listWr1" style="width:330px"><a href="${ContextPath}p/${pc.prepareCourseId}/0/"<#if pc.title?length &gt; 24> title="${pc.title!?html}"</#if>>${Util.getCountedWords(pc.title!?html,24)}</a></span>
      <#assign u = Util.userById(pc.leaderId)>
      <span class="listWr2" style="width:60px"><#if u??><a href='${ContextPath}go.action?loginName=${u.loginName}' <#if u.trueName?length &gt; 3> title="${u.trueName!?html}"</#if>>${Util.getCountedWords(u.trueName!?html,3)}</a></#if></span>
      <span class="listWr3" style="width:80px">${pc.startDate?string('yyyy-MM-dd')}</span>
      <span class="listWr4" style="width:80px">${pc.endDate?string('yyyy-MM-dd')}</span>
      <span class="listWr5" style="width:40px">${privateCountList[pc_index]}</span>
      <span class="listWr6" style="width:80px">${editCountList[pc_index]}</span>
  </li>
 </#list>
 <#else>
<li>无数据返回。</li>
 </#if>
</ul>
 <div class="listPage clearfix" id="__pager">${HtmlPager!}</div>