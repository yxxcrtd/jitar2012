<!--教研动态 Start-->
<div class="leftWidth border">
    <h3 class="h3Head textIn"><a href="showNewsList.action" class="more">更多</a>教研动态</h3>
    <div class="teach clearfix">
         <div class="teachScroll">
          <#if (pic_news??) && (pic_news?size &gt; 0) >
	        <div class="container" id="pic">
              <ul class="indImg">
                	<#list pic_news as ps>
                	<#assign link="">
            			<#assign icon="">
            			<#assign txt="">
            			<#if ps_index &lt; 5>
                    <li>
          					<#assign link=link + "showNews.action?newsId=" + (ps.newsId?string)>
          					<#assign txt=txt + (ps.title!?html)>
          					<a href="${link}">
          						<img src="${Util.GetimgUrl(ps.picture,'320x240')}" onerror="this.src='${ContextPath}images/pixel.gif'" style="width:320px;height:240px" />
          					</a>
                        <div>
                            <a title="${txt!}" href=${link}>${Util.getCountedWords(txt!?html,16)}</a>
                            <p></p>
                        </div>
                    </li>
                  </#if>
                 </#list>
              </ul>
          </div>
          <div class="teachScrollBtn" id="tip">
               <ul class="pagination">
                 <#if (pic_news??) && (pic_news?size &gt; 0) >
                   <#list pic_news as news>
                     <#if news_index &lt; 5>
                        <#if news_index == 0>
                           <li class="active" onclick="change(1)" id="smallimg_1">1</li>
                        <#else>
                            <li onclick="change(${news_index+1})" id="smallimg_${news_index+1}" class="">${news_index+1}</li>
                        </#if>
                   </#if>
                 </#list>
                 </#if>
              </ul>
          </div>
           </#if>
        </div>
 		
        <div class="teachNews">
            <#if jitar_news?? && (jitar_news?size &gt; 0)>
            <#assign jitar_news_1 = jitar_news[0]>
              <h4><a title="${jitar_news_1.title}" href="showNews.action?newsId=${jitar_news_1.newsId}">${Util.getCountedWords(jitar_news_1.title!?html,15)}</a></h4>
          	    <ul class="ulList mt13">
          	      <#list jitar_news as news>
            	       <#if news_index &lt; 9 && news_index &gt;0 >
              	       <li>
              	       <em class="emDate">${news.createDate?string('MM-dd')}</em>
              	       <a href='showNews.action?newsId=${news.newsId}' target='_blank' title="${news.title!?html}" >${Util.getCountedWords(news.title!?html,20)}</a>
              	       </li> 
            	       </#if>
          	       </#list>
          	    </ul>
          	</#if>
        </div>
     </div>
     <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
</div>
