 <#if meeting_list??>
 <#list meeting_list as meeting>
  <li<#if meeting_index % 2 == 1> class="liBg"</#if>>
      <span style="width:600px">&nbsp;
        <#assign bupload=0>
        <#if meeting.href??>
            <#if meeting.href.length() &gt; 0>
                <#assign bupload=1>
                    <a title="${meeting.subject!?html} 编号:${meeting.confKey!?html}" href="${ContextPath}sitevideomeeting.action?cmd=show&id=${meeting.id}">${meeting.subject!?html}</a>
            </#if>
        </#if>
        <#if bupload==0>
            ${meeting.subject!?html}
        </#if>
      </span>
      <span style="width:100px;text-align:center">${meeting.startTime?string("yyyy-MM-dd")}</span>
      <span style="width:100px;text-align:center">${meeting.endTime?string("yyyy-MM-dd")}</span>
      <span style="width:120px;text-align:center">
      <#if showType=="ready">
        <#if bManage>
            <a href="${SiteUrl}sitevideomeeting.action?cmd=startmeeting&id=${meeting.id}" target="_blank">启动</a>&nbsp;&nbsp;
            <a href='${SiteUrl}sitevideomeeting.action?cmd=modify&amp;Id=${meeting.id}' target='_blank'>管理</a>&nbsp;&nbsp;
            <a onclick='return confirm_delete()' href='${SiteUrl}sitevideomeeting.action?cmd=delete&amp;Id=${meeting.id}'>删除</a>
        <#else>
未启动     
        </#if>
      </#if>
      <#if showType=="running">
         <#if loginUser??>
            <#if meeting.hostName??>
                <#if loginUser.loginName == meeting.hostName>
                    <#if meeting.hostUrl??>
                        <#if meeting.hostUrl.length() &gt; 0>
                             <a href='${meeting.hostUrl!?html}' target="_blank">主持</a>&nbsp;&nbsp;
                        </#if>
                    </#if>
                 </#if>
             </#if>
            <#if meeting.attendeeUrl??>
                <#if meeting.attendeeUrl.length() &gt; 0>
                    <a href='${meeting.attendeeUrl!?html}' target="_blank">参会</a>
                </#if>
            </#if>
         </#if>
      </#if>
      <#if showType=="finish">
        <#if bManage>
            <a href='${SiteUrl}sitevideomeeting.action?cmd=modify&amp;Id=${meeting.id}' target='_blank'>管理</a>&nbsp;&nbsp;
            <a onclick='return confirm_delete()' href='${SiteUrl}sitevideomeeting.action?cmd=delete&amp;Id=${meeting.id}'>删除</a>
        <#else>
        已结束 
        </#if>        
      </#if>
      </span>
  </li>
 </#list>
 </#if>
 <div class="listPage clearfix" id="__pager">${HtmlPager}</div>