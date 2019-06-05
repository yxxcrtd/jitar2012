<#include '/zdsoft/zdjs.ftl' >
<div id="loginstatus">
<#if loginUser?? >
	<div class='login_head login_head_a'>欢迎：${loginUser.nickName}</div>
	<div class='logincontent'>
	<div>工作室访问量：${loginUser.visitCount!}</div>
	<div>发表的文章数:${loginUser.articleCount!}</div>
	<div>上传的资源数:${loginUser.resourceCount!}</div>
	<div>发表的图片数:${loginUser.photoCount!}</div>
	<div>我发表的评论数:${loginUser.commentCount!}</div>
	</div>				
<#else>
  <div class='login_head login_head_a'>用户登录</div>
  <iframe name="_hidden"  style="display:none"></iframe>
  <form id='userlogin' method='post' style='padding-top:8px;'>
	<input type="hidden" name="redUrl" value="${SiteUrl}index.action" />
	<div>用户名称：<input class='loginInput' name='username' id="username"/></div>
	<div>用户密码：<input class='loginInput' name='password' id='password' type='password' /></div>
	<div>验证码：<input id="verifyCode" type="text" size="3" maxLength="4" /><a href="#" onClick="refreshImage()"><img id="verifyImage" border="0" align="middle" alt="验证码" src="${passportURL}/verifyImage?v="/></a></div>
	<div><input type='button'  value='登录' class='subbtn' onclick='login()' /> <input type='reset' class='subbtn' value='重置' /> </div>				 				 
	<div style='padding:18px 10px 0 0;'><a class='recovery' href='' onclick="this.href='${passportURL}/forgotPassword?server=${passportServerId}&url=${SiteUrl}&root=1&auth='">找回密码</a></div>
	<#if is_show_reg_link == "true">
      <div style='padding:10px 10px 0 0;'>
          <a class='newreg' href="regist.action" target="_self">新用户注册</a>
      </div>
      </#if>
  </form>
</#if>	
</div>
<script>
window.onload=function()
{
	window.parent.document.getElementById("loginstatus").innerHTML = document.getElementById("loginstatus").innerHTML
}
</script>
