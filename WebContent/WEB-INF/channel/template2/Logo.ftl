<#if channel.logo??>
<div class="logo">
<#if (channel.logo?lower_case)?contains(".swf")>
<embed src='${channel.logo}' quality='high' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width="1000" height="199"></embed>
<#else>
<img src="${channel.logo}" title="${channel.title!?html}" />
</#if>
</div>
</#if>