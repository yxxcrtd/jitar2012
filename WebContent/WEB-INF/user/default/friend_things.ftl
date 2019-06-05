<ul class='listul'>
<#list data_list as data> <#--  data[0] = incident, data[1] = user -->
  <#if (data[1].trueName??)><#assign userName = data[1].trueName >
  <#elseif (data[1].nickName??)><#assign userName = data[1].nickName >
  <#elseif (data[1].loginName??)><#assign userName = data[1].loginName >
  </#if>
  <li title='时间：${data[0].createDate?string('yyyyy年M月d日 h点m分s秒')}'><a href='${SiteUrl}u/${data[1].loginName}' onmouseover="ToolTip.showUserCard(event,'${data[1].loginName}','${userName}');">${data[1].nickName!}</a>
  <#if data[0].url??><a href='${data[0].url}'>${data[0].title!?html}</a>
  <#else>${data[0].title!?html}
  </#if></li>
</#list>
</ul>