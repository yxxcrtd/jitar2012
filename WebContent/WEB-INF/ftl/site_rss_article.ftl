<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
  <channel>
    <#if (showType??) && (article_list[0]??) >
        <title>${article_list[0].nickName!} - 教研文章</title>
      <#else>
        <title>中教启星网络教研平台 - 教研文章</title>
      </#if>
    <link>${SiteUrl!?xml}</link>
    <description>中教启星网络教研平台</description>
    <language>zh-CN</language>
    <copyright>Copyright 1999-2016 北京中教启星科技股份有限公司</copyright>
    <pubDate>${today?string}</pubDate>
    <lastBuildDate>${today?string}</lastBuildDate>
    <category>教研文章</category>
    <docs>${SiteUrl}rss.action</docs>
    <image>
      <title>中教启星网络教研平台</title>
      <width>144</width>
      <height>35</height>
      <link>${SiteUrl!?xml}</link>
      <#if (showType??) && (article_list[0]??) && (article_list[0].userIcon??) >
        <url>${article_list[0].userIcon}</url>
      <#else>
        <url>${SiteUrl}images/index/ci.gif</url>
      </#if>
    </image>
    <#list article_list as article>
 	<item>
      <link>${SiteUrl}showArticle.action?articleId=${article.articleId}</link>
      <title>${article.title!?xml}</title>
      <description>
      <#if article.articleAbstract?? && article.articleAbstract != '' >
        ${article.articleAbstract!?xml}
      <#else >
        ${article.articleContent!?xml}
      </#if>
      </description>
      <author>${article.trueName!?xml}</author>
      <category>${article.subjectName?default('无分类')}</category>
      <guid>${SiteUrl}showArticle.action?articleId=${article.articleId}</guid>
      <pubDate>${article.lastModifiedString!}</pubDate>
    </item>
    </#list>
  </channel>
</rss>