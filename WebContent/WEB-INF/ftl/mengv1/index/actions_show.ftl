<!--最新教研活动 Start-->
        <div class="rightWidth border mt3">
            <h3 class="h3Head textIn"><a href="actions.action" class="more">更多</a>最新教研活动</h3>
           <#if jitar_actions??>
            <div class="activity">
                <ul class="ulList">
                  <#list jitar_actions as a>
                    <li>
                      <em class="emDate">${a.createDate?string('MM-dd')}</em>
                      <#if (a.title!?html?length) gt 16> 
                      		<a href="showAction.action?actionId=${a.actionId}" title="${a.title!?html}">${Util.getCountedWords(a.title!?html,14)}</a>
                      <#else>
                      		<a href="showAction.action?actionId=${a.actionId}">${Util.getCountedWords(a.title!?html,14)}</a>
                      </#if>
                      </li> 
                  </#list> 
                </ul>
            </div>
            </#if>
            <div class="imgShadow"><img src="${SiteThemeUrl}images/imgShadow.jpg" class="imgShadowSize1" /></div>
        </div>
<!--最新教研活动 End-->