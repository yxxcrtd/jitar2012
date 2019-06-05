<!--最新公告 Start-->
        <div class="rightWidth border">
            <h3 class="h3Head textIn"><a href="showPlacardList.action" class="more">更多</a>最新公告</h3>
            <div class="latest">
             <#if site_placard_list??>
                <ul class="ulList">
                  <#list site_placard_list as placard>
                    <li><em class="emDate">${placard.createDate?string('MM-dd')}</em>
                    <a href="showPlacard.action?placardId=${placard.id}" title="${placard.title!?html}">
                    	${Util.getCountedWords(placard.title!?html,15)}
                    </a>
                    </li> 
                  </#list> 
                </ul>
             </#if>
            </div>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
        </div>
<!--最新公告 End-->