<div class="operate">
  <h3 class="operateH3">
      <a href="javascript:void(0);" class="o0">举报</a>
        <span class="o1">${resource.viewCount}次观看</span>
      <a href="javascript:void(0);" class="o2<#if praise??> hover</#if>"><#if praise??>取消</#if>赞</a>
        <a href="javascript:void(0);" class="o3<#if favorate??> hover</#if>">
          <b><#if favorate??>取消收藏<#else>收藏</#if></b>
          <span class="operateTips">
              <span>收藏成功！</span>
            </span>
        </a>
        <#if resourcedowload?? && resourcedowload == true && fileExists >
        <a href="manage/resourceDownload?resourceId=${resource.resourceId}" class="o5">下载</a>
        </#if>
    </h3>
    <div class="operateCont"<#if praise??> style="display:block"</#if>>    
      <p class="praise"<#if praise??> style="display:block"</#if>><a href="javascript:void(0);">
      <#if praiseCount == -1>
                   我觉得很赞!
      <#elseif praiseCount == 0>
                  还没有人赞。
      <#else>
                  我和其他${praiseCount}个人觉得很赞！
      </#if>
      </a></p>
      <ul class="clearfix"<#if praise??> style="display:block"</#if>>
       <#if praiseList??>
        <#list praiseList as p>
          <#assign user=Util.userById(p.userId)>
          <#if user??>
          <li><a title="${user.trueName}" href="${ContextPath}go.action?loginName=${user.loginName}" target="_blank"><img width="30" onerror="this.src='${ContextPath}images/default.gif'" src="${SSOServerUrl}upload/${user.userIcon!""}" /></a></li>
          </#if>
        </#list>
      </#if>
      </ul>
    </div>
</div>
