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
  <form method='post' action="http://www.eduyun.cn/index.php?m=user&amp;c=member&amp;a=loginin&backUrl=${SiteUrl?url}" style='padding-top:8px;'>
   <div style="padding:4px 0">用户名：<input placeholder="请输入用户名" class='loginInput' name='info[username]' id="login_username" /></div>
   <div style="padding:4px 0">密&nbsp; &nbsp;码：<input placeholder="请输入用密码" class='loginInput' name='info[password]' type='password' /></div>
   <div style="padding:4px 0"><label><input name="info[remember]" value="1" type="checkbox">记住帐号密码</label>	</div>
   <div style="padding:4px 0;text-align:center;"><input type='submit'  value='登录' class='subbtn' /> <input type='reset' class='subbtn' value='重置' /> </div>				 				 
   <div style="text-align:right;padding:4px 10px"><a href="http://www.eduyun.cn/index.php?m=user&amp;c=member&amp;a=backpassword">忘记密码</a></div>
  </form>
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