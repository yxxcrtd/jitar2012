    <!--教研动态 Start-->
    <div class="leftWidth border">
          <h3 class="h3Head textIn"><a href="showNewsList.action" class="more">更多</a>教研动态</h3>
          <div class="teach clearfix">
               <div class="teachScroll">
                <#if (pic_news??) && (pic_news?size > 0) >
				<div class="container" id="pic">
                    <ul class="indImg">
                      	<#list pic_news as ps>
                      	<#assign link="">
						<#assign icon="">
						<#assign txt="">
                        <li>
							<#assign link=link + "showNews.action?newsId=" + (ps.newsId?string)>
							<#assign icon=icon + (ps.picture!"")>
							<#assign txt=txt + (ps.title!?html)>
							<input id="picture${ps_index}" type="hidden" value="${icon}"/>
							<a id="imgAdd${ps_index}" href=${link} >
								<script type="text/javascript">
								   $.setImgAddr(${ps_index},'picture','imgAdd','320*240');
								</script>
							</a>
                            <div>
                                <a href=${link}>${Util.getCountedWords(txt!?html,10)}</a>
                                <p></p>
                            </div>
                        </li>
                       </#list>
                    </ul>
                </div>
                <div class="teachScrollBtn" id="tip">
                     <ul class="pagination">
                        <li class="active" onclick="change(1)" id="smallimg_1">1</li>
                        <li onclick="change(2)" id="smallimg_2" class="">2</li>
                        <li onclick="change(3)" id="smallimg_3" class="">3</li>
                        <li onclick="change(4)" id="smallimg_4" class="">4</li>
                        <li onclick="change(5)" id="smallimg_5" class="">5</li>
                    </ul>
                </div>
                 </#if>
              </div>
   		 		
              <div class="teachNews">
                  <#if jitar_news??>
                  <#assign jitar_news_1 = jitar_news[0]>
                  <#assign jitar_news_o = jitar_news[1..]>
                  <#if (jitar_news_o?size > 8)>
                    <#assign jitar_new = jitar_news_o[0..7]>
                  <#else>
                    <#assign jitar_new = jitar_news_o>
                  </#if>
                    <h4><a href="showNews.action?newsId=${jitar_news_1.newsId}">${Util.getCountedWords(jitar_news_1.title!?html,15)}</a></h4>
                	    <ul class="ulList mt13">
                	      <#list jitar_new as news>
                	       <li>
                	       <em class="emDate">${news.createDate?string('MM-dd')}</em>
                	       <a href='showNews.action?newsId=${news.newsId}' target='_blank'<#if (news.title!?html?length) gt 21> title="${news.title!?html}"</#if> >${Util.getCountedWords(news.title!?html,15)}</a>
                	       </li> 
                	       </#list>
                	    </ul>
                	</#if>
              </div>
           </div>
           <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize2" /></div>
      </div>
