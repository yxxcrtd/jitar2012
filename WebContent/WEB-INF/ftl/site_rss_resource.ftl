<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
  <channel>
<#if (showType??) && (resource_list[0]??) >
    <title>${resource_list[0].trueName!?xml} - 教研资源</title>
  <#else>
    <title>中教启星网络教研平台 - 教研资源</title>
  </#if>
    <link>${SiteUrl!?xml}</link>
    <description>中教启星网络教研平台</description>
    <language>zh-CN</language>
    <copyright>Copyright 1999-2016 北京中教启星科技股份有限公司</copyright>
    <managingEditor>amxh@chinaedustar.com</managingEditor>
    <webMaster>amxh@chinaedustar.com</webMaster>
    <pubDate>${today?string}</pubDate>
    <lastBuildDate>${today?string}</lastBuildDate>
    <category>教研资源</category>
    <docs>${SiteUrl}rss.py?type=resource</docs>
    <image>
      <title>中教启星网络教研平台</title>
      <width>144</width>
      <height>35</height>
      <link>${SiteUrl!?xml}</link>
      <#if (showType??) && (resource_list[0]??) && (resource_list[0].userIcon??) >
        <url>${resource_list[0].userIcon}</url>
      <#else>
        <url>${SiteUrl}images/index/ci.gif</url>
      </#if>
    </image>   
    <#list resource_list as r>
    <item>
      <link>${SiteUrl}showResource.action?resourceId=${r.resourceId}</link>
      <title>${r.title!?xml}</title>
      <description>
      <#if r.summary?? && r.summary != '' >
        ${r.summary!?xml}
      <#else >
        ${r.title!?xml}
      </#if>
      </description>
      <author>${r.trueName!?xml}</author>
      <category>
      <#if r.subjectName?? && r.subjectName != '' >
        ${r.subjectName!?xml}
      <#else >
        无分类
      </#if>
      </category>
      <guid>${SiteUrl}showResource.action?resourceId=${r.resourceId}</guid>
      <pubDate>${r.lastModifiedString!}</pubDate>
    </item>
    </#list>
  </channel>
</rss>