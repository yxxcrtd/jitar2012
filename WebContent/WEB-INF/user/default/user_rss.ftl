<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
        xmlns="http://purl.org/rss/1.0/">
  <channel>
    <title>${user.blogName!?xml}</title>
    <link>${SiteUrl}${user.loginName}</link>
    <description>
      ${user.blogIntroduce!?xml}
    </description>
  </channel>
  
<#list article_list as article>
  <item>
    <title>${article.title!?xml}</title>
    <link>${SiteUrl}${user.loginName}/article/${article.id}.html</link>
    <description>
      ${article.articleAbstract!?xml}
    </description>
  </item>
</#list>
  
</rdf:RDF>
