<html>
	<script type="text/javascript" src="${SiteUrl}js/jquery.js"></script>
	<script type="text/javascript">
	<!--
	$(function() {
		var ifrmeNode = $("#loginhiddenfame");
		<#if list??>
			<#list list as p>
				// $.post("${p}?t=" + getCookies("UserTicket"));
				ifrmeNode.attr("src", "${p}?t=" + getCookies("UserTicket"));
			</#list>
		</#if>
	});
	function getCookies(name) {
		var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
		if (arr != null) {
			return unescape(arr[2]);
		}
		return null;
	}

  var casticketurl="";	
  <#if casServerUrl?? && casServerUrl != "">
  casticketurl = "${casServerUrl}/ticket.jsp?tmp=" + Math.random();
  </#if>  
  <#if loginUser??>
    //已经登录了
  <#else>
    //window.parent.checkticket(casticketurl);
  </#if>   
	//-->
	</script>
<body>
<div id="loginstatus">
<iframe id="loginhiddenfame" name="loginhiddenfame" style="display:none"></iframe>
<#if loginUser??>
	<div class='login_head login_head_a'>欢迎：${loginUser.trueName}</div>
	<div class='logincontent'>
	<div>工作室访问量：${loginUser.visitCount!}</div>
	<div>发表的文章数:${loginUser.articleCount!}</div>
	<div>上传的资源数:${loginUser.resourceCount!}</div>
	<div>发表的图片数:${loginUser.photoCount!}</div>
	<div>发表的评论数:${loginUser.commentCount!}</div>	
	<#if __genuser??>
	<div>上次于:${__genuser.lastLoginTime?string('yyyy-MM-dd HH:mm')}</div>
	<#if lastLoginAddress??>
	<div title="${__genuser.lastLoginIp}">上次登录地点: <br/>
	<div style="text-align:center">${lastLoginAddress}</div>
	</div>
	<#else>
	<div>上次登录IP: ${__genuser.lastLoginIp}</div>
	</#if>
	</#if>
	</div>
<#else>
  <div class='login_head login_head_a'>用户登录</div>
  <iframe name="_remotelogin" style="width:176px;height:124px;border:0;overflow:none;background-color:transparent;padding:0 0 0 0;" frameborder="0" scrolling="no" src="${casServerUrl}/remotelogin?service=${serverName}&ru=${siteserverurl}/login&target=_top&callbackLogOutUrl=${callbackLogOutUrl}"></iframe>
    <div style='padding:18px 10px 0 0;'><a class='recovery' href='${casServerUrl}/resetpassword'  target="_top"  onclick="this.href='${casServerUrl}/resetpassword'">找回密码</a></div>
    <#if is_show_reg_link == "true">
      <div style='padding:10px 10px 0 0;'>
          <a class='newreg' href="register.action" target="_top">新用户注册</a>
      </div>
    </#if>
</#if>
  </div>

<script>
window.onload=function()
{
	window.parent.document.getElementById("loginstatus").innerHTML = document.getElementById("loginstatus").innerHTML
}
</script>
</body>
</html>