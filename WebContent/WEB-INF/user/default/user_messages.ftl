<ul class='listul'>
 <#list message_list as message>
 <li>[<a href='${SiteUrl}u/${message.loginName}'>${message.nickName}</a>]
 <br />${message.sendtime?string("yyyy-MM-dd HH:mm:ss")}
 <br /><a href='${SiteUrl}u/readmsg.action?msgid=message.id'>${message.title}</a></li>
 </#list>
</ul>