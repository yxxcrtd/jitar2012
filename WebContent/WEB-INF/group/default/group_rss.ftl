<?xml version="1.0" encoding="UTF-8"?>
<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
        xmlns="http://purl.org/rss/1.0/">
  <channel>
    <title>${group.groupTitle!?xml}</title>
    <link>${SiteUrl}g/${group.groupName!}</link>
    <description>
      ${group.groupIntroduce!?xml}
    </description>
  </channel>
  
<#list article_list as article>
  <item>
    <title>${article.title!?xml}</title>
    <link>${SiteUrl}s/article/${article.id}.html</link>
    <description>
      ${article.articleAbstract!?xml}
    </description>
  </item>
</#list>

</rdf:RDF>
