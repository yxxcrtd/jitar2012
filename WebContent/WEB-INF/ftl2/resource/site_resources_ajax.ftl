<!-- 资源列表 -->
<ul>
  <li class="listContTitle">
      <div class="listContTitleBg">
          <span style="width:35%">标题</span>
          <span style="width:10%">学科</span>
          <span style="width:10%">年级</span>
          <span style="width:10%">类型</span>
          <span style="width:10%">大小</span>
          <span style="width:10%">上传者</span>
          <span style="width:15%">上传日期</span>
      </div>
  </li> 
<#if resource_list?? && resource_list?size &gt; 0 >
    <#list resource_list as r>
    <#if r_index % 2 = 1>
     <li class="liBg">
    <#else> 
     <li>
    </#if>
          <span style="width:35%"><!--<#if r.href??><#if r.href?length &gt; 0><img class="liR" src='${Util.iconCss(r.href)}' border='0' align='absmiddle' /></#if></#if>--><a title="${r.title!?html}" href='showResource.action?resourceId=${r.resourceId}'>${Util.getCountedWords(r.title!?html,14,1)}</a></span>
          <span style="width:10%;text-align:center">&nbsp;<#if r.subjectId??>${Util.getCountedWords(Util.subjectById(r.subjectId!).msubjName!?html,5)}</#if></span>
          <span style="width:10%;text-align:center">&nbsp;${Util.getCountedWords(r.gradeName!?html,3)}</span>
          <span style="width:10%;text-align:center">&nbsp;${Util.getCountedWords(r.scName!?html,5)}</span>
          <span style="width:10%;text-align:right">${Util.fsize(r.fsize!)}</span>
          <span style="width:10%;text-align:center"><a title="${r.nickName!}" href='${SiteUrl}go.action?loginName=${r.loginName}' target='_blank'>${Util.getCountedWords(r.nickName,3,1)}</a></span>
          <span style="width:15%">${r.createDate?string('yyyy/MM/dd')}</span>
    </li>
   </#list>
<#else>
<li>无数据返回。</li>
</#if>     
</ul>
<div class="listPage clearfix" id="__pager">${HtmlPager}</div>