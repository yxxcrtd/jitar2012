<rss version="2.0">
  <channel>
      <#if (showType??) && (topic_list[0]??) >
        <title>${topic_list[0].nickName} - 协作组话题</title>
      <#else>
        <title>中教启星网络教研平台 - 协作组话题</title>
      </#if>    
    <link>${SiteUrl}</link>
    <description>中教启星网络教研平台</description>
    <language>zh-CN</language>
    <copyright>Copyright 1999-2016 北京中教启星科技股份有限公司</copyright>
    <managingEditor></managingEditor>
    <webMaster></webMaster>
    <pubDate>${today?string}</pubDate>
    <lastBuildDate>${today?string}</lastBuildDate>
    <category>协作组</category>
    <docs>${SiteUrl}rss.py?type=topic</docs>
    <image>
      <title>中教启星网络教研平台</title>
      <width>144</width>
      <height>35</height>
      <link>${SiteUrl}</link>
      <#if (showType??) && (topic_list[0]??) && (topic_list[0].userIcon??) >
        <url>${topic_list[0].userIcon}</url>
      <#else>
        <url>${SiteUrl}images/index/ci.gif</url>
      </#if>
    </image>
   <#list topic_list as t>
    <item>
      <link>${SiteUrl}manage/groupBbs.action?cmd=reply_list&amp;groupId=${t.groupId}&amp;topicId=${t.topicId}</link>
      <title>${t.title!?xml}</title>
      <description>
        ${t.content!?xml}
      </description>
      <author>${t.trueName!?xml}</author>
      <category>${t.groupName!?xml}</category>
      <guid>${SiteUrl}manage/?url=groupBbs.action%3Fcmd=reply_list&amp;groupId=${t.groupId}&amp;topicId=${t.topicId}</guid>
      <pubDate>${t.createDateString!}</pubDate>
    </item>
    </#list>
  </channel>
</rss>