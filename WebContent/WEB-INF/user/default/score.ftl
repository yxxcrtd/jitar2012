<#if score?? >
<div class='article_footer' style="text-align:left;padding-right:20px;">
<#if score&gt;0 >
<img border="0" src="${SiteUrl}images/verygood.gif"/>&nbsp;&nbsp;${scoreCreateUserName!?html}&nbsp;&nbsp;于:&nbsp;${scoreDate?string('yyyy-MM-dd HH:mm:ss')}&nbsp;&nbsp;对此内容加${score}分，理由：${scoreReason!?html}
<#else>
<img border="0" src="${SiteUrl}images/bad.gif"/>&nbsp;&nbsp;${scoreCreateUserName!?html}&nbsp;&nbsp;于:&nbsp;${scoreDate?string('yyyy-MM-dd HH:mm:ss')}&nbsp;&nbsp;对此内容罚${-1*score}分，理由：${scoreReason!?html}
</#if>
</div>
</#if>