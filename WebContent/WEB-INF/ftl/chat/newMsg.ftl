${lastId}|<#if msgList??>
<#list msgList as msg>
<li>
<font color="${msg.senderColor!}">${msg.senderName}</font>对<font color="${msg.receiverColor!}">${msg.receiverName}</font>说：[${msg.sendDate}]<br/>
<font color="${msg.senderColor!}">${msg.talkContent!}</font>
<br/><br/>
</li>
</#list>
</#if>
