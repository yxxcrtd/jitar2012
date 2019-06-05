<ul>
  <li class="listContTitle">
      <div class="listContTitleBg">
          <span class="listWa1" style="width:420px">活动名称</span>
          <span class="listWa2">发起人</span>
          <span class="listWa3">报名截止日期</span>
          <span class="listWa4">活动类型</span>
      </div>
  </li>
<#if action_list?? && action_list?size &gt; 0 >
 <#list action_list as action>
  <li<#if action_index % 2 == 1> class="liBg"</#if>>
      <span class="listWa1" style="width:420px"><a title="${action.title}" href="${ContextPath}showAction.action?actionId=${action.actionId}">${Util.getCountedWords(action.title!?html,30,1)}</a></span>
      <span class="listWa2"><a title="${action.trueName}" href="${ContextPath}go.action?loginName=${action.loginName}">${Util.getCountedWords(action.trueName!?html,3,1)}</a></span>
      <span class="listWa3">${action.attendLimitDateTime?string("yyyy/MM/dd")}</span>
      <span class="listWa4">
      <#if action.ownerType == 'user' ><a target='_blank' href='${ContextPath}go.action?userId=${action.ownerId}'>个人</a>
        <#elseif action.ownerType == 'group' ><a target='_blank' href='${ContextPath}go.action?groupId=${action.ownerId}'>协作组</a>
        <#elseif action.ownerType == 'preparecourse' ><a target='_blank' href='${ContextPath}go.action?courseId=${action.ownerId}'>集备</a>
        <#elseif action.ownerType == 'subject' ><a target='_blank' href='${ContextPath}go.action?id=${action.ownerId}'>学科</a>
        <#else>未知
      </#if>
    </span>
  </li>
 </#list>
<#else>
<li>无数据返回。</li>
 </#if>
 </ul>
 <div class="listPage clearfix" id="__pager">${HtmlPager}</div>