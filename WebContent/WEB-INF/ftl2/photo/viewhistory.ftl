<div id="contentRelationHistory" class="contentRelation readThisCont">
  <h3>
      <span class="ImgSlide">
        <a href="javascript:void(0);" class="ImgSlideNext">下一页</a>
        <a href="javascript:void(0);" class="ImgSlidePrev">上一页</a>
      </span>
                   看过该图片的人
    </h3>
    <div class="readThis">
      <ul class="readThisList">
        <#list 0..7 as i>
        <#if browsingUser[i]?? >
        <li><a href="${ContextPath}u/${browsingUser[i].loginName}"><img src="${SSOServerUrl}upload/${browsingUser[i].userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" /></a><a href="${ContextPath}u/${browsingUser[i].loginName}">${browsingUser[i].trueName}</a></li>
        </#if>
        </#list>
      </ul>
      <ul class="readThisList">
        <#list 8..16 as i>
        <#if browsingUser[i]?? >
        <li><a href="${ContextPath}u/${browsingUser[i].loginName}"><img src="${SSOServerUrl}upload/${browsingUser[i].userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" /></a><a href="${ContextPath}u/${browsingUser[i].loginName}">${browsingUser[i].trueName}</a></li>
        </#if>
        </#list>
      </ul>
      <ul class="readThisList">
      <#list 17..24 as i>
      <#if browsingUser[i]?? >
      <li><a href="${ContextPath}u/${browsingUser[i].loginName}"><img src="${SSOServerUrl}upload/${browsingUser[i].userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" /></a><a href="${ContextPath}u/${browsingUser[i].loginName}">${browsingUser[i].trueName}</a></li>
      </#if>
      </#list>
      </ul>
    </div>
</div>