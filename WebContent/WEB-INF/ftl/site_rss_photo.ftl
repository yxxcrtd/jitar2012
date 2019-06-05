<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
  <channel>
    <#if (showType??) && (photo_list[0]??) >
	    <title>${photo_list[0].nickName!} - 教研图片</title>
	  <#else>
	    <title>中教启星网络教研平台 - 教研图片</title>
	  </#if>
    
    <link>${SiteUrl!?xml}</link>
    <description>中教启星网络教研平台</description>
    <language>zh-CN</language>
    <copyright>Copyright 1999-2009 北京中教启星科技股份有限公司</copyright>
    <managingEditor></managingEditor>
    <webMaster></webMaster>
    <pubDate>${today?string}</pubDate>
    <lastBuildDate>${today?string}</lastBuildDate>
    <category>教研图片</category>
    <docs>${SiteUrl}rss.py?type=photo</docs>
    <image>
      <title>中教启星网络教研平台</title>
      <width>144</width>
      <height>35</height>
      <link>${SiteUrl!?xml}</link>
      <#if (showType??) && (photo_list[0]??) && (photo_list[0].userIcon??) >
        <url>${photo_list[0].userIcon}</url>
      <#else>
        <url>${SiteUrl}images/index/ci.gif</url>
      </#if>
    </image>
       
    <#list photo_list as p>
    <item>
      <link>${SiteUrl}showPhoto.py?photoId=${p.photoId}</link>
      <title>${p.title!?xml}</title>
      <description>
      <![CDATA[
      <a target='_blank' href='${SiteUrl}showPhoto.py?photoId=${p.photoId}'><img border='0' src='${Util.url(p.href)}' /></a>
      ]]>
      </description>
      <author>${p.trueName!?xml}</author>
      <category>${p.name?default('没有分类')}</category>
      <guid>${Util.url(p.href)}</guid>
      <pubDate>${p.lastModifiedString!}</pubDate>
    </item>
    </#list>
  </channel>
</rss>