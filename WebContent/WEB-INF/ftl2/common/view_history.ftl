<#macro showViewHistory title>
<div class="contentRelation readThisCont">
  <h3>
  <#if browsingUser?? && browsingUser?size &gt; 8>
    <span class="ImgSlide">
      <a href="javascript:;" class="ImgSlideNext">下一页</a>
      <a href="javascript:;" class="ImgSlidePrev ImgSlidePrev_on">上一页</a>
    </span>
  </#if>
   ${title}
  </h3>
  <div class="readThis">
    <#if browsingUser??>
    <ul class="readThisList">
      <#list 0..7 as i>
      <#if browsingUser[i]?? >
      <li><a title="${browsingUser[i].trueName!""}" href="${ContextPath}u/${browsingUser[i].loginName}"><img src="${SSOServerUrl}upload/${browsingUser[i].userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" /></a><a title="${browsingUser[i].trueName!""}" href="${ContextPath}u/${browsingUser[i].loginName}">${Util.getCountedWords(browsingUser[i].trueName!"",4)}</a></li>
      </#if>
      </#list>
    </ul>
    <ul class="readThisList">
      <#list 8..16 as i>
      <#if browsingUser[i]?? >
      <li><a title="${browsingUser[i].trueName!""}" href="${ContextPath}u/${browsingUser[i].loginName}"><img src="${SSOServerUrl}upload/${browsingUser[i].userIcon!""}" onerror="this.src='${ContextPath}images/default.gif'" /></a><a title="${browsingUser[i].trueName!""}" href="${ContextPath}u/${browsingUser[i].loginName}">${Util.getCountedWords(browsingUser[i].trueName!"",4)}</a></li>
      </#if>
      </#list>
    </ul>
    </#if>
  </div>
</div>
</#macro>